package symulator;

public class SummaryStatistics extends Statistics {

    private final int numberOfDays;

    public SummaryStatistics(int numberOfDays, Vehicle[] vehicles, Passenger[] passengers) {

        this.numberOfDays = numberOfDays;

        int waitingTime = 0;
        int numberOfWaitingPassengers = 0;
        int numberOfRides = 0;

        for (Vehicle vehicle : vehicles) {
            numberOfRides += vehicle.totalNumberOfRides();
        }

        for (Passenger passenger : passengers) {
            waitingTime += passenger.totalWaitingTime();
            numberOfWaitingPassengers += passenger.totalWaitingCounter();
        }

        set(waitingTime, numberOfWaitingPassengers, numberOfRides);
    }

    @Override
    public void print() {
        System.out.println(numberOfDays + " day summary statistics:");
        super.print();
        double averageNumberOfRides = numberOfRides();
        if (numberOfDays != 0) {
            averageNumberOfRides /= numberOfDays;
        }
        System.out.println("Average number of rides per day: "
                + String.format("%.2f", averageNumberOfRides));
    }
}
