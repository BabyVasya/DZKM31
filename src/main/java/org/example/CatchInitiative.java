package org.example;

import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class CatchInitiative extends Behaviour {
    private String[] agents = {"Agent1", "Agent2", "Agent3"};
    private boolean iteration1 = false;

    @Override
    public void action() {
        MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
        ACLMessage receiveQueue = getAgent().receive(mt);
        if (!iteration1 && myAgent.getLocalName().equals("Agent1")) {
            myAgent.addBehaviour(new InitiateDistributedCalculation( Math.random(), 4));
            iteration1 = true;
        }
        if(receiveQueue != null && receiveQueue.getSender().getLocalName().equals("Agent1")) {
            String[] xd = receiveQueue.getContent().split(" ");
            getAgent().addBehaviour(new InitiateDistributedCalculation(Double.parseDouble(xd[0]), Double.parseDouble(xd[1])));
            System.out.println("Очередь передана от " + receiveQueue.getSender().getLocalName() + " к " + myAgent.getLocalName() + " " +receiveQueue);
        }
        if(receiveQueue != null && receiveQueue.getSender().getLocalName().equals("Agent2") ) {
            String[] xd = receiveQueue.getContent().split(" ");
            getAgent().addBehaviour(new InitiateDistributedCalculation(Double.parseDouble(xd[0]), Double.parseDouble(xd[1])));
            System.out.println("Очередь передана от " + receiveQueue.getSender().getLocalName() + " к " + myAgent.getLocalName() + " " +receiveQueue);
        }
        if(receiveQueue != null && receiveQueue.getSender().getLocalName().equals("Agent3") ) {
            String[] xd = receiveQueue.getContent().split(" ");
            getAgent().addBehaviour(new InitiateDistributedCalculation(Double.parseDouble(xd[0]), Double.parseDouble(xd[1])));
            System.out.println("Очередь передана от " + receiveQueue.getSender().getLocalName() + " к " + myAgent.getLocalName() + " " +receiveQueue);
        }
    }


    @Override
    public boolean done() {
        return false;
    }
}
