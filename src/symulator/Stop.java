package symulator;

public class Stop implements Comparable<Stop> {
    private final String nazwa;
    private final int pojemność;

    private final ZbiórPasażerów pasażerowie;

    public Stop(String nazwa, int pojemność) {
        this.nazwa = nazwa;
        this.pojemność = pojemność;
        pasażerowie = new TablicowyZbiórPasażerów(pojemność);
    }

    @Override
    public String toString() {
        return "przystanek " + nazwa;
    }

    @Override
    public int compareTo(Stop p) {
        return nazwa.compareTo(p.nazwa);
    }

    public boolean isFreeSpace() {
        return pasażerowie.rozmiar() < pojemność;
    }

    public boolean pusty() {
        return pasażerowie.pusty();
    }

    public void addPassenger(Passenger passenger) {
        assert isFreeSpace() : "dodanie pasażera do pełnego przystanku";
        pasażerowie.dodaj(passenger);
    }

    public Passenger usuńPasażera() {
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
