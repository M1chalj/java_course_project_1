package symulator;

public class PasażerWychodziZDomu extends Wydarzenie {
    final Pasażer pasażer;

    public PasażerWychodziZDomu(Czas czas, Pasażer pasażer) {
        super(czas);
        this.pasażer = pasażer;
    }

    @Override
    public void symuluj(KolejkaWydarzeń kolejka) {
        pasażer.wyjdźZDomu(czas());
    }
}
