package surfboxserver;

import java.util.Scanner;

/*
*	Command Handler
*
*	Developed by, Andrew C.
**/

public class CommandHandler {
    
    /*
     * Parses packets recieved from the client, and returns a new packet to be served back
    **/
    public String parsecmd(ProbeObj probe, Scanner sc) {
        
        // Commands are required to be from string location 1 to 5
        String command = sc.next();
        
        // Empty string to store new packet
        String rtr = "";
        
        System.out.println(command);
        
        // Decides what to do with given command
        switch(command) {
            
            // Enters new reading into memory for probe
            case "newr":
                try {
                    probe.newReading(sc.nextFloat());
                    rtr = "Stored new reading into memory";
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
        
        return rtr + "\r";
    }
}