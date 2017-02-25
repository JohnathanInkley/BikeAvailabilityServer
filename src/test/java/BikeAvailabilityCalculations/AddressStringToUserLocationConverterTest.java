package BikeAvailabilityCalculations;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AddressStringToUserLocationConverterTest {
    @Test
    public void calculatorShouldTakeStringAndReturnUserLocation() {
        UserLocation cromwellRoad = new UserLocation("200 Cromwell Road, London", 51.4946983, -0.1936039);
        UserLocation returnedLocation = AddressStringToUserLocationConverter.getUserLocationFromString("200 Cromwell Road, London");
        assertEquals(cromwellRoad, returnedLocation);
        assertTrue(false);
    }
}