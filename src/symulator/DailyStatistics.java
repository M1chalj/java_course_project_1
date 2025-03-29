package symulator;

public class DailyStatistics extends Statistics {

    private final int numberOfDay;

    public DailyStatistics(int numberOfDay, Vehicle[] vehicles, Passenger[] passengers) {
        this.numberOfDay = numberOfDay;

        int waitingTime = 0;
        int numberOfWaitingPassengers = 0;
        int numberOfRides = 0;

        for (Vehicle vehicle : vehicles) {
            numberOfRides += vehicle.dailyNumberOfRides();
        }

        for (Passenger passenger : passengers) {
            waitingTime += passenger.dailyWaitingTime();
            numberOfWaitingPassengers += passenger.dailyWaitingCounter();
        }

        set(waitingTime, numberOfWaitingPassengers, numberOfRides);
    }

    @Override
    public void print() {
        System.out.println("Statistics - day " + numberOfDay + ":");
        super.print();
    }
}
