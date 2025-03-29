package symulator;

public class Stop implements Comparable<Stop> {
    private final String name;
    private final int capacity;

    private final PassengersSet passengers;

    public Stop(String name, int capacity) {
        this.name = name;
        this.capacity = capacity;
        passengers = new PassengersArraySet(capacity);
    }

    @Override
    public String toString() {
        return "stop " + name;
    }

    @Override
    public int compareTo(Stop p) {
        return name.compareTo(p.name);
    }

    public boolean isFreeSpace() {
        return passengers.size() < capacity;
    }

    public boolean empty() {
        return passengers.empty();
    }

    public void addPassenger(Passenger passenger) {
        assert isFreeSpace() : "adding passenger to full stop";
        passengers.add(passenger);
    }

    public Passenger deletePassenger() {
        assert !empty() : "deleting passenger from empty stop";
        return passengers.get();
    }

    public void endDay() {
        passengers.clear();
    }

    public void printData() {
        System.out.print(name);
    }
}
