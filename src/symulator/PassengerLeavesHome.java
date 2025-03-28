package symulator;

public class PassengerLeavesHome extends Event {
    final Passenger passenger;

    public PassengerLeavesHome(Time time, Passenger passenger) {
        super(time);
        this.passenger = passenger;
    }

    @Override
    public void simulate(EventsQueue queue) {
        passenger.leaveHome(time());
    }
}
