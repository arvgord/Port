/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.prodes.port;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.xml.stream.XMLStreamException;
import org.apache.logging.log4j.LogManager;
import xyz.prodes.port.state.Ship;
import xyz.prodes.port.xmlreader.XMLReader;
import org.apache.logging.log4j.Logger;
import xyz.prodes.port.entity.Port;

/**
 *
 * @author ARTYOM
 */
public class App {

    private static final String FILE = "Port.xml";
    private static final Logger LOGGER = LogManager.getLogger();

    public static void main(String[] args) throws InterruptedException {
        LOGGER.info("Start xml reading");
        XMLReader reader = new XMLReader();
        List<Ship> ships;
        try {
            ships = reader.read(FILE);
        } catch (FileNotFoundException | XMLStreamException ex) {
            LOGGER.error("Error XML Reading!", ex);
            return;
        }
        
        ExecutorService executorService = Executors.newFixedThreadPool(Port.BERTHS);

        for (int i = 0; i < ships.size(); i++) {
            executorService.execute(ships.get(i));
            LOGGER.info("Start thread № " + i);
        }
        executorService.shutdown();
    }
}
