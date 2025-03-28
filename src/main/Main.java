package main;

import symulator.DaneSymulacji;
import symulator.Symulator;

public class Main {
    public static void main(String[] args) {
        DaneSymulacji dane = new DaneSymulacji();
        dane.wczytaj();
        dane.wypisz();
        System.out.println();
        Symulator symulator = new Symulator(dane);
        symulator.symuluj();
        symulator.wypiszStatystyki();
    }
}
