package symulator;

public interface KolejkaWydarzeń {
    void dodaj(Wydarzenie w);

    Wydarzenie wyjmij();

    boolean pusta();
}