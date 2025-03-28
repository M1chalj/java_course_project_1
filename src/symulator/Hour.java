package symulator;

public class Hour implements Comparable<Hour> {
    private static final int HOUR_IN_MINUTES = 60;

    private final int hour;
    private final int minute;

    public Hour(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }

    public Hour(int minutes) {
        hour = minutes / HOUR_IN_MINUTES;
        minute = minutes % HOUR_IN_MINUTES;
    }

    @Override
    public String toString() {
        return (hour < 10 ? "0" : "") + hour + ":" + (minute < 10 ? "0" : "") + minute;
    }

    @Override
    public int compareTo(Hour g) {
        if (hour != g.hour) {
            return hour < g.hour ? -1 : 1;
        }
        if (minute != g.minute) {
            return minute < g.minute ? -1 : 1;
        }
        return 0;
    }

    public int toMinutes() {
        return hour * HOUR_IN_MINUTES + minute;
    }

    public Hour add(int minutes) {
        return new Hour(toMinutes() + minutes);
    }

    public int difference(Time g) {
        return toMinutes() - g.toMinutes();
    }
}
