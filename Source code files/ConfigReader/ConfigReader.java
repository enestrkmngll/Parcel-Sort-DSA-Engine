package ConfigReader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ConfigReader {
    private String[] cityList;
    private int maxTicks;
    private int queueCapacity;
    private int terminalRotationInterval;
    private int parcelPerTickMin;
    private int parcelPerTickMax;
    private double misroutingRate;

    public ConfigReader(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line= reader.readLine();
            
        while (line != null) { 
            line = line.trim(); 
            if (line.isEmpty()) continue;
                
            int equalSign = line.indexOf('='); 
            if (equalSign == -1) continue;
                
            String key = line.substring(0, equalSign).trim(); 
            String value = line.substring(equalSign + 1).trim();
                
            switch (key) {
                case "MAX_TICKS":
                    this.maxTicks = Integer.parseInt(value);
                    break;
                case "QUEUE_CAPACITY":
                    this.queueCapacity = Integer.parseInt(value);
                    break;
                case "TERMINAL_ROTATION_INTERVAL":
                    this.terminalRotationInterval = Integer.parseInt(value);
                    break;
                case "PARCEL_PER_TICK_MIN":
                    this.parcelPerTickMin = Integer.parseInt(value);
                    break;
                case "PARCEL_PER_TICK_MAX":
                    this.parcelPerTickMax = Integer.parseInt(value);
                    break;
                case "MISROUTING_RATE":
                    this.misroutingRate = Double.parseDouble(value);
                    break;
                case "CITY_LIST":
                    this.cityList = parseCityList(value);
                    break;
            }

            line=reader.readLine();
        }
        reader.close();
    }

    private String[] parseCityList(String citiesStr) {
        String[] cities = citiesStr.split(",");
        for (int i = 0; i < cities.length; i++) {
            cities[i] = cities[i].trim();
        }
        return cities;
    }
    
    public String[] getCityList() { return cityList; }
    public int getMaxTicks() { return maxTicks; }
    public int getQueueCapacity() { return queueCapacity; }
    public int getTerminalRotationInterval() { return terminalRotationInterval; }
    public int getParcelPerTickMin() { return parcelPerTickMin; }
    public int getParcelPerTickMax() { return parcelPerTickMax; }
    public double getMisroutingRate() { return misroutingRate; }
}
