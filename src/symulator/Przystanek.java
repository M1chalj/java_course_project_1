package symulator;

public class Przystanek implements Comparable<Przystanek> {
    private final String nazwa;
    private final int pojemność;

    private final ZbiórPasażerów pasażerowie;

    public Przystanek(String nazwa, int pojemność) {
        this.nazwa = nazwa;
        this.pojemność = pojemność;
        pasażerowie = new TablicowyZbiórPasażerów(pojemność);
    }

    @Override
    public String toString() {
        return "przystanek " + nazwa;
    }

    @Override
    public int compareTo(Przystanek p) {
        return nazwa.compareTo(p.nazwa);
    }

    public boolean jestMiejsce() {
        return pasażerowie.rozmiar() < pojemność;
    }

    public boolean pusty() {
        return pasażerowie.pusty();
    }

    public void dodajPasażera(Pasażer pasażer) {
        assert jestMiejsce() : "dodanie pasażera do pełnego przystanku";
        pasażerowie.dodaj(pasażer);
    }

    public Pasażer usuńPasażera() {
        assert !pusty() : "usunięcie pasażera z pustego przystanku";
        return pasażerowie.wyjmij();
    }

    public void zakończDzień() {
        pasażerowie.opróżnij();
    }

    public void wypiszDane() {
        System.out.print(nazwa);
    }
}
