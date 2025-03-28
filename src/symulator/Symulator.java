package symulator;

import java.util.Arrays;

public class Symulator {
    private static final Godzina KONIEC_DNIA = new Godzina(24, 0);

    private final Pojazd[] pojazdy;
    private final Przystanek[] przystanki;
    private final Pasażer[] pasażerowie;
    private final Linia[] linie;
    private final int liczbaDni;
    private final StatystykiDzienne[] statystykiDzienne;
    private StatystykiSumaryczne statystykiSumaryczne;

    public Symulator(int liczbaDni, Pojazd[] pojazdy, Przystanek[] przystanki,
                     Pasażer[] pasażerowie, Linia[] linie) {
        this.liczbaDni = liczbaDni;
        this.pojazdy = Arrays.copyOf(pojazdy, pojazdy.length);
        this.przystanki = Arrays.copyOf(przystanki, przystanki.length);
        this.pasażerowie = Arrays.copyOf(pasażerowie, pasażerowie.length);
        this.linie = Arrays.copyOf(linie, linie.length);
        this.statystykiDzienne = new StatystykiDzienne[liczbaDni];
    }

    public Symulator(DaneSymulacji dane) {
        this(dane.liczbaDni(), dane.pojazdy(), dane.przystanki(),
                dane.pasażerowie(), dane.linie());
    }

    public void symuluj() {
        for (int i = 0; i < liczbaDni; i++) {
            symulujDzień(i + 1);
            statystykiDzienne[i] = new StatystykiDzienne(i + 1, pojazdy, pasażerowie);
        }
        statystykiSumaryczne = new StatystykiSumaryczne(liczbaDni, pojazdy, pasażerowie);
    }

    private void symulujDzień(int numer_dnia) {
        KolejkaWydarzeń kolejka = new TablicowaKolejkaWydarzeń();

        for (Pasażer pasażer : pasażerowie) {
            pasażer.rozpocznijDzień(kolejka, numer_dnia);
        }

        for (Linia linia : linie) {
            linia.rozpocznijDzień(kolejka, numer_dnia);
        }

        while (!kolejka.pusta()) {
            Wydarzenie wydarzenie = kolejka.wyjmij();
            wydarzenie.symuluj(kolejka);
        }

        for (Pasażer pasażer : pasażerowie) {
            pasażer.zakończDzień(new Czas(numer_dnia, KONIEC_DNIA));
        }

        for (Przystanek przystanek : przystanki) {
            przystanek.zakończDzień();
        }

        for (Pojazd pojazd : pojazdy) {
            pojazd.zakończDzień();
        }
    }

    public void wypiszStatystyki() {
        for (Statystyki stat : statystykiDzienne) {
            System.out.println();
            stat.wypisz();
        }
        System.out.println();
        statystykiSumaryczne.wypisz();
    }
}