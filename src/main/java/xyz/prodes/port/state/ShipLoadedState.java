/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.prodes.port.state;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author ARTYOM
 */
public class ShipLoadedState implements ShipState{

    private static final ShipState NEXT_STATE = new ShipUnloadedState();
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void process(Ship ship) {
        LOGGER.info("Ship " + ship.getNumber() + " was unloaded");
        ship.setCurrentState(NEXT_STATE);
    }
}
