package BikeServer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BikeStopWebTableTest {

    @Test
    public void entriesShouldComeOutInSortedOrderAndShouldBeAbleToRestartGettingThem() {
        BikeStopWebTable testTable = new BikeStopWebTable();
        testTable.add(new BikeWebTableEntry("far", "0", "0.2"));
        testTable.add(new BikeWebTableEntry("close", "0", "0.1"));
        testTable.add(new BikeWebTableEntry("veryFar", "0", "0.5"));
        testTable.startIterating();
        assertEquals(new BikeWebTableEntry("close", "0", "0.1"), testTable.nextEntry());
        testTable.startIterating();
        assertEquals(new BikeWebTableEntry("close", "0", "0.1"), testTable.nextEntry());
        assertEquals(new BikeWebTableEntry("far", "0", "0.2"), testTable.nextEntry());
        assertEquals(new BikeWebTableEntry("veryFar", "0", "0.5"), testTable.nextEntry());
    }

    @Test
    public void equalTablesShouldBeEqual() {
        BikeStopWebTable testTable = new BikeStopWebTable();
        testTable.add(new BikeWebTableEntry("far", "0", "0.2"));
        testTable.add(new BikeWebTableEntry("close", "0", "0.1"));
        BikeStopWebTable otherTable = new BikeStopWebTable();
        otherTable.add(new BikeWebTableEntry("far", "0", "0.2"));
        otherTable.add(new BikeWebTableEntry("close", "0", "0.1"));
        assertEquals(testTable, otherTable);
    }

}