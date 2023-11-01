package org.example;

import jade.core.Agent;

public class FunctionAgent extends Agent {
    private double x;
    private double d;
    @Override
    protected void setup() {
        addBehaviour(new CatchInitiative());
        addBehaviour(new CalcMyFunction());
    }
}
