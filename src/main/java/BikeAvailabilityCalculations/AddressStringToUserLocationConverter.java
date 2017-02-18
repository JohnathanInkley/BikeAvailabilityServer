package BikeAvailabilityCalculations;

public class AddressStringToUserLocationConverter {

    private static String GOOGLE_API_KEY = "AIzaSyDuzsl1bwFRRwvwUSNkkm5sAs8rb05AyEI";
    private static String inputLocation;
    private static StringBuilder requestBuilder;

    private static String getGoogleGeocodeSyntax() {
        requestBuilder = new StringBuilder();
        requestBuilder.append("https://maps.googleapis.com/maps/api/geocode/json?address=");
        String[] locationWords = inputLocation.split("\\s+");
        for (String word : locationWords) {
            requestBuilder.append(word);
            requestBuilder.append("+");
        }
        requestBuilder.append("&key=");
        requestBuilder.append(GOOGLE_API_KEY);
        return requestBuilder.toString();
    }

    public static UserLocation getUserLocationFromString(String location) {
        inputLocation = location;
        getGoogleGeocodeSyntax();
        String googleResults = WebDataReader.readTextFromURL(requestBuilder.toString());
        String intermediateResult = googleResults.split("\"location\"")[1];
        String[] splitString = intermediateResult.split("\\s+");
        double latitude = Double.valueOf(splitString[5].replace(",",""));
        double longitude = Double.valueOf(splitString[8]);
        return new UserLocation(location, latitude, longitude);
    }
}
