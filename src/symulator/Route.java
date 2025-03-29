package symulator;

import java.util.Arrays;

public class Route {
    private final Stop[] stops;
    private final int[] timetable;
    private final int totalTravelTime;

    public Route(Stop[] stops, int[] timetable) {
        assert stops.length == timetable.length : "invalid input";

        this.stops = Arrays.copyOf(stops, stops.length);
        this.timetable = Arrays.copyOf(timetable, timetable.length);

        int travelTime = 0;
        for (int j : timetable) {
            travelTime += j;
        }
        this.totalTravelTime = travelTime * 2;
    }

    public Stop stop(int n) {
        return stops[n];
    }

    public int firstStop(boolean direction) {
        return direction ? 0 : numberOfStops() - 1;
    }

    public int lastStop(boolean direction) {
        return direction ? numberOfStops() - 1 : 0;
    }

    public int numberOfStops() {
        return stops.length;
    }

    public int totalTravelTime() {
        return totalTravelTime;
    }

    public int timeToNextStop(int currentStop, boolean direction) {
        if (direction) {
            return timetable[currentStop];
        } else {
            if (currentStop == 0) {
                return timetable[timetable.length - 1];
            } else {
                return timetable[currentStop - 1];
            }
        }
    }

    public int nextStop(int currentStop, boolean direction) {
        if (currentStop == lastStop(direction)) {
            return currentStop;
        } else {
            return currentStop + (direction ? 1 : -1);
        }
    }

    public void printData() {
        System.out.println("Number of stops: " + numberOfStops());
        for (int i = 0; i < stops.length; i++) {
            if (i != 0) {
                System.out.print(" ");
            }
            stops[i].printData();
            System.out.print(" " + timetable[i]);
        }
    }
}
