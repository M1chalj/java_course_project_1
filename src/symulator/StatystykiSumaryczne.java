package symulator;

public class StatystykiSumaryczne extends Statystyki {

    private final int liczbaDni;

    public StatystykiSumaryczne(int liczbaDni, Pojazd[] pojazdy, Pasażer[] pasażerowie) {

        this.liczbaDni = liczbaDni;

        int czasOczekiwania = 0;
        int liczbaCzekających = 0;
        int liczbaPrzejazdów = 0;

        for (Pojazd pojazd : pojazdy) {
            liczbaPrzejazdów += pojazd.sumarycznaLiczbaPrzejazdów();
        }

        for (Pasażer pasażer : pasażerowie) {
            czasOczekiwania += pasażer.sumarycznyCzasCzekania();
            liczbaCzekających += pasażer.ileRazySumarycznieCzekałNaPrzystanku();
        }

        ustaw(czasOczekiwania, liczbaCzekających, liczbaPrzejazdów);
    }

    @Override
    public void wypisz() {
        System.out.println("Statystyki sumaryczne z " + liczbaDni + " dni:");
        super.wypisz();
        double średniaLiczbaPrzejazdów = liczbaPrzejazdów();
        if (liczbaDni != 0) {
            średniaLiczbaPrzejazdów /= liczbaDni;
        }
        System.out.println("Średnia liczba przejazdów jednego dnia: "
                + String.format("%.2f", średniaLiczbaPrzejazdów));
    }
}
