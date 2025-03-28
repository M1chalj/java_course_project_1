package symulator;

import java.util.Arrays;

public class Simulator {
    private static final Hour KONIEC_DNIA = new Hour(24, 0);

    private final Vehicle[] pojazdy;
    private final Stop[] przystanki;
    private final Passenger[] pasażerowie;
    private final Line[] linie;
    private final int liczbaDni;
    private final StatystykiDzienne[] statystykiDzienne;
    private StatystykiSumaryczne statystykiSumaryczne;

    public Simulator(int liczbaDni, Vehicle[] pojazdy, Stop[] przystanki,
                     Passenger[] pasażerowie, Line[] linie) {
        this.liczbaDni = liczbaDni;
        this.pojazdy = Arrays.copyOf(pojazdy, pojazdy.length);
        this.przystanki = Arrays.copyOf(przystanki, przystanki.length);
        this.pasażerowie = Arrays.copyOf(pasażerowie, pasażerowie.length);
        this.linie = Arrays.copyOf(linie, linie.length);
        this.statystykiDzienne = new StatystykiDzienne[liczbaDni];
    }

    public Simulator(SimulationData dane) {
        this(dane.liczbaDni(), dane.pojazdy(), dane.przystanki(),
                dane.pasażerowie(), dane.linie());
    }

    public void simulate() {
        for (int i = 0; i < liczbaDni; i++) {
            symulujDzień(i + 1);
            statystykiDzienne[i] = new StatystykiDzienne(i + 1, pojazdy, pasażerowie);
        }
        statystykiSumaryczne = new StatystykiSumaryczne(liczbaDni, pojazdy, pasażerowie);
    }

    private void symulujDzień(int numer_dnia) {
        EventsQueue kolejka = new EventsArrayQueue();

        for (Passenger passenger : pasażerowie) {
            passenger.beginDay(kolejka, numer_dnia);
        }

        for (Line line : linie) {
            line.beginDay(kolejka, numer_dnia);
        }

        while (!kolejka.empty()) {
            Event event = kolejka.get();
            event.simulate(kolejka);
        }

        for (Passenger passenger : pasażerowie) {
            passenger.zakończDzień(new Time(numer_dnia, KONIEC_DNIA));
        }

        for (Stop stop : przystanki) {
            stop.zakończDzień();
        }

        for (Vehicle vehicle : pojazdy) {
            vehicle.zakończDzień();
        }
    }

    public void printStatistics() {
        for (Statystyki stat : statystykiDzienne) {
            System.out.println();
            stat.wypisz();
        }
        System.out.println();
        statystykiSumaryczne.wypisz();
    }
}