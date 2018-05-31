/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.prodes.port.entity;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import xyz.prodes.port.state.Ship;

/**
 *
 * @author ARTYOM
 */
public class Port {

    private static volatile Port INSTANCE;
    public static final int BERTHS = 2;
    private static final Semaphore PORT = new Semaphore(BERTHS);
    private static final Logger LOGGER = LogManager.getLogger();
    private static final ReentrantLock LOCKER = new ReentrantLock();

    private Port() {
    }

    public static Port getInstance() {
        if (INSTANCE == null) {
            try {
                LOCKER.lock();
                if (INSTANCE == null) {
                    INSTANCE = new Port();
                    LOGGER.debug("Singleton was created");
                }
            } finally {
                LOCKER.unlock();
            }
        }
        return INSTANCE;
    }

    public void process(Ship ship) {
        try {
            PORT.acquire();
            LOGGER.info("Ship " + ship.getNumber() + " arrived at the pier");
            ship.process();
        } catch (InterruptedException ex) {
            LOGGER.error(ex.getMessage());
        } finally {
            PORT.release();
            LOGGER.info("Ship " + ship.getNumber() + " was servised");
        }
    }
}
