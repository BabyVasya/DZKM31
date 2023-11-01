package org.example;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.util.ArrayList;
import java.util.List;

public class InitiateDistributedCalculation extends Behaviour {
    private boolean isDone = false;
    private double x;
    private double d;
    private String maxArgs;
    double sumOfExtremum1 ;
    double sumOfExtremum2 ;
    double sumOfExtremum3 ;
    double extremum ;
    private boolean flagSend;
    private boolean receivedFlag;
    private List<ACLMessage> responseList = new ArrayList<>();

    private MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.CONFIRM);

    public InitiateDistributedCalculation(double x, double d) {
        this.x = x;
        this.d = d;
    }

    @Override
    public void onStart() {
        sendedCalcReq();
    }
    @Override
    public void action() {
        if (!receivedFlag) {
            this.calcConfirm();
        } else {
            this.procces();
            isDone = true;
        }
    }

    private void procces() {
        CalcResponse ag1 = new CalcResponse(responseList.get(0).getContent());
        CalcResponse ag2 = new CalcResponse(responseList.get(1).getContent());
        CalcResponse ag3 = new CalcResponse(responseList.get(2).getContent());
        sumOfExtremum1 = ag1.getY1() + ag2.getY1() + ag3.getY1();
        sumOfExtremum2 = ag1.getY2() + ag2.getY2() + ag3.getY2();
        sumOfExtremum3 = ag1.getY3() + ag2.getY3() + ag3.getY3();
        extremum = Math.max(sumOfExtremum1, Math.max(sumOfExtremum2, sumOfExtremum3));
        System.out.println("максимальные значение на этой итерации " + sumOfExtremum1 + " " +sumOfExtremum2+ " " + sumOfExtremum3);
            if (extremum == sumOfExtremum1) {
                maxArgs = "x";
            }
            if (extremum == sumOfExtremum2) {
                maxArgs = "x+d";
            }
            if (extremum == sumOfExtremum3) {
                maxArgs = "x-d";
            }

            if (maxArgs.equals("x-d")) {
                x = x - d;
                nextQueue(x, d);
            }
            if (maxArgs.equals("x+d")) {
                x = x + d;
                nextQueue(x, d);
            }
            if (maxArgs.equals("x")) {
                d = d/2;
                nextQueue(x,d);
            }
    }

    @Override
    public int onEnd() {
        System.out.println("Результат" + extremum);
        return 0;
    }

    public void nextQueue(double x, double d) {
        if(d > 0.001 ) {
            ACLMessage nextQueue = new ACLMessage(ACLMessage.INFORM);
            if(myAgent.getLocalName().equals("Agent1")) {
                nextQueue.addReceiver(new AID("Agent2", false));
                nextQueue.setContent(x + " " + d);
                getAgent().send(nextQueue);
            }
            if(myAgent.getLocalName().equals("Agent2")) {
                nextQueue.addReceiver(new AID("Agent3", false));
                nextQueue.setContent(x + " " + d);
                getAgent().send(nextQueue);
            }
            if(myAgent.getLocalName().equals("Agent3")) {
                nextQueue.addReceiver(new AID("Agent1", false));
                nextQueue.setContent(x + " " + d);
                getAgent().send(nextQueue);
            }
        } else onEnd();

    }

    private void calcConfirm() {
        ACLMessage receiveCalculations = getAgent().receive(mt);
        if(receiveCalculations != null) {
            responseList.add(receiveCalculations);
        }
        if(responseList.size() == 3) {
            receivedFlag = true;
        }
    }


    private void sendedCalcReq() {
        ACLMessage calculationRequest = new ACLMessage(ACLMessage.REQUEST);
        calculationRequest.addReceiver(new AID("Agent1", false));
        calculationRequest.addReceiver(new AID("Agent2", false));
        calculationRequest.addReceiver(new AID("Agent3", false));
        calculationRequest.setContent(x + " " + d);
        getAgent().send(calculationRequest);
        flagSend = true;

    }


    @Override
    public boolean done() {
        return isDone;
    }

    private class CalcResponse {
        private double y1;
        private double y2;
        private double y3;


        public CalcResponse(String response) {
            String[] split = response.split(" ");
            this.y1 = Double.parseDouble(split[0]);
            this.y2 = Double.parseDouble(split[1]);
            this.y3 = Double.parseDouble(split[2]);
        }

        public double getY1() {
            return y1;
        }

        public double getY2() {
            return y2;
        }

        public double getY3() {
            return y3;
        }

    }
}
