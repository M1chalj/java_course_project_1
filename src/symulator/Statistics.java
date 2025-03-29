package symulator;

public abstract class Statistics {
    private int waitingTime;
    private int numberOfWaitingPassengers;
    private int numberOfRides;

    public void print() {
        System.out.println("Total waiting time: "
                + waitingTime + " min");
        double averageTime = waitingTime;
        if (numberOfWaitingPassengers != 0) {
            averageTime /= numberOfWaitingPassengers;
        }
        System.out.println("Average waiting time: "
                + String.format("%.2f", averageTime) + " min");
        System.out.println("Number of rides: " + numberOfRides);
    }

    protected void set(int waitingTime, int numberOfWaitingPassengers, int numberOfRides) {
        this.waitingTime = waitingTime;
        this.numberOfWaitingPassengers = numberOfWaitingPassengers;
        this.numberOfRides = numberOfRides;
    }

    protected int numberOfRides() {
        return numberOfRides;
    }
}
