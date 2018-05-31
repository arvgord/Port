/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.prodes.port.xmlreader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import xyz.prodes.port.state.Ship;
import xyz.prodes.port.state.ShipLoadedState;
import xyz.prodes.port.state.ShipState;
import xyz.prodes.port.state.ShipUnloadedState;

/**
 *
 * @author ARTYOM
 */
public class XMLReader {
    private List<Ship> ships;
    private Ship currShip;
    private Attributes currTag;

    public List<Ship> read(String input) throws FileNotFoundException, XMLStreamException{
        FileInputStream fi;
        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        fi = new FileInputStream(input);
        XMLStreamReader reader = xmlInputFactory.createXMLStreamReader(fi);
            
        int event = reader.getEventType();
        boolean hasNext;
        do {
            switch (event) {
                case XMLStreamConstants.START_ELEMENT:
                    processStartElement(reader);
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    processEndElement(reader);
                    break;
                case XMLStreamConstants.CHARACTERS:
                    if (reader.isWhiteSpace()) {
                        break;
                    }
                    processCharacters(reader);
                    break;
            }
            hasNext = reader.hasNext();
            if (hasNext) {
                event = reader.next();
            }

        } while (hasNext);

        reader.close();
        return ships;
    }
    
    private void processStartElement(XMLStreamReader reader){
        String elementName = reader.getLocalName().toUpperCase();
        switch (Attributes.valueOf(elementName)){
            case SHIPS:
                ships = new ArrayList<>();
                break;
            case SHIP:
                int currentNumber = Integer.valueOf(reader.getAttributeValue("", "number"));
                currShip = new Ship();
                currShip.setNumber(currentNumber);
                currTag = Attributes.SHIP;
                break;
        }
    }
    
    private void processEndElement(XMLStreamReader reader){
        String elementName = reader.getLocalName().toUpperCase();
        switch (Attributes.valueOf(elementName)){
            case SHIP:
                ships.add(currShip);
                break;
        }
    }
    
    private void processCharacters(XMLStreamReader reader){
        switch (currTag) {
            case SHIP:
                boolean currentIsLoaded = Boolean.valueOf(reader.getText());
                ShipState state = currentIsLoaded ? new ShipLoadedState() : new ShipUnloadedState();
                currShip.setCurrentState(state);
                break;
        }
    }
}
