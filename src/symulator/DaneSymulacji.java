package symulator;

import main.RandomNumberGenerator;

import java.util.Arrays;
import java.util.Scanner;

public class DaneSymulacji {
    private Pojazd[] pojazdy;
    private Przystanek[] przystanki;
    private Pasażer[] pasażerowie;
    private Linia[] linie;
    private int liczbaDni;
    private int pojemnośćPrzystanku;
    private int pojemnośćTramwaju;

    public DaneSymulacji() {
        pojazdy = new Pojazd[0];
        przystanki = new Przystanek[0];
        pasażerowie = new Pasażer[0];
        linie = new Linia[0];
    }

    public Pojazd[] pojazdy() {
        return pojazdy;
    }

    public Przystanek[] przystanki() {
        return przystanki;
    }

    public Pasażer[] pasażerowie() {
        return pasażerowie;
    }

    public Linia[] linie() {
        return linie;
    }

    public int liczbaDni() {
        return liczbaDni;
    }

    public void wczytaj() {
        Scanner scanner = new Scanner(System.in);

        liczbaDni = scanner.nextInt();

        pojemnośćPrzystanku = scanner.nextInt();
        int liczbaPrzystanków = scanner.nextInt();
        przystanki = new Przystanek[liczbaPrzystanków];
        for (int i = 0; i < liczbaPrzystanków; i++) {
            String nazwa = scanner.next();
            przystanki[i] = new Przystanek(nazwa, pojemnośćPrzystanku);
        }
        Arrays.sort(przystanki);

        int liczbaPasażerów = scanner.nextInt();
        pasażerowie = new Pasażer[liczbaPasażerów];
        for (int i = 0; i < liczbaPasażerów; i++) {
            pasażerowie[i] = new Pasażer(
                    przystanki[RandomNumberGenerator.rand(0, liczbaPrzystanków - 1)]);
        }

        pojemnośćTramwaju = scanner.nextInt();
        int liczbaLinii = scanner.nextInt();
        linie = new Linia[liczbaLinii];
        for (int i = 0; i < liczbaLinii; i++) {
            int liczbaTramwajów = scanner.nextInt();
            int długośćTrasy = scanner.nextInt();

            Przystanek[] przystankiNaTrasie = new Przystanek[długośćTrasy];
            int[] czasyPrzejazdów = new int[długośćTrasy];

            for (int j = 0; j < długośćTrasy; j++) {
                String nazwa = scanner.next();
                czasyPrzejazdów[j] = scanner.nextInt();
                przystankiNaTrasie[j] = znajdźPrzystanek(nazwa);
            }
            linie[i] = new Linia(i, new Trasa(przystankiNaTrasie, czasyPrzejazdów));

            int początek = pojazdy.length;
            pojazdy = Arrays.copyOf(pojazdy, pojazdy.length + liczbaTramwajów);
            for (int j = 0; j < liczbaTramwajów; j++) {
                pojazdy[początek + j] = new Tramwaj(linie[i], pojemnośćTramwaju);
            }
        }
        scanner.close();
    }

    public void wypisz() {
        System.out.println("Liczba dni: " + liczbaDni);
        System.out.println("Pojemność przystanku: " + pojemnośćPrzystanku);
        System.out.println("Liczba przystanków: " + przystanki.length);
        if (przystanki.length > 0) {
            System.out.println("Przystanki: ");
        }
        for (Przystanek przystanek : przystanki) {
            przystanek.wypiszDane();
            System.out.println();
        }
        System.out.println("Liczba pasażerów: " + pasażerowie.length);
        System.out.println("Pojemność tramwaju: " + pojemnośćTramwaju);
        System.out.println("Liczba linii: " + linie.length);
        for (int i = 0; i < linie.length; i++) {
            System.out.println("Linia " + i + ": ");
            linie[i].wypiszDane();
            System.out.println();
        }
    }

    private Przystanek znajdźPrzystanek(String nazwa) {
        Przystanek szukany = new Przystanek(nazwa, liczbaDni);
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
