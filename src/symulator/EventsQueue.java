package symulator;

public interface EventsQueue {
    void add(Event w);

    Event get();

    boolean empty();
}