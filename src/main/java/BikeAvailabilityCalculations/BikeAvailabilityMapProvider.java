package BikeAvailabilityCalculations;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BikeAvailabilityMapProvider {

    private RawBikeDataParser dataProvider;
    private ConcurrentHashMap<String, BikeStopEntry> latestBikeAvailabilityMap;
    private ExecutorService service;

    private ConcurrentHashMap<String, BikeStopEntry> latestBikeAvailabilityMapDummy;

    public void start() {
        dataProvider = new RawBikeDataParser();
        latestBikeAvailabilityMap = new ConcurrentHashMap<>(dataProvider.requestNewBikeAvailabilityMap());
        service = Executors.newFixedThreadPool(1);
        service.execute(this::continuouslyUpdateBikeAvailabilityMap);
    }

    private void continuouslyUpdateBikeAvailabilityMap() {
        ConcurrentHashMap<String, BikeStopEntry> temporaryMap;
        while (true) {
            temporaryMap = new ConcurrentHashMap<>(dataProvider.requestNewBikeAvailabilityMap());
            latestBikeAvailabilityMap = temporaryMap;
        }
    }

    public HashMap<String, BikeStopEntry> getLatestBikeAvailabilityMap() {
        return new HashMap<>(latestBikeAvailabilityMap);
    }

    public void startDummy() {
        ExecutorService service = Executors.newFixedThreadPool(1);
        service.execute(this::continuouslyUpdateDummyBikeAvailabilityMap);
        while (latestBikeAvailabilityMapDummy == null) {
            //
        }
    }

    private void continuouslyUpdateDummyBikeAvailabilityMap() {
        ConcurrentHashMap<String, BikeStopEntry> temporaryMap;
        HashMap<String, BikeStopEntry> dummyMap = new HashMap<>(1);
        dummyMap.put("bikeStop", new BikeStopEntry(1, new UserLocation("", 2, 3)));
        while (true) {
            temporaryMap = new ConcurrentHashMap<>(dummyMap);
            latestBikeAvailabilityMapDummy = temporaryMap;
        }
    }

    public HashMap<String, BikeStopEntry> getLatestBikeAvailabilityMapDummy() {
        return new HashMap<>(latestBikeAvailabilityMapDummy);
    }

    public BikeStopsInRadiusCalculator getNewRadiusCalculator() {
        BikeStopsInRadiusCalculator newCalculator = new BikeStopsInRadiusCalculator();
        newCalculator.openConnectionToMapProvider(this);
        return newCalculator;
    }
}
