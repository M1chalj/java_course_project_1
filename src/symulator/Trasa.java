package symulator;

import java.util.Arrays;

public class Trasa {
    private final Przystanek[] przystanki;
    private final int[] czasy;
    private final int całkowityCzasPrzejazdu;

    public Trasa(Przystanek[] przystanki, int[] czasy) {
        assert przystanki.length == czasy.length : "niepoprawne tablice wejściowe";

        this.przystanki = Arrays.copyOf(przystanki, przystanki.length);
        this.czasy = Arrays.copyOf(czasy, czasy.length);

        int czasPrzejazdu = 0;
        for (int j : czasy) {
            czasPrzejazdu += j;
        }
        this.całkowityCzasPrzejazdu = czasPrzejazdu * 2;
    }

    public Przystanek przystanek(int n) {
        return przystanki[n];
    }

    public int przystanekPoczątkowy(boolean kierunek) {
        return kierunek ? 0 : liczbaPrzystanków() - 1;
    }

    public int przystanekKońcowy(boolean kierunek) {
        return kierunek ? liczbaPrzystanków() - 1 : 0;
    }

    public int liczbaPrzystanków() {
        return przystanki.length;
    }

    public int całkowityCzasPrzejazdu() {
        return całkowityCzasPrzejazdu;
    }

    public int czasPrzejazduNaNastępny(int aktualnyPrzystanek, boolean kierunek) {
        if (kierunek) {
            return czasy[aktualnyPrzystanek];
        } else {
            if (aktualnyPrzystanek == 0) {
                return czasy[czasy.length - 1];
            } else {
                return czasy[aktualnyPrzystanek - 1];
            }
        }
    }

    public int następnyPrzystanek(int przystanek, boolean kierunek) {
        if (przystanek == przystanekKońcowy(kierunek)) {
            return przystanek;
        } else {
            return przystanek + (kierunek ? 1 : -1);
        }
    }

    public void wypiszDane() {
        System.out.println("Liczba przystanków: " + liczbaPrzystanków());
        for (int i = 0; i < przystanki.length; i++) {
            if (i != 0) {
                System.out.print(" ");
            }
            przystanki[i].wypiszDane();
            System.out.print(" " + czasy[i]);
        }
    }
}
