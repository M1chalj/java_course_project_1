# Public Transport Simulation in Java

*This project was created as a first programming project for the Object-Oriented Programming Course at the University of Warsaw, Faculty of Mathematics, Informatics, and Mechanics. Due to its introductory nature, the use of standard libraries was limited.*

*Polish description of the project is available in the Treść\_pl.pdf file.Project Overview*

## Overview

This project is a simplified simulation of urban public transport, focusing on tram operations and passenger behaviors. It is developed in Java with an emphasis on object-oriented programming principles, allowing easy extensibility.

## Features

- **Entities in the simulation:**
    - Passengers
    - Trams (Vehicles)
    - Tram lines
    - Stops
- **Simulation Mechanics:**
    - Trams follow predefined routes with designated stops.
    - Passengers board and disembark trams based on random travel preferences.
    - Event-driven simulation using a priority queue.
    - Logs detailed events and provides statistics on passenger travel times.

## Input Format

Simulation data is read from standard input. The expected input format:

```
<number_of_days>
<stop_capacity>
<number_of_stops>
<stop_name> <passenger_count>  # Repeated for each stop
<tram_capacity>
<number_of_tram_lines>
<trams_per_line> <route_length>
<stop_name> <travel_time>  # Repeated for each stop on the route
```

## Output

The program outputs:

1. **Loaded Parameters**
2. **Event Log:** Each event is timestamped with the simulation time.
3. **Simulation Statistics:** Includes total passenger trips and average waiting times.

