package symulator;

import main.RandomNumberGenerator;

import java.util.Arrays;
import java.util.Scanner;

public class SimulationData {
    private Vehicle[] pojazdy;
    private Stop[] przystanki;
    private Passenger[] pasażerowie;
    private Line[] linie;
    private int liczbaDni;
    private int pojemnośćPrzystanku;
    private int pojemnośćTramwaju;

    public SimulationData() {
        pojazdy = new Vehicle[0];
        przystanki = new Stop[0];
        pasażerowie = new Passenger[0];
        linie = new Line[0];
    }

    public Vehicle[] pojazdy() {
        return pojazdy;
    }

    public Stop[] przystanki() {
        return przystanki;
    }

    public Passenger[] pasażerowie() {
        return pasażerowie;
    }

    public Line[] linie() {
        return linie;
    }

    public int liczbaDni() {
        return liczbaDni;
    }

    public void read() {
        Scanner scanner = new Scanner(System.in);

        liczbaDni = scanner.nextInt();

        pojemnośćPrzystanku = scanner.nextInt();
        int liczbaPrzystanków = scanner.nextInt();
        przystanki = new Stop[liczbaPrzystanków];
        for (int i = 0; i < liczbaPrzystanków; i++) {
            String nazwa = scanner.next();
            przystanki[i] = new Stop(nazwa, pojemnośćPrzystanku);
        }
        Arrays.sort(przystanki);

        int liczbaPasażerów = scanner.nextInt();
        pasażerowie = new Passenger[liczbaPasażerów];
        for (int i = 0; i < liczbaPasażerów; i++) {
            pasażerowie[i] = new Passenger(
                    przystanki[RandomNumberGenerator.rand(0, liczbaPrzystanków - 1)]);
        }

        pojemnośćTramwaju = scanner.nextInt();
        int liczbaLinii = scanner.nextInt();
        linie = new Line[liczbaLinii];
        for (int i = 0; i < liczbaLinii; i++) {
            int liczbaTramwajów = scanner.nextInt();
            int długośćTrasy = scanner.nextInt();

            Stop[] przystankiNaTrasie = new Stop[długośćTrasy];
            int[] czasyPrzejazdów = new int[długośćTrasy];

            for (int j = 0; j < długośćTrasy; j++) {
                String nazwa = scanner.next();
                czasyPrzejazdów[j] = scanner.nextInt();
                przystankiNaTrasie[j] = znajdźPrzystanek(nazwa);
            }
            linie[i] = new Line(i, new Route(przystankiNaTrasie, czasyPrzejazdów));

            int początek = pojazdy.length;
            pojazdy = Arrays.copyOf(pojazdy, pojazdy.length + liczbaTramwajów);
            for (int j = 0; j < liczbaTramwajów; j++) {
                pojazdy[początek + j] = new Tramwaj(linie[i], pojemnośćTramwaju);
            }
        }
        scanner.close();
    }

    public void print() {
        System.out.println("Liczba dni: " + liczbaDni);
        System.out.println("Pojemność przystanku: " + pojemnośćPrzystanku);
        System.out.println("Liczba przystanków: " + przystanki.length);
        if (przystanki.length > 0) {
            System.out.println("Przystanki: ");
        }
        for (Stop stop : przystanki) {
            stop.wypiszDane();
            System.out.println();
        }
        System.out.println("Liczba pasażerów: " + pasażerowie.length);
        System.out.println("Pojemność tramwaju: " + pojemnośćTramwaju);
        System.out.println("Liczba linii: " + linie.length);
        for (int i = 0; i < linie.length; i++) {
            System.out.println("Linia " + i + ": ");
            linie[i].printData();
            System.out.println();
        }
    }

    private Stop znajdźPrzystanek(String nazwa) {
        Stop szukany = new Stop(nazwa, liczbaDni);
        int lewy = 0;
        int prawy = przystanki.length - 1;
        while (lewy < prawy) {
            int środek = (lewy + prawy + 1) / 2;
            if (przystanki[środek].compareTo(szukany) <= 0) {
                lewy = środek;
            } else {
                prawy = środek - 1;
            }
        }
        assert szukany.compareTo(przystanki[lewy]) == 0 : "Nieznana nazwa przystanku";
        return przystanki[lewy];
    }
}
