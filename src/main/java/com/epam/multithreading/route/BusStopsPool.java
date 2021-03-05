package com.epam.multithreading.route;

import java.util.Map;


public enum BusStopsPool {

    INSTANCE;

    private Map<Integer, BusStop> busStopsPool;

    public BusStop getBusStop(int busStopNumber) {
        return busStopsPool.get(busStopNumber);
    }

    public void setBusStopsPool(Map<Integer, BusStop> busStopsPool) {
        this.busStopsPool = busStopsPool;
    }


}
