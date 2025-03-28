package symulator;

import java.util.Arrays;

public class Line {
    private final static Hour FIRST_DEPARTURE = new Hour(6, 0);
    private final static boolean START_FROM_BEGIN = true;
    private final static boolean START_FROM_END = false;

    private final Route route;
    private final int number;

    private Vehicle[] vehicles;
    private int numberOfVehicles;

    public Line(int number, Route route) {
        this.number = number;
        this.route = route;
        vehicles = new Vehicle[1];
        numberOfVehicles = 0;
    }

    @Override
    public String toString() {
        return "line " + number;
    }

    public Route route() {
        return route;
    }

    public void addVehicle(Vehicle p) {
        if (vehicles.length == numberOfVehicles) {
            vehicles = Arrays.copyOf(vehicles, vehicles.length * 2);
        }
        vehicles[numberOfVehicles++] = p;
    }

    public void beginDay(EventsQueue queue, int day) {
        if (numberOfVehicles == 0) {
            return;
        }

        int interval = Math.ceilDiv(route.totalTravelTime(), numberOfVehicles);

        Hour departureHour = FIRST_DEPARTURE;
        for (int i = 0; i < numberOfVehicles / 2; i++) {
            vehicles[i].beginDay(queue, day, START_FROM_END, departureHour);
            departureHour = departureHour.add(interval);
        }

        departureHour = FIRST_DEPARTURE;
        for (int i = numberOfVehicles / 2; i < numberOfVehicles; i++) {
            vehicles[i].beginDay(queue, day, START_FROM_BEGIN, departureHour);
            departureHour = departureHour.add(interval);
        }
    }

    public void printData() {
        System.out.println("Number of vehicles: " + numberOfVehicles);
        System.out.println("Route: ");
        route.printData();
    }
}
