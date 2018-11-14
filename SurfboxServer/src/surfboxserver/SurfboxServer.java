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
        
        final int PROBE_COUNT = 1;
        
        ProbeObj[] probes = new ProbeObj[PROBE_COUNT];
        
        boolean run = true;
        String data = "";
        
        /*
         * Build probe objects
        **/
        System.out.print("Setting up probes...\n");
        
        for (int i = 0; i < PROBE_COUNT; i++) {
            probes[i] = new TemperatureProbe(); // Sets up temperature probe with default settings
            float percent = ((float) (i+1) / PROBE_COUNT) * 100; // Grabs percentage of probes setup so far
            System.out.printf("%.0f", percent);
            System.out.print("%...");
        }
        
        System.out.print("Done!\n");
        
        /*
         * Build and Initialize server
        **/
        System.out.print("Setting up server...");
        ServerObj server = new ServerConnection(5000);
        
        if (server.setup()) {
            System.out.print("Done!\n");
            run = true;
        } else {
            System.out.println("Failed to set up server.");
            run = false;
        }
        
        while(run) {
            
            data = server.listen();
            
            switch(data) {
                
                case "temp":
                    server.write(String.valueOf(probes[0].getReading()));
                break;
                
                case "newreading":
                    server.write("value:");
                    data = server.listen();
                    probes[0].newReading(Float.valueOf(data));
                    server.write("updated");
                break;
                
                case "paramscheck":
                    server.write(probes[0].paramsCheck());
                break;
                
                case "kill":
                    server.write("Shutting down...");
                    run = false;
                break;
                
                default:
                    server.write("unknown command");
                break;
            }
        }
    }
}
