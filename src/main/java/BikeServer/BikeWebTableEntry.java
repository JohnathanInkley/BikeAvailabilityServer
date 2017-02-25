package BikeServer;

public class BikeWebTableEntry implements Comparable {
    public String location;
    public String numberBikes;
    public String distanceFromCurrentLocation;

    public BikeWebTableEntry(String location, String numberBikes, String distanceFromCurrentLocation) {
        this.location = location;
        this.numberBikes = numberBikes;
        this.distanceFromCurrentLocation = distanceFromCurrentLocation;
    }

    @Override
    public int compareTo(Object o) {
        BikeWebTableEntry otherEntry = (BikeWebTableEntry) o;
        double delta = Double.valueOf(this.distanceFromCurrentLocation) - Double.valueOf(otherEntry.distanceFromCurrentLocation);
        if (delta < 0) {
            return -1;
        } else if (delta == 0) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public boolean equals(Object o) {
        BikeWebTableEntry otherEntry = (BikeWebTableEntry) o;
        return this.location == otherEntry.location && this.distanceFromCurrentLocation == otherEntry.distanceFromCurrentLocation;
    }
}
