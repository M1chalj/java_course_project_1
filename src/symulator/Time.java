package symulator;

public class Time implements Comparable<Time> {
    private static final int DAY_IN_MINUTES = 24 * 60;

    private final int day;
    private final Hour hour;

    public Time(int day, Hour hour) {
        this.day = day;
        this.hour = hour;
    }

    public Time(int minutes) {
        day = minutes / DAY_IN_MINUTES;
        hour = new Hour(minutes % DAY_IN_MINUTES);
    }

    @Override
    public String toString() {
        return day + ", " + hour;
    }

    @Override
    public int compareTo(Time g) {
        if (day != g.day) {
            return day < g.day ? -1 : 1;
        }
        return hour.compareTo(g.hour);
    }

    public int day() {
        return day;
    }

    public int toMinutes() {
        return day * DAY_IN_MINUTES + hour.toMinutes();
    }

    public Time add(int minutes) {
        return new Time(toMinutes() + minutes);
    }

    public int difference(Time g) {
        return toMinutes() - g.toMinutes();
    }
}
