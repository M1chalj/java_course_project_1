package symulator;

public interface PassengersSet {
    void add(Passenger passenger);

    Passenger get();

    boolean empty();

    int size();

    void clear();
}
