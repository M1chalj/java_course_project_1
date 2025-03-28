package symulator;

import java.util.Arrays;

public class TablicowyZbiórPasażerów implements ZbiórPasażerów {

    private Pasażer[] t;
    private int ile;

    public TablicowyZbiórPasażerów(int pojemnośćPoczątkowa) {
        t = new Pasażer[pojemnośćPoczątkowa];
        ile = 0;
    }

    public TablicowyZbiórPasażerów() {
        this(0);
    }

    @Override
    public void dodaj(Pasażer pasażer) {
        if (t.length == ile) {
            powiększ();
        }
        t[ile++] = pasażer;
    }

    @Override
    public Pasażer wyjmij() {
        assert !pusty() : "Wyjęcie z pustego zbioru";
        Pasażer pasażer = t[--ile];
        if (ile * 2 < t.length / 2) {
            pomniejsz();
        }
        return pasażer;
    }

    @Override
    public boolean pusty() {
        return ile == 0;
    }

    @Override
    public int rozmiar() {
        return ile;
    }

    @Override
    public void opróżnij() {
        ile = 0;
        t = new Pasażer[0];
    }

    private void powiększ() {
        t = Arrays.copyOf(t, Math.max(2 * t.length, 1));
    }

    private void pomniejsz() {
        t = Arrays.copyOf(t, ile * 2);
    }
}
