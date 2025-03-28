package symulator;

public class StatystykiDzienne extends Statystyki {

    private final int numerDnia;

    public StatystykiDzienne(int numerDnia, Pojazd[] pojazdy, Pasażer[] pasażerowie) {
        this.numerDnia = numerDnia;

        int czasOczekiwania = 0;
        int liczbaCzekających = 0;
        int liczbaPrzejazdów = 0;

        for (Pojazd pojazd : pojazdy) {
            liczbaPrzejazdów += pojazd.dziennaLiczbaPrzejazdów();
        }

        for (Pasażer pasażer : pasażerowie) {
            czasOczekiwania += pasażer.dziennyCzasCzekania();
            liczbaCzekających += pasażer.ileRazyDziśCzekałNaPrzystanku();
        }

        ustaw(czasOczekiwania, liczbaCzekających, liczbaPrzejazdów);
    }

    @Override
    public void wypisz() {
        System.out.println("Statystyki - dzień " + numerDnia + ":");
        super.wypisz();
    }
}
