package symulator;

import java.util.Arrays;

public class TablicowaKolejkaWydarzeń implements KolejkaWydarzeń {

    Wydarzenie[] t;
    int początek;
    int koniec;

    public TablicowaKolejkaWydarzeń() {
        t = new Wydarzenie[0];
        początek = 0;
        koniec = 0;
    }

    @Override
    public void dodaj(Wydarzenie w) {
        if (koniec == t.length) {
            powiększ();
        }
        t[koniec++] = w;

        int i = koniec - 1;
        while (i > 0 && t[i].compareTo(t[i - 1]) < 0) {
            Wydarzenie temp = t[i - 1];
            t[i - 1] = t[i];
            t[i] = temp;
            i--;
        }
    }

    @Override
    public Wydarzenie wyjmij() {
        assert !pusta() : "Wyjęcie z pustej kolejki";
        Wydarzenie wydarzenie = t[początek++];
        if (początek >= t.length / 2) {
            przebuduj();
        }
        return wydarzenie;
    }

    @Override
    public boolean pusta() {
        return początek == koniec;
    }

    private void powiększ() {
        t = Arrays.copyOf(t, Math.max(2 * t.length, 1));
    }

    private void przebuduj() {
        for (int i = 0; i < koniec - początek; i++) {
            t[i] = t[początek + i];
        }
        koniec -= początek;
        początek = 0;
        t = Arrays.copyOf(t, koniec * 2);
    }
}
