package Logger;

import Parcel.Parcel;
import DataStructures.DestinationSorter;
import DataStructures.ParcelTracker;

import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.io.IOException;


public class Logger {
    private ParcelTracker parcelTracker;
    private DestinationSorter destinationSorter;
    private int maxQueueSizeObserved;
    private int maxStackSizeObserved;

    public Logger(ParcelTracker tracker, DestinationSorter sorter) {
        this.parcelTracker = tracker;
        this.destinationSorter = sorter;
        this.maxQueueSizeObserved = 0;
        this.maxStackSizeObserved = 0;
    }

    public void writeToLogFile(String message) { 
        try {
            FileWriter fw = new FileWriter("log.txt", true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw);
            
            out.println(message);
            
            out.close();
            bw.close();
            fw.close();
        } catch (IOException e) {
            System.err.println("Error writing to log: " + e.getMessage());
        }
    }

    public void generateReport(int totalTicks, int totalParcels, String[] cityList) {
        PrintWriter out = null;
        try {
            out = new PrintWriter("report.txt");

            out.println("=== 1. SIMULATION OVERVIEW ===");
            out.println("Total Ticks Executed:\t     " + totalTicks);
            out.println("Number of Parcels Generated: " + totalParcels);
            out.println();

            out.println("=== 2. PARCEL STATISTICS ===");
            out.println("Total Dispatched Parcels:    " + getDispatchedParcelCount());
            out.println("Total Returned Parcels:      " + getReturnedParcelCount());
            out.println("Parcels Still in Queue:      " + getInQueueParcelCount()); 
            out.println("Parcels Still in BST:        " + getSortedParcelCount());
            out.println("Parcels Still in Stack:      "+getReturnedParcelCount());
            out.println();
                
            out.println("=== 3. DESTINATION METRICS ===");
            out.println("Parcels per City:");
                
            for (String city : cityList) {
                int count = destinationSorter.countCityParcels(city);
                out.printf("%-10s: %d parcels%n", city, count);
            }
            out.println("Most Frequently Targeted Destination: " + 
                    destinationSorter.highestParcelCity() + " (" + 
                    destinationSorter.countCityParcels(destinationSorter.highestParcelCity()) + " parcels)");
            out.println();

            out.println("=== 4. TIMING AND DELAY METRICS ===");
            out.printf("Average Processing Time: %.2f ticks%n", getAverageDispatchTime());
            out.println("Parcel With Longest Delay: " + getLongestDelayParcelInfo());
            out.println("Parcels Returned More Than Once: " + getMultipleReturnedParcelCount());
            out.println();

            out.println("=== 5. DATA STRUCTURE STATISTICS ===");
            out.println("Maximum Queue Size Observed: " + this.maxQueueSizeObserved);
            out.println("Maximum Stack Size Observed: " + this.maxStackSizeObserved);
            out.println("Final Height of BST:         " + destinationSorter.heightOfBST());
            out.println("Number of BST Nodes:         " + destinationSorter.numberOfNodes());
            out.printf("Hash Table Load Factor:      %.2f%n", parcelTracker.getLoadFactor());
            out.println();

        } catch (IOException e) {
            System.err.println("Error generating report: " + e.getMessage());
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    private double getAverageDispatchTime() { 
        int total = 0;                        
        int count = 0;
        for (int i = 0; i < parcelTracker.table.length; i++) {
            ParcelTracker.Hash entry = parcelTracker.table[i];
            while (entry != null) {
                if (entry.record.dispatchTick != -1) {
                    total += entry.record.dispatchTick - entry.record.arrivalTick;
                    count++;
                }
                entry = entry.next;
            }
        }
        if (count > 0) {
            return (double) total / count;
        } else {
            return 0;
        }
    }

    private int getDispatchedParcelCount() {
        return parcelTracker.countParcelsByStatus(Parcel.ParcelStatus.DISPATCHED);
    }
        
    private int getReturnedParcelCount() {
        int count = 0;
        for (int i = 0; i < parcelTracker.table.length; i++) {
            ParcelTracker.Hash entry = parcelTracker.table[i];
            while (entry != null) {
                if (entry.record.returnCount >= 1) {
                    count++;
                }
                entry = entry.next;
            }
        }
        return count;
    }

    private int getInQueueParcelCount() { 
        return parcelTracker.countParcelsByStatus(Parcel.ParcelStatus.IN_QUEUE);
    }

    private int getSortedParcelCount() {
        return parcelTracker.countParcelsByStatus(Parcel.ParcelStatus.SORTED);
    }

    private int getMultipleReturnedParcelCount() {
        int count = 0;
        for (int i = 0; i < parcelTracker.table.length; i++) {
            ParcelTracker.Hash entry = parcelTracker.table[i];
            while (entry != null) {
                if (entry.record.returnCount > 1) {
                    count++;
                }
                entry = entry.next;
            }
        }
        return count;
    }
        
    private String getLongestDelayParcelInfo() {
        String parcelID = "None";
        int maxDelay = 0;
            
        for (int i = 0; i < parcelTracker.table.length; i++) {
            ParcelTracker.Hash entry = parcelTracker.table[i];
            while (entry != null) {
                if (entry.record.dispatchTick != -1) {
                    int delay = entry.record.dispatchTick - entry.record.arrivalTick;
                    if (delay > maxDelay) {
                        maxDelay = delay;
                        parcelID = entry.parcelID;
                    }
                }
                entry = entry.next;
            }
        }
        return parcelID + " (" + maxDelay + " ticks)";
    }

    public void updateMaxQueueSize(int currentSize) { 
        this.maxQueueSizeObserved = Math.max(this.maxQueueSizeObserved, currentSize);
    }

    public void updateMaxStackSize(int currentSize) { 
        this.maxStackSizeObserved = Math.max(this.maxStackSizeObserved, currentSize);
    }
}
