package com.epam.multithreading.route;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RouteRunner {
    //private static final String DATA = "input.json";

    public static void main(String[] args) {
        BusStop busStop1001 = new BusStop(1001, 2, 15);
        BusStop busStop2002 = new BusStop(2002, 3, 9);
        BusStop busStop3003 = new BusStop(3003, 2, 17);
        BusStop busStop4004 = new BusStop(4004, 2, 25);
        BusStop busStop5005 = new BusStop(5005, 3, 7);

        Map<Integer, BusStop> busStops = new HashMap<>();
        busStops.put(busStop1001.getBusStopNumber(), busStop1001);
        busStops.put(busStop2002.getBusStopNumber(), busStop2002);
        busStops.put(busStop3003.getBusStopNumber(), busStop3003);
        busStops.put(busStop4004.getBusStopNumber(), busStop4004);
        busStops.put(busStop5005.getBusStopNumber(), busStop5005);

        BusStopsPool busStopsPool = BusStopsPool.INSTANCE;
        busStopsPool.setBusStopsPool(busStops);

        List<Bus> buses = new ArrayList<>(Arrays.asList(
                new Bus(1, 5, new ArrayList<>(Arrays.asList(1001, 2002))),
                new Bus(2, 6, new ArrayList<>(Arrays.asList(4004, 5005, 3003))),
                new Bus(3, 8, new ArrayList<>(Arrays.asList(1001, 2002, 3003, 2002))),
                new Bus(4, 5, new ArrayList<>(Arrays.asList(1001, 5005, 4004, 5005, 2002))),
                new Bus(5, 6, new ArrayList<>(Arrays.asList(4004, 5005, 3003, 2002, 5005, 1001))),
                new Bus(6, 8, new ArrayList<>(Arrays.asList(1001, 2002, 3003, 5005))),
                new Bus(7, 5, new ArrayList<>(Arrays.asList(1001, 5005, 2002, 3003))),
                new Bus(8, 6, new ArrayList<>(Arrays.asList(4004, 5005, 3003))),
                new Bus(9, 8, new ArrayList<>(Arrays.asList(1001, 2002)))
        ));

        int numberOfBuses = buses.size();

        ExecutorService executorService = Executors.newFixedThreadPool(numberOfBuses);
        for (Bus bus : buses) {
            executorService.submit(bus);
            System.out.println("Bus No. " + bus.getBusNumber() + " started");
        }
        executorService.shutdown();
    }

}
