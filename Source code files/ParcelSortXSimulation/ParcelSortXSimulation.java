package ParcelSortXSimulation;

import Parcel.Parcel;
import DataStructures.*;
import Logger.Logger;

import java.io.PrintWriter;
import java.io.IOException;
import java.util.Random;

public class ParcelSortXSimulation {
    private int currentTick;
    private int maxTicks;
    private int terminalRotationInterval;
    private int parcelPerTickMin;
    private int parcelPerTickMax;
    private double misroutingRate;
    private String[] cityList;
    private int totalParcels = 0;
    private int maxQueueSizeObserved = 0;
    private int maxStackSizeObserved = 0;  
    private ParcelTracker parcelTracker;
    private ArrivalBuffer arrivalBuffer;
    private ReturnStack returnStack;
    private DestinationSorter destinationSorter;
    private TerminalRotator terminalRotator;
    private Logger logger;
    private Random random;
    public ParcelSortXSimulation(int maxTicks, int queueCapacity, int terminalRotationInterval, 
                            int parcelPerTickMin, int parcelPerTickMax, double misroutingRate, 
                            String[] cityList) {
        this.maxTicks = maxTicks;
        this.terminalRotationInterval = terminalRotationInterval;
        this.parcelPerTickMin = parcelPerTickMin;
        this.parcelPerTickMax = parcelPerTickMax;
        this.misroutingRate = misroutingRate;
        this.cityList = cityList;   
        this.currentTick = 0;
        this.random = new Random();
        this.parcelTracker = new ParcelTracker(queueCapacity);
        this.arrivalBuffer = new ArrivalBuffer(queueCapacity);
        this.returnStack = new ReturnStack();
        this.destinationSorter = new DestinationSorter(queueCapacity);
        this.terminalRotator = new TerminalRotator(cityList);
        this.logger = new Logger(parcelTracker, destinationSorter);
    }
        
    public void runSimulation() {
        try {
            new PrintWriter("log.txt").close();
        } catch (IOException e) {
            System.err.println("Error clearing log file: " + e.getMessage());
        }
            
        for (currentTick = 1; currentTick <= maxTicks; currentTick++) {
            logger.writeToLogFile("[Tick " + currentTick + "]");
                
            generateNewParcels();
 
            processArrivalBuffer();

            evaluateDispatch();
                
            if (currentTick % terminalRotationInterval == 0) {
                terminalRotator.advanceTerminal();
                logger.writeToLogFile("Terminal rotated to: " + terminalRotator.getActiveTerminal());
            }
            maxQueueSizeObserved = Math.max(maxQueueSizeObserved, arrivalBuffer.size());
            maxStackSizeObserved = Math.max(maxStackSizeObserved, returnStack.size());

            logTickSummary();
        }

        logger.generateReport(maxTicks, totalParcels,cityList);
    }
        
    private void generateNewParcels() {
        int parcelsToGenerate = parcelPerTickMin + random.nextInt(parcelPerTickMax - parcelPerTickMin + 1);
                        
        for (int i = 0; i < parcelsToGenerate; i++) {
            String parcelID = "P" + (currentTick * 100 + i); 
            String destinationCity = cityList[random.nextInt(cityList.length)];
            int priority = 1 + random.nextInt(3); 
            String size = getRandomParcelSize();
                
            Parcel parcel = new Parcel(
                parcelID, 
                destinationCity, 
                priority, 
                size, 
                currentTick, 
                Parcel.ParcelStatus.IN_QUEUE
            );
            totalParcels++; 
            arrivalBuffer.enqueue(parcel);
            logger.updateMaxQueueSize(arrivalBuffer.size());
            parcelTracker.insert(
                parcelID, 
                Parcel.ParcelStatus.IN_QUEUE, 
                currentTick, 
                destinationCity, 
                priority, 
                size
            );
            logger.writeToLogFile("New parcel: " + parcelID + " to " + destinationCity + 
                            " (Priority " + priority + ", Size " + size + ")");
        }
        
        logger.writeToLogFile("Queue size after arrivals: " + arrivalBuffer.size());
    }
        
    private String getRandomParcelSize() {
        int sizeRand = random.nextInt(3);
        switch (sizeRand) {
            case 0: return "Small";
            case 1: return "Medium";
            default: return "Large";
        }
    }
        
    private void processArrivalBuffer() {
        while (!arrivalBuffer.isEmpty()) {
            Parcel parcel = arrivalBuffer.dequeue();
                
            destinationSorter.insertParcel(parcel);
                
            parcelTracker.updateStatus(parcel.getParcelID(), Parcel.ParcelStatus.SORTED);
                
            logger.writeToLogFile("Sorted to BST: " + parcel.getParcelID() + " to " + 
                            parcel.getDestinationCity());
        }
    }
        
    private void evaluateDispatch() {
        String activeTerminal = terminalRotator.getActiveTerminal();
        ArrivalBuffer cityParcels = destinationSorter.getCityParcels(activeTerminal);

        if (cityParcels != null && !cityParcels.isEmpty()) {
            Parcel firstParcel = cityParcels.peek(); 
            Parcel parcel = destinationSorter.removeParcel(activeTerminal, firstParcel.getParcelID()); 
            
            
            if (parcel != null) {
                if (random.nextDouble() < misroutingRate) {

                    returnStack.push(parcel);
                    parcelTracker.updateStatus(parcel.getParcelID(), Parcel.ParcelStatus.RETURNED);
                    parcelTracker.incrementReturnCount(parcel.getParcelID());
                    logger.writeToLogFile("MISROUTED: " + parcel.getParcelID());
                } else {

                    parcelTracker.updateStatus(parcel.getParcelID(), Parcel.ParcelStatus.DISPATCHED);
                    parcelTracker.setDispatchTick(parcel.getParcelID(), currentTick);
                }
            }
        }
    }
        
    private void logTickSummary() {
        logger.writeToLogFile("\n=== TICK "+currentTick+" SUMMARY ===");
        logger.writeToLogFile("Queue size: " + arrivalBuffer.size());
        logger.writeToLogFile("ReturnStack size: " + returnStack.size());
        logger.writeToLogFile("Active Terminal: " + terminalRotator.getActiveTerminal());
        logger.writeToLogFile("\n===============================================================================\n");

        destinationSorter.inOrderTraversal();
        logger.writeToLogFile(""); 

        logger.updateMaxQueueSize(arrivalBuffer.size());
        logger.updateMaxStackSize(returnStack.size());
    }
}
