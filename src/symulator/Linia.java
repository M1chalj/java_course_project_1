package symulator;

import java.util.Arrays;

public class Linia {
    private final static Godzina PIERWSZY_WYJAZD = new Godzina(6, 0);
    private final static boolean START_Z_PIERWSZEGO_KOŃCA = true;
    private final static boolean START_Z_DRUGIEGO_KOŃCA = false;

    private final Trasa trasa;
    private final int numer;

    private Pojazd[] pojazdy;
    private int ilePojazdów;

    public Linia(int numer, Trasa trasa) {
        this.numer = numer;
        this.trasa = trasa;
        pojazdy = new Pojazd[1];
        ilePojazdów = 0;
    }

    @Override
    public String toString() {
        return "linia " + numer;
    }

    public Trasa trasa() {
        return trasa;
    }

    public void dodajPojazd(Pojazd p) {
        if (pojazdy.length == ilePojazdów) {
            pojazdy = Arrays.copyOf(pojazdy, pojazdy.length * 2);
        }
        pojazdy[ilePojazdów++] = p;
    }

    public void rozpocznijDzień(KolejkaWydarzeń kolejka, int dzień) {
        if (ilePojazdów == 0) {
            return;
        }

        int odstęp = Math.ceilDiv(trasa.całkowityCzasPrzejazdu(), ilePojazdów);

        Godzina godzinaWyjazdu = PIERWSZY_WYJAZD;
        for (int i = 0; i < ilePojazdów / 2; i++) {
            pojazdy[i].rozpocznijDzień(kolejka, dzień, START_Z_DRUGIEGO_KOŃCA, godzinaWyjazdu);
            godzinaWyjazdu = godzinaWyjazdu.dodaj(odstęp);
        }

        godzinaWyjazdu = PIERWSZY_WYJAZD;
        for (int i = ilePojazdów / 2; i < ilePojazdów; i++) {
            pojazdy[i].rozpocznijDzień(kolejka, dzień, START_Z_PIERWSZEGO_KOŃCA, godzinaWyjazdu);
            godzinaWyjazdu = godzinaWyjazdu.dodaj(odstęp);
        }
    }

    public void wypiszDane() {
        System.out.println("Liczba tramwajów na linii: " + ilePojazdów);
        System.out.println("Trasa: ");
        trasa.wypiszDane();
    }
}
