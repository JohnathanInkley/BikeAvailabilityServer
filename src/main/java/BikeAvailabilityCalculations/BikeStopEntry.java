package BikeAvailabilityCalculations;

public class BikeStopEntry {
    private final int numberOfFreeBikes;
    private final UserLocation location;

    public BikeStopEntry(int numberOfFreeBikes, UserLocation userLocation) {
        validateInputs(numberOfFreeBikes);
        this.numberOfFreeBikes = numberOfFreeBikes;
        this.location = userLocation;
    }

    private static void validateInputs(int numberOfFreeBikes) {
        if (numberOfFreeBikes < 0) {
            throw new RuntimeException("Negative number of bikes: " + numberOfFreeBikes);
        }
    }

    public static BikeStopEntry getDefaultEmptyEntry() {
        return new BikeStopEntry(0, new UserLocation("no location", 0, 0));
    }

    public boolean equals(Object second) {
        BikeStopEntry secondData = (BikeStopEntry) second;
        return secondData.numberOfFreeBikes == numberOfFreeBikes
               && location.equals(secondData.location);
    }

    public double distanceTo(BikeStopEntry second) {
        return location.distanceTo(second.location);
    }

    public String toString() {
        return numberOfFreeBikes + " free bike(s) ";
    }

    public double getLatitude() {
        return location.getLatitude();
    }

    public double getLongitude() {
        return location.getLongitude();
    }

    public UserLocation getLocation() {
        return location;
    }
}
