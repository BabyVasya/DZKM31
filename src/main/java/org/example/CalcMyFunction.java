package org.example;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.util.function.Function;

public class CalcMyFunction extends Behaviour {

    @Override
    public void action() {
        MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
        ACLMessage receiveRequest1 = getAgent().receive(mt);
        if (receiveRequest1!=null) {
            System.out.println("Полученный запрос на расчет функции агента " + myAgent.getLocalName()+ " от агента " + receiveRequest1.getSender().getLocalName() +" выглядит так: " + receiveRequest1);
            double d = Double.parseDouble(receiveRequest1.getContent().split(" ")[0]);
            double x = Double.parseDouble(receiveRequest1.getContent().split(" ")[1]);
            ACLMessage responseMsg1 = new ACLMessage(ACLMessage.CONFIRM);
            ACLMessage responseMsg2 = new ACLMessage(ACLMessage.CONFIRM);
            ACLMessage responseMsg3 = new ACLMessage(ACLMessage.CONFIRM);
            responseMsg1.setContent(Functions.agent1Function(x) + " " + Functions.agent1Function(x+d) + " " + Functions.agent1Function(x-d));
            responseMsg2.setContent(Functions.agent2Function(x) + " " + Functions.agent2Function(x+d) + " " + Functions.agent2Function(x-d));
            responseMsg3.setContent(Functions.agent3Function(x) + " " + Functions.agent3Function(x+d) + " " + Functions.agent3Function(x-d));
            if(receiveRequest1.getSender().getLocalName().equals("Agent1")) {
                if (myAgent.getLocalName().equals("Agent1")) {
                    responseMsg1.addReceiver(new AID("Agent1", false));
                    getAgent().send(responseMsg1);
                }
                if (myAgent.getLocalName().equals("Agent2")) {
                    responseMsg2.addReceiver(new AID("Agent1", false));
                    getAgent().send(responseMsg2);
                }
                if (myAgent.getLocalName().equals("Agent3")) {
                    responseMsg3.addReceiver(new AID("Agent1", false));
                    getAgent().send(responseMsg3);
                }
            }
            if(receiveRequest1.getSender().getLocalName().equals("Agent2")) {
                if (myAgent.getLocalName().equals("Agent1")) {
                    responseMsg1.addReceiver(new AID("Agent2", false));
                    getAgent().send(responseMsg1);
                }
                if (myAgent.getLocalName().equals("Agent2")) {
                    responseMsg2.addReceiver(new AID("Agent2", false));
                    getAgent().send(responseMsg2);
                }
                if (myAgent.getLocalName().equals("Agent3")) {
                    responseMsg3.addReceiver(new AID("Agent2", false));
                    getAgent().send(responseMsg3);
                }
            }
            if(receiveRequest1.getSender().getLocalName().equals("Agent3")) {
                if (myAgent.getLocalName().equals("Agent1")) {
                    responseMsg1.addReceiver(new AID("Agent3", false));
                    getAgent().send(responseMsg1);
                }
                if (myAgent.getLocalName().equals("Agent2")) {
                    responseMsg2.addReceiver(new AID("Agent3", false));
                    getAgent().send(responseMsg2);
                }
                if (myAgent.getLocalName().equals("Agent3")) {
                    responseMsg3.addReceiver(new AID("Agent3", false));
                    getAgent().send(responseMsg3);
                }
            }




        }
    }
    @Override
    public boolean done() {
        return false;
    }
}
