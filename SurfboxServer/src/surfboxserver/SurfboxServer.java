package surfboxserver;

import grab.ServerConnection;
import grab.ServerObj;

/*
*	The main class for SurfBox2 - Server
*
*	Developed by, Andrew C.
**/

public class SurfboxServer {

    public static void main(String[] args) {
        
        boolean run = true;
        String data = "";
        String cla = "";
        
        for (int i = 0; i < args.length; i++) {
            cla += args[i];
        }
        
        if (cla.equals("config")) {
            SurfboxConfigManager.run();
        }
        
        System.out.println("Surfbox 3 - Smart Aquarium Controller Software\n");
        
        /*
         * Probe Configuration
        **/
        Configuration config = new Configuration("/Users/andrewcampagna/NetBeansProjects/SurfboxServer/src/surfboxserver/config.properties");
        final int TEMP_PROBE_COUNT = Integer.valueOf(config.getConfig("temp"));
        final int EC_PROBE_COUNT = Integer.valueOf(config.getConfig("ec"));
        final int PH_PROBE_COUNT = Integer.valueOf(config.getConfig("ph"));
        final int PROBE_COUNT = TEMP_PROBE_COUNT + EC_PROBE_COUNT + PH_PROBE_COUNT;
        ProbeObj[] probes = new ProbeObj[PROBE_COUNT];
        
        /*
         * Build probe objects
        **/
        System.out.print("Setting up probes...[" + PROBE_COUNT + "]\n");
        
        int k = 0; // Iterator for probe loading algorithm
        
        for (int i = 0; i < TEMP_PROBE_COUNT; i++) {
            probes[k] = new TemperatureProbe();
            System.out.println("Temperature probes...[" + i + "]");
            k++;
        }
        
        for (int i = 0; i < EC_PROBE_COUNT; i++) {
            probes[k] = new ECProbe();
            System.out.println("EC probes...[" + i + "]");
            k++;
        }
        
        for (int i = 0; i < PH_PROBE_COUNT; i++) {
            probes[k] = new PHProbe();
            System.out.println("PH probes...[" + i + "]");
            k++;
        }
        
        System.out.print("Done!\n");
        
        /*
         * Build and Initialize server
        **/
        System.out.print("Setting up server...");
        ServerObj server = new ServerConnection(5000);
        System.out.print("Done!\n");
        System.out.print("Server is wating for connection...");
        
        if (server.setup()) {
            System.out.print("Done!\n");
        } else {
            System.out.println("Failed to set up server.");
            run = false;
        }
        
        while(run) {
            
        }  
    }
}
