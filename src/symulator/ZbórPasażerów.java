package symulator;

public interface ZbiórPasażerów {
    void dodaj(Passenger passenger);

    Passenger wyjmij();

    boolean pusty();

    int rozmiar();

    void opróżnij();
}
