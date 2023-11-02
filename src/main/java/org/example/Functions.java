package org.example;

import java.util.function.Function;

public class Functions {
    public static double agent1Function(double x) {
        return -0.5*Math.pow(x, 2) - 4;
    }
    public static double agent2Function(double x) {
        return Math.pow(2, -0.1*(x));
    }
    public static double agent3Function(double x) {
        return Math.cos(x);
    }

    public static double getAgentFunction(String senderName, double x) {
        switch (senderName) {
            case "Agent1":
                return Functions.agent1Function(x);
            case "Agent2":
                return Functions.agent2Function(x);
            case "Agent3":
                return Functions.agent3Function(x);
            default:
                // Если имя отправителя не соответствует ни одному агенту, вернуть нулевое значение или другое по умолчанию.
                return 0.0;
        }
    }
}
