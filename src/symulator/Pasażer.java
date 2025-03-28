package symulator;

import main.RandomNumberGenerator;

public class Pasażer {
    private static final Godzina WYJŚCIE_POCZĄTEK = new Godzina(6, 0);
    private static final Godzina WYJŚCIE_KONIEC = new Godzina(12, 0);
    private static int licznikPasażerów = 0;

    private final int numer;
    private final Przystanek przystanekStartowy;

    private Czas odKiedyCzeka;
    private int dziennyCzasCzekania;
    private int ileRazyDziśCzekałNaPrzystanku;
    private int sumarycznyCzasCzekania;
    private int ileRazySumarycznieCzekałNaPrzystanku;

    public Pasażer(Przystanek przystanekStartowy) {
        numer = licznikPasażerów++;
        this.przystanekStartowy = przystanekStartowy;
        sumarycznyCzasCzekania = 0;
        ileRazySumarycznieCzekałNaPrzystanku = 0;
    }

    void rozpocznijDzień(KolejkaWydarzeń kolejka, int dzień) {
        dziennyCzasCzekania = 0;
        odKiedyCzeka = null;
        ileRazyDziśCzekałNaPrzystanku = 0;
        Czas czasWyjścia = new Czas(dzień, new Godzina(
                RandomNumberGenerator.rand(WYJŚCIE_POCZĄTEK.naMinuty(), WYJŚCIE_KONIEC.naMinuty())));
        kolejka.dodaj(new PasażerWychodziZDomu(czasWyjścia, this));
    }

    @Override
    public String toString() {
        return "Pasażer nr " + numer;
    }

    public void wyjdźZDomu(Czas czas) {
        if (przystanekStartowy.jestMiejsce()) {
            wyjdźNaPrzystanek(czas, przystanekStartowy);
        } else {
            System.out.println(czas.toString() + " " + this +
                    " nie zmieścił się na swoim przystanku (" + przystanekStartowy + ") i wraca do domu");
        }
    }

    public void wyjdźNaPrzystanek(Czas czas, Przystanek przystanek) {
        ileRazyDziśCzekałNaPrzystanku++;
        odKiedyCzeka = czas;
        przystanek.dodajPasażera(this);
        System.out.println(czas + ": " + this + " wyszedł na " + przystanek);
    }

    public void wejdźDoPojazdu(Czas czas, Pojazd pojazd) {
        dziennyCzasCzekania += czas.różnica(odKiedyCzeka);
        odKiedyCzeka = null;
        int przystanekDocelowy = pojazd.któryPrzystanekZa(
                RandomNumberGenerator.rand(1, pojazd.ilePrzystankówDoKońca()));
        pojazd.dodajPasażera(this, przystanekDocelowy);
        System.out.println(czas + ": " + this + " wsiadł do pojazdu - " + pojazd +
                " - z zamiarem dojechania na " + pojazd.przystanek(przystanekDocelowy));
    }

    public void zakończDzień(Czas czas) {
        if (odKiedyCzeka != null) {
            dziennyCzasCzekania += czas.różnica(odKiedyCzeka);
        }
        sumarycznyCzasCzekania += dziennyCzasCzekania;
        ileRazySumarycznieCzekałNaPrzystanku += ileRazyDziśCzekałNaPrzystanku;
    }

    public int dziennyCzasCzekania() {
        return dziennyCzasCzekania;
    }

    public int sumarycznyCzasCzekania() {
        return sumarycznyCzasCzekania;
    }

    public int ileRazyDziśCzekałNaPrzystanku() {
        return ileRazyDziśCzekałNaPrzystanku;
    }

    public int ileRazySumarycznieCzekałNaPrzystanku() {
        return ileRazySumarycznieCzekałNaPrzystanku;
    }
}
