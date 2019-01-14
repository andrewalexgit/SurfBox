package surfboxserver;

import grab.ServerConnection;
import grab.ServerObj;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.parser.ParseException;

/*
*	The main class for SurfBox3 - Server
*
*	Developed by, Andrew C.
**/
public class SurfboxServer {

    public static void main(String[] args) {

        /*
         * Scanner reference
        **/
        Scanner sc;

        /*
         * Main program flag
        **/
        boolean run = true;
        
        /*
         * Grabs API File Path from properties
        **/
        Properties apiProps = new Properties();
        try {
            apiProps.load(new FileInputStream("src/api/api.properties"));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SurfboxServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SurfboxServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String apiPath = apiProps.getProperty("api_path");

        /*
         * Holds command line argument
        **/
        String cla = "";

        /*
         * Check for command line arguments
         **/
        for (String arg : args) {
            cla += arg;
        }

        if (cla.equals("config")) {
            try {
                SurfboxConfigManager.runConfigManager(apiPath);
            } catch (IOException | ParseException ex) {
                Logger.getLogger(SurfboxServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        // Main program welcome message
        System.out.println("Surfbox 3 - Smart Aquarium Controller Software");

        /*
         * Initialize probes and devices
        **/
        Configuration config = new Configuration(apiPath);
        ArrayList<ProbeObj> probes = new ArrayList<>();
        ArrayList<Device> devices = new ArrayList<>();
        ArrayList<Timer> timers = new ArrayList<>();
        
        System.out.println("Version " + config.getSettingsConfig("version") + "\n");

        System.out.println("Loading (" + config.getProbeCount() + ") probes...");

        // Load Probe objects
        for (int i = 0; i < config.getProbeCount(); i++) {

            switch (config.getProbeConfig(i, "type")) {
                case "temp":
                    probes.add(new TemperatureProbe());
                    System.out.println("Set up temperature probe named " + config.getProbeConfig(i, "name") + " address " + i);
                    break;
                case "ec":
                    probes.add(new ECProbe());
                    System.out.println("Set up conductivity probe named " + config.getProbeConfig(i, "name") + " address " + i);
                    break;
                case "ph":
                    probes.add(new PHProbe());
                    System.out.println("Set up pH probe named " + config.getProbeConfig(i, "name") + " address " + i);
                    break;
                default:
                    break;
            }

        }

        System.out.println("Done!");
        System.out.println("Loading (" + config.getDeviceCount() + ") devices...");

        // Load Device objects - Change to switch statement when new device objects are created
        for (int i = 0; i < config.getDeviceCount(); i++) {

            switch (config.getDeviceConfig(i, "type")) {

                case "outlet":
                    devices.add(new Outlet());
                    System.out.println("Set up outlet device named " + config.getDeviceConfig(i, "name") + " address " + i);
                    break;

                default:
                    break;
            }

        }

        System.out.println("Done!");
        
        /*
         * Build and initialize timer threads
        **/
        System.out.println("Initializing timer threads...");
        
        // Loads array of timers from configuration
        for (int i = 0; i < config.getTimerCount(); i++) {
            
            int deviceAddr = Integer.valueOf(config.getTimerConfig(i, "deviceaddr"));
            
            System.out.println("Timer thread created for " + config.getDeviceConfig(deviceAddr, "name"));
            
            timers.add(new Timer(config.getTimerConfig(i, "ontime"), 
                       config.getTimerConfig(i, "offtime"), 
                       config, deviceAddr,
                       devices.get(deviceAddr)));
        }
        
        // Begins each timer thread
        timers.forEach((t) -> { t.start(); });
        System.out.println("Done!");
        
        // Creating new logger session
        System.out.println("Creating new logger session...");
        config.clearLogger();
        System.out.println("Done!");

        /*
         * Build and Initialize server
        **/
        System.out.print("Setting up server...");
        ServerObj server = new ServerConnection(Integer.valueOf(config.getSettingsConfig("port")));
        System.out.println("port " + server.getPort() + "...");
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
        CommandHandler cmd = new CommandHandler(apiPath);

        while (run) {

            String data = server.listen();

            // Handles client disconnecting events
            if (data.equals("null") || data.equals("kill") || data.contains("<2>")) {
                System.out.println("Client disconnected, wating for new connection...");
                server.kill();

                if (server.setup()) {
                    System.out.print("Done!\n");
                    run = true;
                } else {
                    System.out.println("Failed to set up server.");
                    run = false;
                }
            } else {

                sc = new Scanner(data);

                try {
                    
                    String head = sc.next();
                    int index;
                    
                    switch (head) {
                        case "p":
                            index = sc.nextInt();
                            server.write(cmd.parsecmd(probes.get(index), sc, index));
                            break;
                        case "d":
                            index = sc.nextInt();
                            server.write(cmd.parsecmd(devices.get(index), sc, index));
                            break;   
                        case "upd":
                            for (int i = 0; i < devices.size(); i++) {
                                boolean b = (config.getDeviceConfig(i, "status").equals("1"));
                                devices.get(i).status = b;
                                System.out.println(devices.get(i).status);
                            }
                            server.write("Updated devices with API");
                            break;
                        default:
                            System.out.println("<13> No probe or device specified");
                            break;
                    }
                } catch (Exception ex) {
                    server.write("<14> Corrupt request");
                }
            }
        }
    }
}
