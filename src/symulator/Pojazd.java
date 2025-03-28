package symulator;

public abstract class Pojazd {
    private static final Godzina GODZINA_OSTATNIEGO_WYJAZDU = new Godzina(23, 0);
    private static int licznikPojazdów;

    private final int numerBoczny;
    private final Linia linia;

    private final int pojemność;
    private final ZbiórPasażerów[] pasażerowie;
    //pasażerowie[i] - zbiór pasażerów chcących wysiąść na i-tym przystanku

    private int ilePasażerów;

    private int przystanekPoczątkowy;
    private boolean aktualnyKierunek;
    private int aktualnyPrzystanek;

    private int dziennaLiczbaPrzejazdów;
    private int sumarycznaLiczbaPrzejazdów;

    public Pojazd(Linia linia, int pojemność) {
        this.linia = linia;
        this.pojemność = pojemność;
        numerBoczny = licznikPojazdów++;
        sumarycznaLiczbaPrzejazdów = 0;

        ilePasażerów = 0;
        pasażerowie = new TablicowyZbiórPasażerów[linia.trasa().liczbaPrzystanków()];
        for (int i = 0; i < pasażerowie.length; i++) {
            pasażerowie[i] = new TablicowyZbiórPasażerów();
        }

        linia.dodajPojazd(this);
    }

    @Override
    public String toString() {
        return linia.toString() + ", nr boczny " + numerBoczny;
    }

    public boolean jestMiejsce() {
        return ilePasażerów < pojemność;
    }

    public void dodajPasażera(Pasażer pasażer, int przystanek) {
        assert jestMiejsce() : "dodanie pasażera do pełnego pojazdu";
        pasażerowie[przystanek].dodaj(pasażer);
        ilePasażerów++;
        dziennaLiczbaPrzejazdów++;
    }

    private Pasażer usuńPasażera(int przystanek) {
        assert ilePasażerów > 0 : "usunięcie pasażera z pustego pojazdu";
        ilePasażerów--;
        return pasażerowie[przystanek].wyjmij();
    }

    public int ilePrzystankówDoKońca() {//zwraca liczbę pozostałych przystanków do pętli
        if (aktualnyKierunek) {
            return linia.trasa().liczbaPrzystanków() - aktualnyPrzystanek - 1;
        } else {
            return aktualnyPrzystanek;
        }
    }

    public int któryPrzystanekZa(int ile) {//zwraca numer przystanku za <ile> przystanków
        assert ile < ilePrzystankówDoKońca() : "Niepoprawny argument";
        return aktualnyPrzystanek + (aktualnyKierunek ? ile : -ile);
    }

    public Przystanek przystanek(int numer) {
        return linia.trasa().przystanek(numer);
    }

    public void rozpocznijDzień(KolejkaWydarzeń kolejka, int dzień,
                                boolean kierunek, Godzina godzinaWyjazdu) {
        przystanekPoczątkowy = linia.trasa().przystanekPoczątkowy(kierunek);
        aktualnyPrzystanek = przystanekPoczątkowy;
        aktualnyKierunek = kierunek;
        dziennaLiczbaPrzejazdów = 0;
        Czas czasPrzyjazdu = new Czas(dzień, godzinaWyjazdu);
        kolejka.dodaj(new PrzyjazdPojazdu(czasPrzyjazdu, this));
    }

    private void wypuśćPasażerów(Czas czas) {
        Przystanek przystanek = linia.trasa().przystanek(aktualnyPrzystanek);
        while (przystanek.jestMiejsce() && !pasażerowie[aktualnyPrzystanek].pusty()) {
            Pasażer pasażer = usuńPasażera(aktualnyPrzystanek);
            pasażer.wyjdźNaPrzystanek(czas, przystanek);
        }
    }

    private void wpuśćPasażerów(Czas czas) {
        Przystanek przystanek = linia.trasa().przystanek(aktualnyPrzystanek);
        while (jestMiejsce() && !przystanek.pusty()) {
            Pasażer pasażer = przystanek.usuńPasażera();
            pasażer.wejdźDoPojazdu(czas, this);
        }
    }

    public void przyjedźNaPrzystanek(KolejkaWydarzeń kolejka, Czas czas) {
        Trasa trasa = linia.trasa();

        System.out.println(czas + ": " + this + " przyjechał na " +
                trasa.przystanek(aktualnyPrzystanek));

        if (aktualnyPrzystanek != trasa.przystanekPoczątkowy(aktualnyKierunek)) {
            wypuśćPasażerów(czas);
        }
        if (aktualnyPrzystanek != trasa.przystanekKońcowy(aktualnyKierunek)) {
            wpuśćPasażerów(czas);
        }

        int następnyPrzystanek = trasa.następnyPrzystanek(aktualnyPrzystanek, aktualnyKierunek);
        Czas przyjazdNaNastępny = czas.dodaj(
                trasa.czasPrzejazduNaNastępny(aktualnyPrzystanek, aktualnyKierunek));

        if (aktualnyPrzystanek != trasa.przystanekKońcowy(aktualnyKierunek) ||
                aktualnyPrzystanek != przystanekPoczątkowy ||
                przyjazdNaNastępny.compareTo(
                        new Czas(czas.dzień(), GODZINA_OSTATNIEGO_WYJAZDU)) <= 0) {
            kolejka.dodaj(new PrzyjazdPojazdu(przyjazdNaNastępny, this));
        }

        if (aktualnyPrzystanek == trasa.przystanekKońcowy(aktualnyKierunek)) {
            aktualnyKierunek = !aktualnyKierunek;
        }
        aktualnyPrzystanek = następnyPrzystanek;
    }

    public void zakończDzień() {
        sumarycznaLiczbaPrzejazdów += dziennaLiczbaPrzejazdów;
        ilePasażerów = 0;
        for (ZbiórPasażerów stos : pasażerowie) {
            stos.opróżnij();
        }
    }

    public int dziennaLiczbaPrzejazdów() {
        return dziennaLiczbaPrzejazdów;
    }

    public int sumarycznaLiczbaPrzejazdów() {
        return sumarycznaLiczbaPrzejazdów;
    }
}
