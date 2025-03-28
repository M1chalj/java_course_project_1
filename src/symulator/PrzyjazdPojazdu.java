package symulator;

public class PrzyjazdPojazdu extends Event {

    private final Vehicle vehicle;

    public PrzyjazdPojazdu(Time time, Vehicle vehicle) {
        super(time);
        this.vehicle = vehicle;
    }

    @Override
    public void simulate(EventsQueue queue) {
        vehicle.przyjedźNaPrzystanek(queue, time());
    }
}
