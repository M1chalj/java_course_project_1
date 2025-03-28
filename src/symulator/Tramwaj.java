package symulator;

public class Tramwaj extends Vehicle {

    public Tramwaj(Line line, int pojemność) {
        super(line, pojemność);
    }

    @Override
    public String toString() {
        return "tramwaj (" + super.toString() + ")";
    }
}
