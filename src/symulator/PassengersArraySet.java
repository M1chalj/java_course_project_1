package symulator;

import java.util.Arrays;

public class PassengersArraySet implements PassengersSet {

    private Passenger[] array;
    private int cnt;

    public PassengersArraySet(int capacity) {
        array = new Passenger[capacity];
        cnt = 0;
    }

    public PassengersArraySet() {
        this(0);
    }

    @Override
    public void add(Passenger passenger) {
        if (array.length == cnt) {
            extend();
        }
        array[cnt++] = passenger;
    }

    @Override
    public Passenger get() {
        assert !empty() : "Get from empty set";
        Passenger passenger = array[--cnt];
        if (cnt * 2 < array.length / 2) {
            shrink();
        }
        return passenger;
    }

    @Override
    public boolean empty() {
        return cnt == 0;
    }

    @Override
    public int size() {
        return cnt;
    }

    @Override
    public void clear() {
        cnt = 0;
        array = new Passenger[0];
    }

    private void extend() {
        array = Arrays.copyOf(array, Math.max(2 * array.length, 1));
    }

    private void shrink() {
        array = Arrays.copyOf(array, cnt * 2);
    }
}
