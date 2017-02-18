package BikeAvailabilityCalculations;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.*;

class BikeStopEntryTest {

    @Test
    public void sameObjectsShouldBeEqual() {
        BikeStopEntry bikeData = new BikeStopEntry(5, new UserLocation("",2, 1));
        BikeStopEntry equalBikeData = new BikeStopEntry(5, new UserLocation("", 2, 1));
        BikeStopEntry notEqualBikeData = new BikeStopEntry(1, new UserLocation("", 1, 1));
        assertTrue(bikeData.equals(equalBikeData));
        assertFalse(bikeData.equals(notEqualBikeData));
    }

    @Test
    public void numberOfBikesShouldBeNonNegative() {
        try {
            BikeStopEntry bikeData = new BikeStopEntry(-1, new UserLocation("",1,1));
            fail("Cannot have negative bikes");
        } catch (RuntimeException ex) {
        }
    }

    @Test
    public void distanceShouldBeCalculatedBetweenTwoDataUnits() {
        BikeStopEntry first = new BikeStopEntry(0, new UserLocation("", 1, 1));
        BikeStopEntry second = new BikeStopEntry(0, new UserLocation("", 1,2));
        assertEquals(69.132, first.distanceTo(second),0.1);
    }

    @Test
    public void shouldBeAbleToDisplayADataUnit() {
        BikeStopEntry data = new BikeStopEntry(1, new UserLocation("", 2,3));
        assertEquals("1 free bike(s) ", data.toString());
    }
}