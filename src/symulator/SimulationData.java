package symulator;

import main.RandomNumberGenerator;

import java.util.Arrays;
import java.util.Scanner;

public class SimulationData {
    private Vehicle[] vehicles;
    private Stop[] stops;
    private Passenger[] passengers;
    private Line[] lines;
    private int numberOfDays;
    private int stopCapacity;
    private int vehicleCapacity;

    public SimulationData() {
        vehicles = new Vehicle[0];
        stops = new Stop[0];
        passengers = new Passenger[0];
        lines = new Line[0];
    }

    public Vehicle[] vehicles() {
        return vehicles;
    }

    public Stop[] stops() {
        return stops;
    }

    public Passenger[] passengers() {
        return passengers;
    }

    public Line[] lines() {
        return lines;
    }

    public int numberOfDays() {
        return numberOfDays;
    }

    public void read() {
        Scanner scanner = new Scanner(System.in);

        numberOfDays = scanner.nextInt();

        stopCapacity = scanner.nextInt();
        int numberOfStops = scanner.nextInt();
        stops = new Stop[numberOfStops];
        for (int i = 0; i < numberOfStops; i++) {
            String name = scanner.next();
            stops[i] = new Stop(name, stopCapacity);
        }
        Arrays.sort(stops);

        int numberOfPassengers = scanner.nextInt();
        passengers = new Passenger[numberOfPassengers];
        for (int i = 0; i < numberOfPassengers; i++) {
            passengers[i] = new Passenger(
                    stops[RandomNumberGenerator.rand(0, numberOfStops - 1)]);
        }

        vehicleCapacity = scanner.nextInt();
        int numberOfLines = scanner.nextInt();
        lines = new Line[numberOfLines];
        for (int i = 0; i < numberOfLines; i++) {
            int numberOfVehicles = scanner.nextInt();
            int routeLength = scanner.nextInt();

            Stop[] stopsOnRoute = new Stop[routeLength];
            int[] timetable = new int[routeLength];

            for (int j = 0; j < routeLength; j++) {
                String name = scanner.next();
                timetable[j] = scanner.nextInt();
                stopsOnRoute[j] = findStop(name);
            }
            lines[i] = new Line(i, new Route(stopsOnRoute, timetable));

            int begin = vehicles.length;
            vehicles = Arrays.copyOf(vehicles, vehicles.length + numberOfVehicles);
            for (int j = 0; j < numberOfVehicles; j++) {
                vehicles[begin + j] = new Tram(lines[i], vehicleCapacity);
            }
        }
        scanner.close();
    }

    public void print() {
        System.out.println("Number of days: " + numberOfDays);
        System.out.println("Stop capacity: " + stopCapacity);
        System.out.println("Number of stops: " + stops.length);
        if (stops.length > 0) {
            System.out.println("Stops: ");
        }
        for (Stop stop : stops) {
            stop.printData();
            System.out.println();
        }
        System.out.println("Number of passengers: " + passengers.length);
        System.out.println("Vehicle capacity: " + vehicleCapacity);
        System.out.println("Number of days: " + lines.length);
        for (int i = 0; i < lines.length; i++) {
            System.out.println("Line " + i + ": ");
            lines[i].printData();
            System.out.println();
        }
    }

    private Stop findStop(String name) {
        Stop stop = new Stop(name, numberOfDays);
        int left = 0;
        int right = stops.length - 1;
        while (left < right) {
            int mid = (left + right + 1) / 2;
            if (stops[mid].compareTo(stop) <= 0) {
                left = mid;
            } else {
                right = mid - 1;
            }
        }
        assert stop.compareTo(stops[left]) == 0 : "Unknown stop " + name;
        return stops[left];
    }
}
