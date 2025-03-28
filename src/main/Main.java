package main;

import symulator.SimulationData;
import symulator.Simulator;

public class Main {
    public static void main(String[] args) {
        SimulationData data = new SimulationData();
        data.read();
        data.print();
        System.out.println();
        Simulator simulator = new Simulator(data);
        simulator.simulate();
        simulator.printStatistics();
    }
}
