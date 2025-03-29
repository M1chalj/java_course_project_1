package symulator;

public class VehicleArrival extends Event {

    private final Vehicle vehicle;

    public VehicleArrival(Time time, Vehicle vehicle) {
        super(time);
        this.vehicle = vehicle;
    }

    @Override
    public void simulate(EventsQueue queue) {
        vehicle.arriveAtStop(queue, time());
    }
}
