package BikeServer;

import BikeAvailabilityCalculations.BikeAvailabilityMapProvider;
import BikeAvailabilityCalculations.BikeStopEntry;
import BikeAvailabilityCalculations.BikeStopsInRadiusCalculator;
import spark.ModelAndView;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

import java.util.HashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static spark.Spark.get;

public class Server {

    private BikeAvailabilityMapProvider mapProvider;
    private Executor clientThreadHandler = Executors.newCachedThreadPool();
    private ThymeleafTemplateEngine engine;
    private HashMap<String, String> fieldsForWebPage;

    public static void main(String[] args) {
        Server testServer = new Server();

        HashMap<String, String> clientMap = new HashMap<>();

        get("/home", (request, response) -> {
            String location = request.queryParams("userLocation");
            String distanceAsString = request.queryParams("userDistance");
            if (location != null) {
                clientMap.put("bikeStops", testServer.produceClientWebpage(location, Double.valueOf(distanceAsString)));
                response.redirect("/locationAccepted");
            }
            return new ModelAndView(clientMap, "home");
        }, testServer.getEngine());

        get("/locationAccepted", (request, response) -> {
            return new ModelAndView(clientMap, "locationAccepted");
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
        fieldsForWebPage = new HashMap<>();
    }

    private void initialiseMapProvider() {
        mapProvider = new BikeAvailabilityMapProvider();
        mapProvider.start();
    }

    public ThymeleafTemplateEngine getEngine() {
        return engine;
    }
}
