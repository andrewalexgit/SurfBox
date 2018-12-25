package surfboxserver;

import java.util.Scanner;

/*
*	Command Handler
*
*	Developed by, Andrew C.
**/

public class CommandHandler {
    
    /*
     * Access to API
    **/
    Configuration config;
    
    public CommandHandler(String apiPath) {
        config = new Configuration(apiPath);
    }
    
    /*
     * Parses packets recieved from the client, and returns a new packet to be served back - PROBES
    **/
    public String parsecmd(ProbeObj probe, Scanner sc, int index) {
        
        // Commands are required to be from string location 1 to 5
        String command = sc.next();
        
        // Empty string to store new packet
        String rtr = "";
        
        // Decides what to do with given command
        switch(command) {
            
            // Enters new reading into memory for probe
            case "newr":
                try {
                    probe.newReading(sc.nextFloat());
                    rtr = "Stored new reading into memory";
                    config.updateProbeConfiguration(index, "reading", String.valueOf(probe.getReading()));
                    config.updateAPI();
                } catch (Exception e) {
                    System.out.println("<12> Not valid format for storing new reading");
                    rtr = "<12> Not valid format for storing new reading";
                }
            break;
            
            // Gets last reading stored in memory
            case "getr":
                rtr = String.valueOf(probe.getReading());
            break;
            
            // Uses last reading stored in memory to check tolerance, and if the probes readings are in range
            case "pchk":
                rtr = probe.paramsCheck();
            break;
            
            // Default
            default:
                rtr = "Invalid command";
            break;
        } 
        
        System.out.println(rtr);
        return rtr + "\r";
    }
    
    /*
     * Parses packets recieved from the client, and returns a new packet to be served back - DEVICES
    **/
    public String parsecmd(Device device, Scanner sc, int index) {
        
        // Commands are required to be from string location 1 to 5
        String command = sc.next();
        
        // Empty string to store new packet
        String rtr = "";
        
        switch (command) {
            
            // Sets device status to on
            case "switchon":
                device.setDevice(true);
                rtr = device.toString();
                config.updateDeviceConfiguration(index, "status", "1");
                config.updateAPI();
            break;
            
            // Sets device status to off
            case "switchoff":
                device.setDevice(false);
                rtr = device.toString();
                config.updateDeviceConfiguration(index, "status", "0");
                config.updateAPI();
            break;
            
            // Gets device status
            case "getd": 
                rtr = String.valueOf(device.status);
            break;
            
            // Default
            default:
                rtr = "Invalid command";
            break;
            
        }
        return rtr + "\r";
    }
}