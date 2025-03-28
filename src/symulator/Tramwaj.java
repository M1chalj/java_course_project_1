package symulator;

public class Tramwaj extends Pojazd {

    public Tramwaj(Linia linia, int pojemność) {
        super(linia, pojemność);
    }

    @Override
    public String toString() {
        return "tramwaj (" + super.toString() + ")";
    }
}
