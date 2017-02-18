package BikeAvailabilityCalculations;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RawBikeDataParserTest {

    @Test
    public void parserShouldReadWebsiteText() {
        RawBikeDataParser parser = new RawBikeDataParser();
        assertEquals("[{\"$type", parser.getAllBikePointDataAsString().substring(0,8), "Starting text should match");
    }

    @Test
    public void stringShouldBeSplittableAtCommonName() {
        RawBikeDataParser parser = new RawBikeDataParser();
        String rawData = parser.getAllBikePointDataAsString();
        ArrayList<String> splitData = parser.splitRawDataAtBikeStopNames(rawData, "commonName");
        assertTrue(splitData.size() > 0);
        for (String each : splitData) {
            assertTrue(containsListOfPhrasesStringMustContainToBeValidBikePointEntry(each));
        }
    }

    public boolean containsListOfPhrasesStringMustContainToBeValidBikePointEntry(String entry) {
        return entry.contains("TerminalName")
                && entry.contains("Installed")
                && entry.contains("Locked")
                && entry.contains("InstallDate")
                && entry.contains("Temporary")
                && entry.contains("NbBikes")
                && entry.contains("NbEmptyDocks");
    }


    @Test
    public void shouldBeAbleToGetLatitudeAndLongitudeOfAGivenStop() {
        RawBikeDataParser parser = new RawBikeDataParser();
        String bikeStop = "River Street , Clerkenwell";
        double latitude = 51.529163;
        double longitude = -0.10997;
        HashMap<String, BikeStopEntry> freeSpaceAndLocationMap = parser.requestNewBikeAvailabilityMap();
        BikeStopEntry bikeStopData = freeSpaceAndLocationMap.get(bikeStop);
        assertEquals(latitude, bikeStopData.getLatitude());
        assertEquals(longitude, bikeStopData.getLongitude());
    }

}