package BikeServer;

import BikeAvailabilityCalculations.BikeAvailabilityMapProvider;
import BikeAvailabilityCalculations.BikeStopsInRadiusCalculator;
import spark.ModelAndView;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

import java.util.HashMap;

import static spark.Spark.get;
import static spark.SparkBase.port;

public class Server {

    private BikeAvailabilityMapProvider mapProvider;
    private ThymeleafTemplateEngine templateEngine;

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
        }, testServer.getTemplateEngineInstance());
    }

    public Server() {
        initialiseMapProvider();
        templateEngine = new ThymeleafTemplateEngine();
    }

    private void initialiseMapProvider() {
        mapProvider = new BikeAvailabilityMapProvider();
        mapProvider.start();
    }

    public ThymeleafTemplateEngine getTemplateEngineInstance() {
        return templateEngine;
    }

    private BikeWebTableEntry[] produceClientWebpageTable(String location, double distance) {
        BikeStopsInRadiusCalculator clientCalculator = mapProvider.getNewRadiusCalculator();
        clientCalculator.setCurrentLocation(location);
        BikeStopWebTable bikeStopEntriesWithinDistance = clientCalculator.getBikeStopEntriesWithinDistanceForWeb(distance);
        return createArrayOfBikeEntries(bikeStopEntriesWithinDistance);
    }

    private BikeWebTableEntry[] createArrayOfBikeEntries(BikeStopWebTable bikeStopEntriesWithinDistance) {
        BikeWebTableEntry[] webTableArray = new BikeWebTableEntry[bikeStopEntriesWithinDistance.size()];
        bikeStopEntriesWithinDistance.startIterating();
        for (int i = 0; i < bikeStopEntriesWithinDistance.size(); i++) {
            webTableArray[i] = bikeStopEntriesWithinDistance.nextEntry();
        }
        return webTableArray;
    }
}
