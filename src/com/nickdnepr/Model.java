package com.nickdnepr;

import java.util.Vector;

public class Model {

    private Vector<Integer> diceState;

    public Model(Vector<Integer> diceState) {
        this.diceState = diceState;
    }

    public Vector<Integer> getDiceState() {
        return diceState;
    }

    public void setDiceState(Vector<Integer> diceState) {
        this.diceState = diceState;
    }
}
