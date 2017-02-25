package BikeAvailabilityCalculations;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AddressStringToUserLocationConverterTest {
    @Test
    public void calculatorShouldTakeStringAndReturnUserLocation() {
        UserLocation cromwellRoad = new UserLocation("200 Cromwell Road, London", 51.4946983, -0.1936039);
        UserLocation returnedLocation = AddressStringToUserLocationConverter.getUserLocationFromString("200 Cromwell Road, London");
        assertEquals(cromwellRoad, returnedLocation);
    }

    @Test
    public void calculatorShouldReturnDefaultLocationForIncorrectString() {
        UserLocation badLocation = new UserLocation("No location found", 0, 0);
        UserLocation returnedLocation = AddressStringToUserLocationConverter.getUserLocationFromString("aergdsfhas");
        assertEquals(badLocation, returnedLocation);
    }
}