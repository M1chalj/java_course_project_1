package symulator;

public class Czas implements Comparable<Czas> {
    private static final int DZIEŃ_W_MINUTACH = 24 * 60;

    private final int dzień;
    private final Godzina godzina;

    public Czas(int dzień, Godzina godzina) {
        this.dzień = dzień;
        this.godzina = godzina;
    }

    public Czas(int minuty) {
        dzień = minuty / DZIEŃ_W_MINUTACH;
        godzina = new Godzina(minuty % DZIEŃ_W_MINUTACH);
    }

    @Override
    public String toString() {
        return dzień + ", " + godzina;
    }

    @Override
    public int compareTo(Czas g) {
        if (dzień != g.dzień) {
            return dzień < g.dzień ? -1 : 1;
        }
        return godzina.compareTo(g.godzina);
    }

    public int dzień() {
        return dzień;
    }

    public int naMinuty() {
        return dzień * DZIEŃ_W_MINUTACH + godzina.naMinuty();
    }

    public Czas dodaj(int minuty) {
        return new Czas(naMinuty() + minuty);
    }

    public int różnica(Czas g) {
        return naMinuty() - g.naMinuty();
    }
}
