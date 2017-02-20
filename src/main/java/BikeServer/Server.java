package BikeServer;

import BikeAvailabilityCalculations.BikeAvailabilityMapProvider;
import BikeAvailabilityCalculations.BikeStopEntry;
import BikeAvailabilityCalculations.BikeStopsInRadiusCalculator;
import spark.ModelAndView;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

import java.util.HashMap;

import static spark.Spark.get;
import static spark.SparkBase.port;

public class Server {

    private BikeAvailabilityMapProvider mapProvider;
    private ThymeleafTemplateEngine engine;

    public static void main(String[] args) {
        port(Integer.valueOf(args[0]));
        Server testServer = new Server();

        HashMap<String, Object> clientMap = new HashMap<>();

        get("/home", (request, response) -> {
            String location = request.queryParams("userLocation");
            String distanceAsString = request.queryParams("userDistance");
            if (location != null) {
                clientMap.put("bikeTable", testServer.produceClientWebpageTable(location, Double.valueOf(distanceAsString)));
                return new ModelAndView(clientMap, "bikeStopTable");
            }
            return new ModelAndView(clientMap, "home");
        }, testServer.getEngine());
    }

    public Server() {
        initialiseMapProvider();
        engine = new ThymeleafTemplateEngine();
    }

    private void initialiseMapProvider() {
        mapProvider = new BikeAvailabilityMapProvider();
        mapProvider.start();
    }

    public ThymeleafTemplateEngine getEngine() {
        return engine;
    }

    private BikeWebTableEntry[] produceClientWebpageTable(String location, double distance) {
        BikeStopsInRadiusCalculator clientCalculator = mapProvider.getNewRadiusCalculator();
        clientCalculator.setCurrentLocation(location);
        HashMap<String, BikeStopEntry> bikeStopEntriesWithinDistance = clientCalculator.getBikeStopEntriesWithinDistance(distance);
        return createArrayOfBikeEntries(bikeStopEntriesWithinDistance);
    }

    private BikeWebTableEntry[] createArrayOfBikeEntries(HashMap<String, BikeStopEntry> bikeStopEntriesWithinDistance) {
        BikeWebTableEntry[] webTableArray = new BikeWebTableEntry[bikeStopEntriesWithinDistance.size()];
        int i = 0;
        for (String place : bikeStopEntriesWithinDistance.keySet()) {
            BikeStopEntry currentEntry = bikeStopEntriesWithinDistance.get(place);
            webTableArray[i] = new BikeWebTableEntry(place, Integer.toString(currentEntry.getFreeBikes()), "0");
            i++;
        }
        return webTableArray;
    }

    public static class BikeWebTableEntry {
        public String location;
        public String numberBikes;
        public String distanceFromCurrentLocation;

        public BikeWebTableEntry(String location, String numberBikes, String distanceFromCurrentLocation) {
            this.location = location;
            this.numberBikes = numberBikes;
            this.distanceFromCurrentLocation = distanceFromCurrentLocation;
        }
    }
}
