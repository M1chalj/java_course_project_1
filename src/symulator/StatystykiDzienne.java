package symulator;

public class StatystykiDzienne extends Statystyki {

    private final int numerDnia;

    public StatystykiDzienne(int numerDnia, Vehicle[] pojazdy, Passenger[] pasażerowie) {
        this.numerDnia = numerDnia;

        int czasOczekiwania = 0;
        int liczbaCzekających = 0;
        int liczbaPrzejazdów = 0;

        for (Vehicle vehicle : pojazdy) {
            liczbaPrzejazdów += vehicle.dziennaLiczbaPrzejazdów();
        }

        for (Passenger passenger : pasażerowie) {
            czasOczekiwania += passenger.dziennyCzasCzekania();
            liczbaCzekających += passenger.ileRazyDziśCzekałNaPrzystanku();
        }

        ustaw(czasOczekiwania, liczbaCzekających, liczbaPrzejazdów);
    }

    @Override
    public void wypisz() {
        System.out.println("Statystyki - dzień " + numerDnia + ":");
        super.wypisz();
    }
}
