package symulator;

import java.util.Arrays;

public class Simulator {
    private static final Hour END_OF_DAY = new Hour(24, 0);

    private final Vehicle[] vehicles;
    private final Stop[] stops;
    private final Passenger[] passengers;
    private final Line[] lines;
    private final int numberOfDays;
    private final DailyStatistics[] dailyStatistics;
    private SummaryStatistics summaryStatistics;

    public Simulator(int numberOfDays, Vehicle[] vehicles, Stop[] stops,
                     Passenger[] passengers, Line[] lines) {
        this.numberOfDays = numberOfDays;
        this.vehicles = Arrays.copyOf(vehicles, vehicles.length);
        this.stops = Arrays.copyOf(stops, stops.length);
        this.passengers = Arrays.copyOf(passengers, passengers.length);
        this.lines = Arrays.copyOf(lines, lines.length);
        this.dailyStatistics = new DailyStatistics[numberOfDays];
    }

    public Simulator(SimulationData data) {
        this(data.numberOfDays(), data.vehicles(), data.stops(),
                data.passengers(), data.lines());
    }

    public void simulate() {
        for (int i = 0; i < numberOfDays; i++) {
            simulateDay(i + 1);
            dailyStatistics[i] = new DailyStatistics(i + 1, vehicles, passengers);
        }
        summaryStatistics = new SummaryStatistics(numberOfDays, vehicles, passengers);
    }

    private void simulateDay(int numberOfDay) {
        EventsQueue eventsQueue = new EventsArrayQueue();

        for (Passenger passenger : passengers) {
            passenger.beginDay(eventsQueue, numberOfDay);
        }

        for (Line line : lines) {
            line.beginDay(eventsQueue, numberOfDay);
        }

        while (!eventsQueue.empty()) {
            Event event = eventsQueue.get();
            event.simulate(eventsQueue);
        }

        for (Passenger passenger : passengers) {
            passenger.endDay(new Time(numberOfDay, END_OF_DAY));
        }

        for (Stop stop : stops) {
            stop.endDay();
        }

        for (Vehicle vehicle : vehicles) {
            vehicle.endDay();
        }
    }

    public void printStatistics() {
        for (Statistics stat : dailyStatistics) {
            System.out.println();
            stat.print();
        }
        System.out.println();
        summaryStatistics.print();
    }
}