package symulator;

public interface ZbiórPasażerów {
    void dodaj(Pasażer pasażer);

    Pasażer wyjmij();

    boolean pusty();

    int rozmiar();

    void opróżnij();
}
