package org.example;

import jade.core.Agent;

public class FunctionAgent2 extends Agent {
    @Override
    protected void setup() {
        addBehaviour(new CatchInitiative());
        addBehaviour(new CalcMyFunction2());
    }
}
