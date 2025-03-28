package symulator;

public abstract class Vehicle {
    private static final Hour LAST_DEPARTURE_HOUR = new Hour(23, 0);
    private static int licznikPojazdów;

    private final int numerBoczny;
    private final Line line;

    private final int pojemność;
    private final ZbiórPasażerów[] pasażerowie;
    //pasażerowie[i] - zbiór pasażerów chcących wysiąść na i-tym przystanku

    private int ilePasażerów;

    private int przystanekPoczątkowy;
    private boolean aktualnyKierunek;
    private int aktualnyPrzystanek;

    private int dziennaLiczbaPrzejazdów;
    private int sumarycznaLiczbaPrzejazdów;

    public Vehicle(Line line, int pojemność) {
        this.line = line;
        this.pojemność = pojemność;
        numerBoczny = licznikPojazdów++;
        sumarycznaLiczbaPrzejazdów = 0;

        ilePasażerów = 0;
        pasażerowie = new TablicowyZbiórPasażerów[line.route().liczbaPrzystanków()];
        for (int i = 0; i < pasażerowie.length; i++) {
            pasażerowie[i] = new TablicowyZbiórPasażerów();
        }

        line.addVehicle(this);
    }

    @Override
    public String toString() {
        return line.toString() + ", nr boczny " + numerBoczny;
    }

    public boolean jestMiejsce() {
        return ilePasażerów < pojemność;
    }

    public void dodajPasażera(Passenger passenger, int przystanek) {
        assert jestMiejsce() : "dodanie pasażera do pełnego pojazdu";
        pasażerowie[przystanek].dodaj(passenger);
        ilePasażerów++;
        dziennaLiczbaPrzejazdów++;
    }

    private Passenger usuńPasażera(int przystanek) {
        assert ilePasażerów > 0 : "usunięcie pasażera z pustego pojazdu";
        ilePasażerów--;
        return pasażerowie[przystanek].wyjmij();
    }

    public int ilePrzystankówDoKońca() {//zwraca liczbę pozostałych przystanków do pętli
        if (aktualnyKierunek) {
            return line.route().liczbaPrzystanków() - aktualnyPrzystanek - 1;
        } else {
            return aktualnyPrzystanek;
        }
    }

    public int któryPrzystanekZa(int ile) {//zwraca numer przystanku za <ile> przystanków
        assert ile < ilePrzystankówDoKońca() : "Niepoprawny argument";
        return aktualnyPrzystanek + (aktualnyKierunek ? ile : -ile);
    }

    public Stop przystanek(int numer) {
        return line.route().przystanek(numer);
    }

    public void beginDay(EventsQueue kolejka, int dzień,
                         boolean kierunek, Hour departureHour) {
        przystanekPoczątkowy = line.route().przystanekPoczątkowy(kierunek);
        aktualnyPrzystanek = przystanekPoczątkowy;
        aktualnyKierunek = kierunek;
        dziennaLiczbaPrzejazdów = 0;
        Time timePrzyjazdu = new Time(dzień, departureHour);
        kolejka.add(new PrzyjazdPojazdu(timePrzyjazdu, this));
    }

    private void wypuśćPasażerów(Time time) {
        Stop stop = line.route().przystanek(aktualnyPrzystanek);
        while (stop.isFreeSpace() && !pasażerowie[aktualnyPrzystanek].pusty()) {
            Passenger passenger = usuńPasażera(aktualnyPrzystanek);
            passenger.goToStop(time, stop);
        }
    }

    private void wpuśćPasażerów(Time time) {
        Stop stop = line.route().przystanek(aktualnyPrzystanek);
        while (jestMiejsce() && !stop.pusty()) {
            Passenger passenger = stop.usuńPasażera();
            passenger.enterVehicle(time, this);
        }
    }

    public void przyjedźNaPrzystanek(EventsQueue kolejka, Time time) {
        Route route = line.route();

        System.out.println(time + ": " + this + " przyjechał na " +
                route.przystanek(aktualnyPrzystanek));

        if (aktualnyPrzystanek != route.przystanekPoczątkowy(aktualnyKierunek)) {
            wypuśćPasażerów(time);
        }
        if (aktualnyPrzystanek != route.przystanekKońcowy(aktualnyKierunek)) {
            wpuśćPasażerów(time);
        }

        int następnyPrzystanek = route.następnyPrzystanek(aktualnyPrzystanek, aktualnyKierunek);
        Time przyjazdNaNastępny = time.add(
                route.czasPrzejazduNaNastępny(aktualnyPrzystanek, aktualnyKierunek));

        if (aktualnyPrzystanek != route.przystanekKońcowy(aktualnyKierunek) ||
                aktualnyPrzystanek != przystanekPoczątkowy ||
                przyjazdNaNastępny.compareTo(
                        new Time(time.day(), LAST_DEPARTURE_HOUR)) <= 0) {
            kolejka.add(new PrzyjazdPojazdu(przyjazdNaNastępny, this));
        }

        if (aktualnyPrzystanek == route.przystanekKońcowy(aktualnyKierunek)) {
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
