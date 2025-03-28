package symulator;

public abstract class Event implements Comparable<Event> {
    private final Time time;

    public Event(Time time) {
        this.time = time;
    }

    @Override
    public int compareTo(Event w) {
        return time.compareTo(w.time);
    }

    protected Time time() {
        return time;
    }

    public abstract void simulate(EventsQueue queue);
}
