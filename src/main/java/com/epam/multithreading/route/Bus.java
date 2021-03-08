package com.epam.multithreading.route;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;


public class Bus implements Runnable {

    private static final Logger LOGGER = LogManager.getLogger(Bus.class);

    private int busNumber;
    private int busPassengers;
    private List<Integer> routePlan;

    @JsonCreator
    public Bus(@JsonProperty("busNumber") int busNumber,
               @JsonProperty("busPassengers") int busPassengers,
               @JsonProperty("routePlan") List<Integer> routePlan) {
        this.busNumber = busNumber;
        this.busPassengers = busPassengers;
        this.routePlan = routePlan;
    }

    @Override
    public void run() {

        BusStopsPool busStopsPool = BusStopsPool.INSTANCE;

        // the bus makes stops according to the route plan
        for (Integer busStopNumber : routePlan) {
            LOGGER.debug("The BUS No." + getBusNumber() + " DRIVES UP to the BUS STOP No.-----> " + busStopNumber);

            busStopsPool.getBusStop(busStopNumber).makeStop(this);
        }
        LOGGER.debug("The BUS No." + getBusNumber() + " FINISHED the route!");
    }

    public int getBusNumber() {
        return busNumber;
    }

    public int getBusPassengers() {
        return busPassengers;
    }

    public void setBusPassengers(int busPassengers) {
        this.busPassengers = busPassengers;
    }

}
