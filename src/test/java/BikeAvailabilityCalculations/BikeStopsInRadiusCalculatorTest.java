package BikeAvailabilityCalculations;

import BikeServer.BikeStopWebTable;
import BikeServer.BikeWebTableEntry;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BikeStopsInRadiusCalculatorTest {

    private static BikeAvailabilityMapProvider newProvider;
    private static BikeStopsInRadiusCalculator calculator;

    @BeforeAll
    public static void setUpProvider() {
        newProvider = new BikeAvailabilityMapProvider();
        newProvider.start();
        calculator = newProvider.getNewRadiusCalculator();
    }

    @Test
    public void calculatorShouldBeAbleToGenerateAnInitialLocationMap() {
        HashMap<String, BikeStopEntry> expectedMap = newProvider.getLatestBikeAvailabilityMap();
        HashMap<String, BikeStopEntry> actualMap = calculator.getCurrentBikeStopMap();
        assertEquals(expectedMap, actualMap);
    }

    @Test
    public void calculatorShouldTakeALocationFromAUser() {
        UserLocation location = new UserLocation("myLocation", 51.529163, -0.10997);
        calculator.setCurrentLocation(location);
        UserLocation calculatorLocation = calculator.getCurrentLocation();
        assertEquals(location, calculatorLocation);
    }

    @Test
    public void calculatorShouldHavePredicateToSayWhetherDistanceLessThanSpecified() {
        UserLocation centre = new UserLocation("Clerkenwell", 51.529163, -0.10997);
        calculator.setCurrentLocation(centre);

        HashMap<String, BikeStopEntry> calculatedCloseBikeStops = calculator.getBikeStopEntriesWithinDistance(0.15);
        HashSet<String> bikeStopsCloseFromMap = new HashSet<>();
        bikeStopsCloseFromMap.add("River Street , Clerkenwell");
        bikeStopsCloseFromMap.add("Hardwick Street, Clerkenwell");

        assertEquals(bikeStopsCloseFromMap, calculatedCloseBikeStops.keySet());
    }

    @Test
    public void calculatorShouldTakeAddressAsStringAndReturnAvailabilityMap() {
        calculator.setCurrentLocation("River Street, Clerkenwell");

        HashMap<String, BikeStopEntry> calculatedCloseBikeStops = calculator.getBikeStopEntriesWithinDistance(0.15);
        HashSet<String> bikeStopsCloseFromMap = new HashSet<>();
        bikeStopsCloseFromMap.add("River Street , Clerkenwell");
        bikeStopsCloseFromMap.add("Hardwick Street, Clerkenwell");

        assertEquals(bikeStopsCloseFromMap, calculatedCloseBikeStops.keySet());
    }

    @Test
    public void nonExistentAddressesShouldReturnAnEmptyMap() {
        calculator.setCurrentLocation("dfgdfzj");

        HashMap<String, BikeStopEntry> calculatedCloseBikeStops = calculator.getBikeStopEntriesWithinDistance(0.15);
        HashSet<String> bikeStopsCloseFromMap = new HashSet<>();

        assertEquals(bikeStopsCloseFromMap, calculatedCloseBikeStops.keySet());
    }

    @Test
    public void calculatorShouldReturnSpecialisedWebSetWhenRequested() {
        calculator.setCurrentLocation("River Street, Clerkenwell");
        BikeStopWebTable returnTable = calculator.getBikeStopEntriesWithinDistanceForWeb(0.15);
        BikeStopWebTable expectedTable = new BikeStopWebTable();
        expectedTable.add(new BikeWebTableEntry("River Street , Clerkenwell", "0", "0.02"));
        expectedTable.add(new BikeWebTableEntry("Hardwick Street, Clerkenwell", "0", "0.12"));
        assertEquals(expectedTable, returnTable);
    }
}
