package symulator;

public class Tram extends Vehicle {

    public Tram(Line line, int capacity) {
        super(line, capacity);
    }

    @Override
    public String toString() {
        return "Tram (" + super.toString() + ")";
    }
}
