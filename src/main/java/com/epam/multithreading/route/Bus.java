package com.epam.multithreading.route;

import java.util.List;


public class Bus implements Runnable {

    private int busNumber;
    private int passengers;
    private List<Integer> routePlan;

    public Bus(int busNumber, int passengers, List<Integer> routePlan) {
        this.busNumber = busNumber;
        this.passengers = passengers;
        this.routePlan = routePlan;
    }

    @Override
    public void run() {
        BusStopsPool busStopsPool = BusStopsPool.INSTANCE; // the bus got bus stops

        for (Integer busStopNumber : routePlan) { // the bus makes stops according to the route plan

            System.out.println("The BUS No." + getBusNumber() + " drives up to the BUS STOP No.-----> " + busStopNumber);

            busStopsPool.getBusStop(busStopNumber).makeStop(this);
        }

        System.out.println("The BUS No." + getBusNumber() + " FINISHED THE ROUTE!____________");
    }

    public int getBusNumber() {
        return busNumber;
    }

    public void setBusNumber(int busNumber) {
        this.busNumber = busNumber;
    }

    public int getPassengers() {
        return passengers;
    }

    public void setPassengers(int passengers) {
        this.passengers = passengers;
    }

}
