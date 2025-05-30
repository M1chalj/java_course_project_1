package symulator;

import main.RandomNumberGenerator;

public class Passenger {
    private static final Hour LEAVE_BEGIN = new Hour(6, 0);
    private static final Hour LEAVE_OUT = new Hour(12, 0);
    private static int passengersCounter = 0;

    private final int number;
    private final Stop firstStop;

    private Time beginOfWaiting;
    private int dailyWaitingTime;
    private int dailyWaitingCounter;
    private int totalWaitingTime;
    private int totalWaitingCounter;

    public Passenger(Stop firstStop) {
        number = passengersCounter++;
        this.firstStop = firstStop;
        totalWaitingTime = 0;
        totalWaitingCounter = 0;
    }

    void beginDay(EventsQueue queue, int day) {
        dailyWaitingTime = 0;
        beginOfWaiting = null;
        dailyWaitingCounter = 0;
        Time leaveTime = new Time(day, new Hour(
                RandomNumberGenerator.rand(LEAVE_BEGIN.toMinutes(), LEAVE_OUT.toMinutes())));
        queue.add(new PassengerLeavesHome(leaveTime, this));
    }

    @Override
    public String toString() {
        return "Passenger " + number;
    }

    public void leaveHome(Time time) {
        if (firstStop.isFreeSpace()) {
            goToStop(time, firstStop);
        } else {
            System.out.println(time.toString() + " " + this +
                    " had no space on his stop (" + firstStop + ") and goes back home");
        }
    }

    public void goToStop(Time time, Stop stop) {
        dailyWaitingCounter++;
        beginOfWaiting = time;
        stop.addPassenger(this);
        System.out.println(time + ": " + this + " went to " + stop);
    }

    public void enterVehicle(Time time, Vehicle vehicle) {
        dailyWaitingTime += time.difference(beginOfWaiting);
        beginOfWaiting = null;
        int destinationStop = vehicle.whichStopAfter(
                RandomNumberGenerator.rand(1, vehicle.stopsToEnd()));
        vehicle.addPassenger(this, destinationStop);
        System.out.println(time + ": " + this + " entered vehicle - " + vehicle +
                " - destination: " + vehicle.stop(destinationStop));
    }

    public void endDay(Time time) {
        if (beginOfWaiting != null) {
            dailyWaitingTime += time.difference(beginOfWaiting);
        }
        totalWaitingTime += dailyWaitingTime;
        totalWaitingCounter += dailyWaitingCounter;
    }

    public int dailyWaitingTime() {
        return dailyWaitingTime;
    }

    public int totalWaitingTime() {
        return totalWaitingTime;
    }

    public int dailyWaitingCounter() {
        return dailyWaitingCounter;
    }

    public int totalWaitingCounter() {
        return totalWaitingCounter;
    }
}
