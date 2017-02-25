package BikeServer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BikeWebTableEntryTest {

    @Test
    public void entriesShouldBeComparable() {
        BikeWebTableEntry closeEntry = new BikeWebTableEntry("close", "0", "0.1");
        BikeWebTableEntry farEntry = new BikeWebTableEntry("far", "0", "0.2");
        assertTrue(closeEntry.compareTo(farEntry) < 0);
    }

    @Test
    public void equalEntriesShouldBeEqual() {
        BikeWebTableEntry anEntry = new BikeWebTableEntry("here", "0", "0.1");
        BikeWebTableEntry anEqualEntry = new BikeWebTableEntry("here", "3", "0.1");
        BikeWebTableEntry aBadEntry = new BikeWebTableEntry("there", "0", "0.3");
        assertTrue(anEntry.equals(anEqualEntry));
        assertFalse(anEntry.equals(aBadEntry));
    }

}