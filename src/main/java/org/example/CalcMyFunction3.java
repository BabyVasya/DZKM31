package org.example;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class CalcMyFunction3 extends Behaviour {
    private double x;
    private double d;
    @Override
    public void action() {
        MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
        ACLMessage receiveRequest1 = getAgent().receive(mt);
        if (receiveRequest1!=null) {
            System.out.println("Полученный запрос на расчет функции агента " + myAgent.getLocalName()+ " от агента " + receiveRequest1.getSender().getLocalName() +" выглядит так: " + receiveRequest1);
            String[] splitting;
            splitting = receiveRequest1.getContent().split(" ");

            try {
                d = Double.parseDouble(splitting[1]);
                x = Double.parseDouble(splitting[0]);

            } catch (NumberFormatException e ){
                e.printStackTrace();
            }
            double y31 = Functions.agent3Function(x);
            double y32 = Functions.agent3Function(x+d);
            double y33 = Functions.agent3Function(x-d);
            ACLMessage responseMsg1 = new ACLMessage(ACLMessage.CONFIRM);
            responseMsg1.setContent(y31 + " " + y32 + " " + y33);
            if (receiveRequest1.getSender().getLocalName().equals("Agent1")) {
                responseMsg1.addReceiver(new AID("Agent1", false));
                getAgent().send(responseMsg1);
                System.out.println("Отправка результата 3 функции агенту 1: " + responseMsg1);
            }
            if (receiveRequest1.getSender().getLocalName().equals("Agent2")) {
                responseMsg1.addReceiver(new AID("Agent2", false));
                getAgent().send(responseMsg1);
                System.out.println("Отправка результата 3 функции  агенту 2: " + responseMsg1);
            }
            if (receiveRequest1.getSender().getLocalName().equals("Agent3")) {
                responseMsg1.addReceiver(new AID("Agent3", false));
                getAgent().send(responseMsg1);
                System.out.println("Отправка результата 3 функции  агенту 3: " + responseMsg1);
            }
        }
    }

    @Override
    public boolean done() {
        return false;
    }
}
