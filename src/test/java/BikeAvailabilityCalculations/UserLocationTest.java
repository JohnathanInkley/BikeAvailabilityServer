package BikeAvailabilityCalculations;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class UserLocationTest {

    @Test
    public void shouldBeAbleToGetLatitudeAndLongitudeFromLocation() {
        UserLocation location = new UserLocation("Here", 1, 2);
        assertEquals(1.0, location.getLatitude());
        assertEquals(2.0, location.getLongitude());
    }

    @Test
    public void shouldBeAbleToCalculateDistanceBetweenLocations() {
        UserLocation first = new UserLocation("first", 1, 1);
        UserLocation second = new UserLocation("second", 1, 2);
        assertEquals(69.132, first.distanceTo(second),0.1);
    }

    @Test
    public void sameLocationsShouldBeEqual() {
        UserLocation first = new UserLocation("first", 1, 1);
        UserLocation equalLocation = new UserLocation("second", 1, 1);
        UserLocation notEqualLocation = new UserLocation("third", 0, 0);
        assertTrue(first.equals(equalLocation));
        assertFalse(first.equals(notEqualLocation));
    }

}