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

        HashMap<String, String> clientMap = new HashMap<>();

        get("/home", (request, response) -> {
            String location = request.queryParams("userLocation");
            String distanceAsString = request.queryParams("userDistance");
            if (location != null) {
                clientMap.put("bikeStops", testServer.produceClientWebpage(location, Double.valueOf(distanceAsString)));
                return new ModelAndView(clientMap, "locationAccepted");
            }
            return new ModelAndView(clientMap, "home");
        }, testServer.getEngine());


    }

    private String produceClientWebpage(String location, double distance) {
        BikeStopsInRadiusCalculator clientCalculator = mapProvider.getNewRadiusCalculator();
        clientCalculator.setCurrentLocation(location);
        HashMap<String, BikeStopEntry> bikeStopEntriesWithinDistance = clientCalculator.getBikeStopEntriesWithinDistance(distance);
        return bikeStopEntriesWithinDistance.toString();
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
}
