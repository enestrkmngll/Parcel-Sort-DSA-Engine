package main;

import java.io.IOException;

import ConfigReader.ConfigReader;
import ParcelSortXSimulation.ParcelSortXSimulation;

public class ParcelSortXMain {
    public static void main(String[] args) {
        try {
           
            ConfigReader config = new ConfigReader("config.txt");
            
            ParcelSortXSimulation simulation = new ParcelSortXSimulation(
                config.getMaxTicks(),
                config.getQueueCapacity(),
                config.getTerminalRotationInterval(),
                config.getParcelPerTickMin(),
                config.getParcelPerTickMax(),
                config.getMisroutingRate(),
                config.getCityList()
            );
                
            simulation.runSimulation();
                
        } catch (IOException e) {
            System.err.println("Failed to read config file: " + e.getMessage());
            System.exit(1);
        } catch (NumberFormatException e) {
            System.err.println("Invalid number format in config file: " + e.getMessage());
            System.exit(1);
        } catch (Exception e) {
            System.err.println("Unexpected error:" + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
}
