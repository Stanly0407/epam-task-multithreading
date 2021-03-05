package com.epam.multithreading.route;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class BusStop {

    private int maxAvailableBusCapacity;
    private int busStopNumber;
    private Semaphore semaphore; // available

    private int passengers;
//    private ReentrantLock lock = new ReentrantLock(true);
//    private Condition condition = lock.newCondition();

    public BusStop(int busStopNumber, int maxAvailableBusCapacity, int passengers) {
        this.busStopNumber = busStopNumber;
        this.maxAvailableBusCapacity = maxAvailableBusCapacity;
        this.passengers = passengers;
        this.semaphore = new Semaphore(maxAvailableBusCapacity, true);
    }


    public void makeStop(Bus bus) {
        try {
            semaphore.acquire(); // trying to enter the bus stop
            System.out.println("Bus No. " + bus.getBusNumber() + " pull into the bus stop No. " + busStopNumber);
            TimeUnit.SECONDS.sleep(1);

            passengersLoadingAndUnloading(bus);

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

    public void passengersLoadingAndUnloading(Bus bus) throws InterruptedException {
        TimeUnit.SECONDS.sleep(1);

        System.out.println("Bus No. " + bus.getBusNumber() + " started Loading and Unloading passengers at the bus stop No. "
                + this.busStopNumber);

        //  condition.await();
        TimeUnit.SECONDS.sleep(2);

        // CHANGE LOGIC!
        int passengersInTheBus = bus.getPassengers();
        int passengersAtTheBusStop = getPassengers();
        if (passengersInTheBus >= 2 && passengersAtTheBusStop >= 2) {
            bus.setPassengers(passengersInTheBus - 4);
            this.setPassengers(passengersAtTheBusStop - 3);
        }

        TimeUnit.SECONDS.sleep(2);


        //   condition.signal();
        System.out.println("Bus No. " + bus.getBusNumber() + " finished Loading and Unloading passengers at the bus stop No. "
                + this.busStopNumber);
    }


    public int getPassengers() {
        return passengers;
    }

    public void setPassengers(int passengers) {
        this.passengers = passengers;
    }

    public int getMaxAvailableBusCapacity() {
        return maxAvailableBusCapacity;
    }

    public void setMaxAvailableBusCapacity(int maxAvailableBusCapacity) {
        this.maxAvailableBusCapacity = maxAvailableBusCapacity;
    }

    public int getBusStopNumber() {
        return busStopNumber;
    }

    public void setBusStopNumber(int busStopNumber) {
        this.busStopNumber = busStopNumber;
    }

    public Semaphore getSemaphore() {
        return semaphore;
    }

    public void setSemaphore(Semaphore semaphore) {
        this.semaphore = semaphore;
    }


}
