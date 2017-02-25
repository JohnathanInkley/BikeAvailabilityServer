package BikeServer;

import java.util.Iterator;
import java.util.TreeSet;

public class BikeStopWebTable {
    private TreeSet<BikeWebTableEntry> tableSet;
    Iterator<BikeWebTableEntry> tableIterator;

    public BikeStopWebTable() {
        tableSet = new TreeSet<>();
        tableIterator = tableSet.iterator();
    }

    public void add(BikeWebTableEntry bikeWebTableEntry) {
        tableSet.add(bikeWebTableEntry);
    }

    public void startIterating() {
        tableIterator = tableSet.iterator();
    }

    public BikeWebTableEntry nextEntry() {
        return tableIterator.next();
    }

    public boolean equals(Object o) {
        BikeStopWebTable otherTable = (BikeStopWebTable) o;
        return this.tableSet.equals(otherTable.tableSet);
    }

    public int size() {
        return tableSet.size();
    }
}
