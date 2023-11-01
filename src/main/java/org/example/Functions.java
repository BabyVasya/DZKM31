package org.example;

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
}
