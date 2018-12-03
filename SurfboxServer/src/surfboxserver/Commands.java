package surfboxserver;

import grab.*;

/*
*	Command Bank
*
*	Developed by, Andrew C.
**/

public class Commands {
    
    public String parsecmd(ProbeObj probe, String data) {
        
        String command = data.substring(1, 5);
        String rtr = "";
        
        System.out.println(command);
        
        switch(command) {
            
            case "newr":
                probe.newReading(Float.valueOf(data.substring(5, data.length())));
                rtr = "Stored new reading into memory";
            break;
            
            case "getr":
                rtr = String.valueOf(probe.getReading());
            break;
            
            case "pchk":
                rtr = probe.paramsCheck();
            break;
            
            default:
                rtr = "Invalid command";
            break;
        } 
        
        return rtr + "\r";
    }
}
