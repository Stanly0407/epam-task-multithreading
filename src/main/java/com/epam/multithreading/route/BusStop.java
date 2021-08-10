package com.epam.multithreading.route;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class BusStop {

    private static final Logger LOGGER = LogManager.getLogger(BusStop.class);

    private int busStopNumber;
    private int busStopPassengers;
    private Semaphore busStopSemaphore;
    private ReentrantLock locking = new ReentrantLock(true);

    @JsonCreator
    public BusStop(@JsonProperty("busStopNumber") int busStopNumber,
                   @JsonProperty("maxAvailableBusCapacity") int maxAvailableBusCapacity,
                   @JsonProperty("busStopPassengers") int busStopPassengers) {
        this.busStopNumber = busStopNumber;
        this.busStopPassengers = busStopPassengers;
        this.busStopSemaphore = new Semaphore(maxAvailableBusCapacity, true);
    }

    public void makeStop(Bus bus) {
        try {
            busStopSemaphore.acquire();

            LOGGER.debug("The Bus No. " + bus.getBusNumber() + " pull into the bus stop No. " + busStopNumber);

            transferPassengers(bus);

        } catch (InterruptedException e) {
            LOGGER.error(e.getMessage(), e);
        } finally {

            busStopSemaphore.release();

            LOGGER.debug("\nThe Bus No. " + bus.getBusNumber() + " left the bus stop No. " + busStopNumber + "\n" +
                    "At the bus stop No." + busStopNumber + " ===> " + busStopPassengers + " waiting passengers \n" +
                    "The bus stop No. " + bus.getBusNumber() + " ===> carries " + bus.getBusPassengers() + " passengers");
        }
    }

    public void transferPassengers(Bus bus) {
        try {
            locking.lock();
            
            LOGGER.debug("The Bus No. " + bus.getBusNumber() + " STARTED TRANSFER  passengers at the bus stop No. " + busStopNumber);
            int busPassengers = bus.getBusPassengers();
            int busStopPassengers = this.getBusStopPassengers();
            int leftBusPassengers = (int) (Math.random() * busPassengers);
            int enteredBusPassengers = (int) (Math.random() * busStopPassengers);
            bus.setBusPassengers(busPassengers - leftBusPassengers + enteredBusPassengers);
            int leftBusStopPassengers = (int) (Math.random() * leftBusPassengers);
            this.setBusStopPassengers(busStopPassengers + leftBusPassengers - leftBusStopPassengers - enteredBusPassengers);

        } finally {
            locking.unlock();
            LOGGER.debug("The Bus No. " + bus.getBusNumber() + " FINISHED LOADING passengers at the bus stop No. " + busStopNumber);
        }
    }

    public int getBusStopPassengers() {
        return busStopPassengers;
    }

    public void setBusStopPassengers(int busStopPassengers) {
        this.busStopPassengers = busStopPassengers;
    }

    public int getBusStopNumber() {
        return busStopNumber;
    }

}
