package symulator;

public abstract class Vehicle {
    private static final Hour LAST_DEPARTURE_HOUR = new Hour(23, 0);
    private static int VehiclesCounter;

    private final int number;
    private final Line line;

    private final int capacity;
    private final PassengersSet[] passengers;
    // passengers[i] = set of passengers who want to get off at stop i

    private int numberOfPassengers;

    private int firstStop;
    private boolean currentDirection;
    private int currentStop;

    private int dailyNumberOfRides;
    private int totalNumberOfRides;

    public Vehicle(Line line, int capacity) {
        this.line = line;
        this.capacity = capacity;
        number = VehiclesCounter++;
        totalNumberOfRides = 0;

        numberOfPassengers = 0;
        passengers = new PassengersArraySet[line.route().numberOfStops()];
        for (int i = 0; i < passengers.length; i++) {
            passengers[i] = new PassengersArraySet();
        }

        line.addVehicle(this);
    }

    @Override
    public String toString() {
        return line.toString() + ", no " + number;
    }

    public boolean isFreeSpace() {
        return numberOfPassengers < capacity;
    }

    public void addPassenger(Passenger passenger, int stop) {
        assert isFreeSpace() : "adding passenger to full vehicle";
        passengers[stop].add(passenger);
        numberOfPassengers++;
        dailyNumberOfRides++;
    }

    private Passenger deletePassenger(int stop) {
        assert numberOfPassengers > 0 : "deleting passenger from empty vehicle";
        numberOfPassengers--;
        return passengers[stop].get();
    }

    // returns number of stops to the end of the route in the current direction
    public int stopsToEnd() {
        if (currentDirection) {
            return line.route().numberOfStops() - currentStop - 1;
        } else {
            return currentStop;
        }
    }
    
    // returns number of stop after k stops
    public int whichStopAfter(int k) {
        assert k < stopsToEnd() : "Invalid argument";
        return currentStop + (currentDirection ? k : -k);
    }

    public Stop stop(int number) {
        return line.route().stop(number);
    }

    public void beginDay(EventsQueue queue, int day,
                         boolean direction, Hour departureHour) {
        firstStop = line.route().firstStop(direction);
        currentStop = firstStop;
        currentDirection = direction;
        dailyNumberOfRides = 0;
        Time arrivalTime = new Time(day, departureHour);
        queue.add(new VehicleArrival(arrivalTime, this));
    }

    private void letPassengersGo(Time time) {
        Stop stop = line.route().stop(currentStop);
        while (stop.isFreeSpace() && !passengers[currentStop].empty()) {
            Passenger passenger = deletePassenger(currentStop);
            passenger.goToStop(time, stop);
        }
    }

    private void letPassengersIn(Time time) {
        Stop stop = line.route().stop(currentStop);
        while (isFreeSpace() && !stop.empty()) {
            Passenger passenger = stop.deletePassenger();
            passenger.enterVehicle(time, this);
        }
    }

    public void arriveAtStop(EventsQueue queue, Time time) {
        Route route = line.route();

        System.out.println(time + ": " + this + " arrived at " +
                route.stop(currentStop));

        if (currentStop != route.firstStop(currentDirection)) {
            letPassengersGo(time);
        }
        if (currentStop != route.lastStop(currentDirection)) {
            letPassengersIn(time);
        }

        int nextStop = route.nextStop(currentStop, currentDirection);
        Time arrivalToNextStop = time.add(
                route.timeToNextStop(currentStop, currentDirection));

        if (currentStop != route.lastStop(currentDirection) ||
                currentStop != firstStop ||
                arrivalToNextStop.compareTo(
                        new Time(time.day(), LAST_DEPARTURE_HOUR)) <= 0) {
            queue.add(new VehicleArrival(arrivalToNextStop, this));
        }

        if (currentStop == route.lastStop(currentDirection)) {
            currentDirection = !currentDirection;
        }
        currentStop = nextStop;
    }

    public void endDay() {
        totalNumberOfRides += dailyNumberOfRides;
        numberOfPassengers = 0;
        for (PassengersSet stos : passengers) {
            stos.clear();
        }
    }

    public int dailyNumberOfRides() {
        return dailyNumberOfRides;
    }

    public int totalNumberOfRides() {
        return totalNumberOfRides;
    }
}
