package symulator;

public abstract class Wydarzenie implements Comparable<Wydarzenie> {
    private final Czas czas;

    public Wydarzenie(Czas czas) {
        this.czas = czas;
    }

    @Override
    public int compareTo(Wydarzenie w) {
        return czas.compareTo(w.czas);
    }

    protected Czas czas() {
        return czas;
    }

    public abstract void symuluj(KolejkaWydarzeń kolejka);
}
