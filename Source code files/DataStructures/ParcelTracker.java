package DataStructures;

import Parcel.Parcel;

public class ParcelTracker {
    public static class Hash {
        public String parcelID; 
        public ParcelRecord record; 
        public Hash next; 

        public Hash(String parcelID, ParcelRecord record) {
            this.parcelID = parcelID;
            this.record = record;
            this.next = null;
        }
    }
    public static class ParcelRecord {
        Parcel.ParcelStatus status;
        public int arrivalTick;
        public int dispatchTick;
        public int returnCount;
        String destinationCity;
        int priority;
        String size;
        public ParcelRecord(Parcel.ParcelStatus status, int arrivalTick, String destinationCity, int priority, String size) {
            this.status = status;
            this.arrivalTick = arrivalTick;
            this.dispatchTick = -1; 
            this.returnCount = 0;
            this.destinationCity = destinationCity;
            this.priority = priority;
            this.size = size;
        }
    }
    public Hash[] table; 
    private int capacity; 
    public ParcelTracker(int capacity) {
        this.capacity = capacity; 
        this.table = new Hash[capacity];
    }


    public void insert(String parcelID, Parcel.ParcelStatus status, int arrivalTick, 
                                    String destinationCity, int priority, String size) {
        if (exists(parcelID)) return; 
            
        int bucketIndex = get(parcelID);
        ParcelRecord newRecord = new ParcelRecord(status, arrivalTick, destinationCity, priority, size);
            
        Hash newEntry = new Hash(parcelID, newRecord);
        if (table[bucketIndex] == null) {
            table[bucketIndex] = newEntry;
        } else {
            Hash current = table[bucketIndex];
            while (current.next != null) {
                current = current.next;
            }
            current.next = newEntry;
        }
    }

    public void updateStatus(String parcelID, Parcel.ParcelStatus newStatus) {
        Hash entry = findEntry(parcelID);
        if (entry != null) {
            entry.record.status = newStatus;
        }
    }

    public int get(String parcelID) {
        return Math.abs(parcelID.hashCode()) % capacity;
    }

    public void incrementReturnCount(String parcelID) {
        Hash entry = findEntry(parcelID);
        if (entry != null) {
            entry.record.returnCount++;
        }
    }

    public boolean exists(String parcelID) {
        return findEntry(parcelID) != null;
    }

    public void setDispatchTick(String parcelID, int dispatchTick) {
        Hash entry = findEntry(parcelID);
        if (entry != null) {
            entry.record.dispatchTick = dispatchTick;
        }
    }

    private Hash findEntry(String parcelID) {
        int bucketIndex = get(parcelID);
        Hash current = table[bucketIndex];
            
        while (current != null) {
            if (current.parcelID.equals(parcelID)) {
                return current;
            }
            current = current.next;
        }
        return null;
    }

    public double getLoadFactor() { 
        int entryCount = 0;
        for (Hash entry : table) {
            while (entry != null) {
                entryCount++;
                entry = entry.next;
            }
        }
        return (double) entryCount / capacity;
    }

    public int countParcelsByStatus(Parcel.ParcelStatus status) { 
        int count = 0;
        for (Hash entry : table) {
            while (entry != null) {
                if (entry.record.status == status) {
                    count++;
                }
                entry = entry.next;
            }
        }
        return count;
    }
}
