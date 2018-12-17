package surfboxserver;

import grab.ServerConnection;
import grab.ServerObj;
import java.util.Scanner;

/*
*	The main class for SurfBox2 - Server
*
*	Developed by, Andrew C.
**/

public class SurfboxServer {

    public static void main(String[] args) {
        
        Scanner sc;
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
            probes[k] = new TemperatureProbe(Float.valueOf(config.getConfig("tempmin")),
                                             Float.valueOf(config.getConfig("tempmax")),
                                             Float.valueOf(config.getConfig("tempperf")),
                                             Float.valueOf(config.getConfig("temptol")),
                                             Byte.valueOf(config.getConfig("tempunit")));
            System.out.println("Temperature probes...[" + i + "]" + " -> Address: " + k);
            k++;
        }
        
        for (int i = 0; i < EC_PROBE_COUNT; i++) {
            probes[k] = new ECProbe(Float.valueOf(config.getConfig("ecmin")),
                                    Float.valueOf(config.getConfig("ecmax")),
                                    Float.valueOf(config.getConfig("ecperf")),
                                    Float.valueOf(config.getConfig("ectol")));
            System.out.println("EC probes...[" + i + "]" + " -> Address: " + k);
            k++;
        }
        
        for (int i = 0; i < PH_PROBE_COUNT; i++) {
            probes[k] = new PHProbe(Float.valueOf(config.getConfig("phmin")),
                                    Float.valueOf(config.getConfig("phmax")),
                                    Float.valueOf(config.getConfig("phperf")),
                                    Float.valueOf(config.getConfig("phtol")));
            System.out.println("PH probes...[" + i + "]" + " -> Address: " + k);
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
        
        /*
         * Main Program Loop
        **/
        
        CommandHandler cmd = new CommandHandler();
        
        while(run) {
            
            data = server.listen();
            
            // Handles client disconnecting events
            if (data.equals("null") || data.equals("kill")) {
                System.out.println("Client disconnected, wating for new connection...");
                server.kill();
                
                if (server.setup()) {
                    System.out.print("Done!\n");
                    data = "";
                    run = true;
                } else {
                    System.out.println("Failed to set up server.");
                    run = false;
                }
            } else {
            
                try {
                    
                    // Parses probe ID
                    sc = new Scanner(data);
                    int p = sc.nextInt();
            
                    // Decides what to do with data recieved from client and serves response
                    String packet = cmd.parsecmd(probes[p], sc);
                    server.write(packet);
                    
                } catch (Exception e) {
                    
                    server.write("Invalid data, check probe ID");
                    
                }
            }
        }   
    }
}
