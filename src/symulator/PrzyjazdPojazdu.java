package symulator;

public class PrzyjazdPojazdu extends Wydarzenie {

    private final Pojazd pojazd;

    public PrzyjazdPojazdu(Czas czas, Pojazd pojazd) {
        super(czas);
        this.pojazd = pojazd;
    }

    @Override
    public void symuluj(KolejkaWydarzeń kolejka) {
        pojazd.przyjedźNaPrzystanek(kolejka, czas());
    }
}
