package com.epam.multithreading.route;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class BusStop {

    private int busStopNumber;
    private Semaphore semaphore;
    private int passengers;
    private ReentrantLock locking = new ReentrantLock(true);

    public BusStop(int busStopNumber, int maxAvailableBusCapacity, int passengers) {
        this.busStopNumber = busStopNumber;
        this.passengers = passengers;
        this.semaphore = new Semaphore(maxAvailableBusCapacity, true);
    }

    public void makeStop(Bus bus) {
        try {
            semaphore.acquire(); // trying to enter the bus stop
            System.out.println("Bus No. " + bus.getBusNumber() + " pull into the bus stop No. " + busStopNumber);
            TimeUnit.SECONDS.sleep(1);

            loadingAndUnloadingPassengers(bus);

            TimeUnit.SECONDS.sleep(1);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore.release();
            System.out.println("**********************************\n"
                    + "Bus No. " + bus.getBusNumber() + " left the bus stop No. " + busStopNumber + "\n"
                    + "At the bus stop No." + getBusStopNumber() + " ===> " + getPassengers() + " waiting passengers" + "\n"
                    + "The bus stop No. " + bus.getBusNumber() + " ===> carries " + bus.getPassengers() + " passengers" + "\n" +
                    "************************************");
        }
    }

    public void loadingAndUnloadingPassengers(Bus bus) throws InterruptedException {
        TimeUnit.SECONDS.sleep(1);

        System.out.println("Bus No. " + bus.getBusNumber() + " STARTED LOADING and Unloading passengers at the bus stop No. "
                + this.busStopNumber);
        try {
            locking.lock();

            TimeUnit.SECONDS.sleep(2);

            // change logic!
            int passengersInTheBus = bus.getPassengers();
            int passengersAtTheBusStop = getPassengers();
            if (passengersInTheBus >= 4 && passengersAtTheBusStop >= 4) {
                bus.setPassengers(passengersInTheBus - 4);
                this.setPassengers(passengersAtTheBusStop - 3);
            }

            TimeUnit.SECONDS.sleep(2);
        } finally {
            locking.unlock();
            System.out.println("Bus No. " + bus.getBusNumber() + " FINISHED LOADING and Unloading passengers at the bus stop No. "
                    + this.busStopNumber);
        }

    }


    public int getPassengers() {
        return passengers;
    }

    public void setPassengers(int passengers) {
        this.passengers = passengers;
    }

    public int getBusStopNumber() {
        return busStopNumber;
    }

}
