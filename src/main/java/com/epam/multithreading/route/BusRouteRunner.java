package com.epam.multithreading.route;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BusRouteRunner {

    private static final Logger LOGGER = LogManager.getLogger(BusRouteRunner.class);
    private static final File BUSES_RESOURCE = new File("src/main/resources/buses.json");
    private static final File BUS_STOPS_RESOURCE = new File("src/main/resources/busstops.json");

    public static void main(String[] args) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();

        List<Bus> buses = objectMapper.readValue(BUSES_RESOURCE, new TypeReference<>() {
        });
        Map<Integer, BusStop> busStops = objectMapper.readValue(BUS_STOPS_RESOURCE, new TypeReference<>() {
        });

        BusStopsPool busStopsPool = BusStopsPool.INSTANCE;
        busStopsPool.setBusStopsPool(busStops);

        int busQuantity = buses.size();
        ExecutorService executorService = Executors.newFixedThreadPool(busQuantity);

        for (Bus bus : buses) {
            executorService.submit(bus);
            LOGGER.debug("The Bus No. " + bus.getBusNumber() + " started!");
        }

        executorService.shutdown();
    }

}
