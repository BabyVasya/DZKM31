package org.example;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;


public class CalcMyFunction extends Behaviour {

    @Override
    public void action() {
        MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
        ACLMessage receiveRequest1 = getAgent().receive(mt);
        if (receiveRequest1!=null) {
            System.out.println("Полученный запрос на расчет функции агента " + myAgent.getLocalName()+ " от агента " + receiveRequest1.getSender().getLocalName() +" выглядит так: " + receiveRequest1);
            calcForAgent("Agent1", receiveRequest1, Double.parseDouble(receiveRequest1.getContent().split(" ")[1]), Double.parseDouble(receiveRequest1.getContent().split(" ")[0]));
            calcForAgent("Agent2", receiveRequest1, Double.parseDouble(receiveRequest1.getContent().split(" ")[1]), Double.parseDouble(receiveRequest1.getContent().split(" ")[0]));
            calcForAgent("Agent3", receiveRequest1,  Double.parseDouble(receiveRequest1.getContent().split(" ")[1]), Double.parseDouble(receiveRequest1.getContent().split(" ")[0]));
        }
    }

    private void calcForAgent (String senderName, ACLMessage receiveRequest1, double x, double d) {
        if(receiveRequest1.getSender().getLocalName().equals(senderName)) {
            ACLMessage responseMsg1 = new ACLMessage(ACLMessage.CONFIRM);
            if (myAgent.getLocalName().equals("Agent1")) {
                responseMsg1.setContent(Functions.agent1Function(x) + " " + Functions.agent1Function(x+d) + " " + Functions.agent1Function(x-d));
                responseMsg1.addReceiver(new AID(senderName, false));
                getAgent().send(responseMsg1);
            }
            if (myAgent.getLocalName().equals("Agent2")) {
                responseMsg1.setContent(Functions.agent2Function(x) + " " + Functions.agent2Function(x+d) + " " + Functions.agent2Function(x-d));
                responseMsg1.addReceiver(new AID(senderName, false));
                getAgent().send(responseMsg1);
            }
            if (myAgent.getLocalName().equals("Agent3")) {
                responseMsg1.setContent(Functions.agent3Function(x) + " " + Functions.agent3Function(x+d) + " " + Functions.agent3Function(x-d));
                responseMsg1.addReceiver(new AID(senderName, false));
                getAgent().send(responseMsg1);
            }
        }
    }
    @Override
    public boolean done() {
        return false;
    }
}