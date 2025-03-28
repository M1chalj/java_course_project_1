package symulator;

import java.util.Arrays;

public class EventsArrayQueue implements EventsQueue {

    Event[] array;
    int begin;
    int end;

    public EventsArrayQueue() {
        array = new Event[0];
        begin = 0;
        end = 0;
    }

    @Override
    public void add(Event w) {
        if (end == array.length) {
            extend();
        }
        array[end++] = w;

        int i = end - 1;
        while (i > 0 && array[i].compareTo(array[i - 1]) < 0) {
            Event temp = array[i - 1];
            array[i - 1] = array[i];
            array[i] = temp;
            i--;
        }
    }

    @Override
    public Event get() {
        assert !empty() : "Get from empty queue";
        Event event = array[begin++];
        if (begin >= array.length / 2) {
            rebuild();
        }
        return event;
    }

    @Override
    public boolean empty() {
        return begin == end;
    }

    private void extend() {
        array = Arrays.copyOf(array, Math.max(2 * array.length, 1));
    }

    /**
     * Rebuilds the array to remove empty spaces at the beginning and shrink the array.
     * This is called when the number of elements in the queue is less than half of the array size.
     */
    private void rebuild() {
        for (int i = 0; i < end - begin; i++) {
            array[i] = array[begin + i];
        }
        end -= begin;
        begin = 0;
        array = Arrays.copyOf(array, end * 2);
    }
}
