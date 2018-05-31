/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.prodes.port.state;

import xyz.prodes.port.entity.Port;

/**
 *
 * @author ARTYOM
 */
public class Ship implements Runnable{

    private ShipState currentState = null;
    private int number;

    public Ship() {
    }

    @Override
    public void run() {
        Port.getInstance().process(this);
    }

    public void process() {
        currentState.process(this);
    }

    public void setCurrentState(ShipState currentState) {
        this.currentState = currentState;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
