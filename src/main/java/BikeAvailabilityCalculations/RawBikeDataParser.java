package BikeAvailabilityCalculations;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Properties;
import java.util.stream.Stream;

import static java.util.Arrays.asList;

public class RawBikeDataParser {
    private String allBikePointDataAsString;
    private String webAddressOfBikeData;
    private ArrayList<String> rawDataForEachBikeStop;
    private HashMap<String, BikeStopEntry> bikeAvailabilityAndLocationMap;

    public static void main(String[] args) {
        RawBikeDataParser parser = new RawBikeDataParser();
        HashMap<String, BikeStopEntry> outputMap = parser.requestNewBikeAvailabilityMap();

        Properties properties = new Properties();

        for (String entry : outputMap.keySet()) {
            properties.put(entry, outputMap.get(entry).toString());
        }

        try {
            properties.store(new FileOutputStream("data.properties"), null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public RawBikeDataParser() {
        webAddressOfBikeData = "https://api.tfl.gov.uk/bikepoint";
    }


    public HashMap<String,BikeStopEntry> requestNewBikeAvailabilityMap() {
        retrieveRawDataFromTfL();
        splitRawDataAtBikeStopNames(allBikePointDataAsString, "commonName");
        createFreeBikeAndLocationMap();
        return bikeAvailabilityAndLocationMap;
    }

    private void retrieveRawDataFromTfL() {
        allBikePointDataAsString = WebDataReader.readTextFromURL(webAddressOfBikeData);
    }


    ArrayList<String> splitRawDataAtBikeStopNames(String rawDataAsSingleString, String textToSplitOn) {
        String[] stringSplitAtRequiredText = rawDataAsSingleString.split(textToSplitOn);
        ArrayList<String> arrayOfUnprocessedDataItems = new ArrayList<>(asList(stringSplitAtRequiredText));
        arrayOfUnprocessedDataItems.removeIf((s) ->
            !s.substring(0, 3).equals("\":\"")
        );
        this.rawDataForEachBikeStop = arrayOfUnprocessedDataItems;
        return arrayOfUnprocessedDataItems;
    }

    private HashMap<String,BikeStopEntry> createFreeBikeAndLocationMap() {
        bikeAvailabilityAndLocationMap = new HashMap<>();
        Stream<String> bikeStopDataStream = rawDataForEachBikeStop.stream();
        bikeStopDataStream.forEach((rawStringEntry) -> {
            String bikeStopName = getBikeStopNameFromRawData(rawStringEntry);
            BikeStopEntry newEntry = createBikeStopMapEntryFromRawData(rawStringEntry, bikeStopName);
            bikeAvailabilityAndLocationMap.put(bikeStopName, newEntry);
        });
        return bikeAvailabilityAndLocationMap;
    }

    private String getBikeStopNameFromRawData(String rawStringEntry) {
        String[] splitEntry = rawStringEntry.split("\"");
        return splitEntry[2];
    }

    private BikeStopEntry createBikeStopMapEntryFromRawData(String rawStringEntry, String bikeStopName) {
        String[] splitEntry = rawStringEntry.split("\"");
        int numberOfFreeBikes = getFreeBikesFromSplitString(splitEntry);
        double latitude = getLatitudeFromSplitString(splitEntry);
        double longitude = getLongitudeFromSplitString(splitEntry);
        BikeStopEntry entry = BikeStopEntry.getDefaultEmptyEntry();
        try {
            entry =  new BikeStopEntry(numberOfFreeBikes, new UserLocation(bikeStopName, latitude, longitude));
        } catch (RuntimeException ex) {
        }
        return entry;
    }

    private double getLatitudeFromSplitString(String[] splitEntry) {
        int latitudeIndex = Arrays.asList(splitEntry).indexOf("lat") + 1;
        String latitudeWithColon = splitEntry[latitudeIndex];
        String latitudeWithoutColon = latitudeWithColon.substring(1).replace(",", "");
        return Double.valueOf(latitudeWithoutColon);
    }

    private double getLongitudeFromSplitString(String[] splitEntry) {
        int longitudeIndex = Arrays.asList(splitEntry).indexOf("lon") + 1;
        String longitudeWithColon = splitEntry[longitudeIndex];
        String longitudeWithoutColon = longitudeWithColon.substring(1).replace("},{", "").replace("}]", "");
        return Double.valueOf(longitudeWithoutColon);
    }

    private int getFreeBikesFromSplitString(String[] splitString) {
        int freeBikeIndex =  Arrays.asList(splitString).indexOf("NbBikes") + 8;
        return Integer.valueOf(splitString[freeBikeIndex]);
    }

    public String getAllBikePointDataAsString() {
        retrieveRawDataFromTfL();
        return allBikePointDataAsString;
    }
}
