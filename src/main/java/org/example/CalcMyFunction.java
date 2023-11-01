package org.example;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.util.function.Function;

public class CalcMyFunction extends Behaviour {
    private double d;
    private double x;
    private Function<Double, Double> func1;
    private Function<Double, Double> func2;
    private Function<Double, Double> func3;

    private boolean  isWork = false;

    public CalcMyFunction(Function<Double, Double> func1,
                          Function<Double, Double> func2,
                          Function<Double, Double> func3) {
        super();
        this.func1 = func1;
        this.func2 = func2;
        this.func3 = func3;
        func1.apply(x);
        func1.apply(x+d);
        func1.apply(x-d);
        func2.apply(x);
        func2.apply(x+d);
        func2.apply(x-d);
        func3.apply(x);
        func3.apply(x+d);
        func3.apply(x-d);
    }
//функ интерфейс
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
            double y11 = Functions.agent1Function(x);
            double y12 = Functions.agent1Function(x+d);
            double y13 = Functions.agent1Function(x-d);
            ACLMessage responseMsg1 = new ACLMessage(ACLMessage.CONFIRM);
            responseMsg1.setContent(y11 + " " + y12 + " " + y13);
            if (receiveRequest1.getSender().getLocalName().equals("Agent2")) {
                responseMsg1.addReceiver(new AID("Agent2", false));
                getAgent().send(responseMsg1);
                System.out.println("Отправка результата 1 функции агенту 2: " + responseMsg1);
            }
            if (receiveRequest1.getSender().getLocalName().equals("Agent3")) {
                responseMsg1.addReceiver(new AID("Agent3", false));
                getAgent().send(responseMsg1);
                System.out.println("Отправка результата 1 функции  агенту 3: " + responseMsg1);
            }
            if (receiveRequest1.getSender().getLocalName().equals("Agent1")) {
                responseMsg1.addReceiver(new AID("Agent1", false));
                getAgent().send(responseMsg1);
                System.out.println("Отправка результата 1 функции  агенту 1: " + responseMsg1);
            }
        }
    }

    @Override
    public boolean done() {
        return isWork;
    }
}
