package symulator;

public class Godzina implements Comparable<Godzina> {
    private static final int GODZINA_W_MINUTACH = 60;

    private final int godzina;
    private final int minuta;

    public Godzina(int godzina, int minuta) {
        this.godzina = godzina;
        this.minuta = minuta;
    }

    public Godzina(int minuty) {
        godzina = minuty / GODZINA_W_MINUTACH;
        minuta = minuty % GODZINA_W_MINUTACH;
    }

    @Override
    public String toString() {
        return (godzina < 10 ? "0" : "") + godzina + ":" + (minuta < 10 ? "0" : "") + minuta;
    }

    @Override
    public int compareTo(Godzina g) {
        if (godzina != g.godzina) {
            return godzina < g.godzina ? -1 : 1;
        }
        if (minuta != g.minuta) {
            return minuta < g.minuta ? -1 : 1;
        }
        return 0;
    }

    public int naMinuty() {
        return godzina * GODZINA_W_MINUTACH + minuta;
    }

    public Godzina dodaj(int minuty) {
        return new Godzina(naMinuty() + minuty);
    }

    public int różnica(Czas g) {
        return naMinuty() - g.naMinuty();
    }
}
