package Parcel;

public class Parcel {
    public String parcelID;
    public String destinationCity;
    public int priority;
    public String size;
    public int arrivalTick;
    public ParcelStatus status;  

    public enum ParcelStatus {
        IN_QUEUE("InQueue"),
        SORTED("Sorted"),
        DISPATCHED("Dispatched"),
        RETURNED("Returned");

        private final String displayName;

        ParcelStatus(String displayName) {
            this.displayName = displayName;
        }

        @Override
        public String toString() {
            return displayName;
        }
    }

    public Parcel(String parcelID, String destinationCity, int priority, String size, 
                    int arrivalTick, ParcelStatus status) {
        this.parcelID = parcelID;
        this.destinationCity = destinationCity;
        this.priority = priority;
        this.size = size;
        this.arrivalTick = arrivalTick;
        this.status = status;
    }

    public String getParcelID() {
        return parcelID;
    }

    public String getDestinationCity() {
        return destinationCity;
    }

    public String getSize() {
        return size;
    }

    public int getArrivalTick() {
        return arrivalTick;
    }

    public ParcelStatus getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return String.format("ParcelID: %s, Dest: %s, Priority: %d, Size: %s, Tick: %d, Status: %s",
                parcelID, destinationCity, priority, size, arrivalTick, status);
    }
}
