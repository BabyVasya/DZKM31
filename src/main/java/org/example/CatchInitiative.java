package org.example;

import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class CatchInitiative extends Behaviour {
    private String[] agents = {"Agent1", "Agent2", "Agent3"};
    private boolean iteration1 = false;

    @Override
    public void action() {
        start();
        MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
        ACLMessage receiveQueue = getAgent().receive(mt);
        String senderName = receiveQueue != null ? receiveQueue.getSender().getLocalName() : null;
        nonStartIterate(receiveQueue, senderName);
    }



    private void start() {
        if (!iteration1 && myAgent.getLocalName().equals("Agent1")) {
            myAgent.addBehaviour(new InitiateDistributedCalculation(Math.random(), 4));
            iteration1 = true;
        }
    }

    private void nonStartIterate (ACLMessage receiveQueue, String senderName) {
        for (String agentName : agents) {
            if (receiveQueue != null && senderName.equals(agentName)) {
                String[] xd = receiveQueue.getContent().split(" ");
                getAgent().addBehaviour(new InitiateDistributedCalculation(Double.parseDouble(xd[0]), Double.parseDouble(xd[1])));
                System.out.println("Очередь передана от " + senderName + " к " + myAgent.getLocalName() + " " + receiveQueue);
            }
        }
    }

    @Override
    public boolean done() {
        return false;
    }
}