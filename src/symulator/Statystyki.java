package symulator;

public abstract class Statystyki {
    private int czasOczekiwania;
    private int liczbaCzekających;
    private int liczbaPrzejazdów;

    public void wypisz() {
        System.out.println("Sumaryczny czas oczekiwania na przystanku: "
                + czasOczekiwania + " min");
        double średniCzas = czasOczekiwania;
        if (liczbaCzekających != 0) {
            średniCzas /= liczbaCzekających;
        }
        System.out.println("Średni czas oczekiwania na przystanku: "
                + String.format("%.2f", średniCzas) + " min");
        System.out.println("Liczba przejazdów: " + liczbaPrzejazdów);
    }

    protected void ustaw(int czasOczekiwania, int liczbaCzekających, int liczbaPrzejazdów) {
        this.czasOczekiwania = czasOczekiwania;
        this.liczbaCzekających = liczbaCzekających;
        this.liczbaPrzejazdów = liczbaPrzejazdów;
    }

    protected int liczbaPrzejazdów() {
        return liczbaPrzejazdów;
    }
}
