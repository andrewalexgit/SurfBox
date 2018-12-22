package surfboxserver;

import grab.ServerConnection;
import grab.ServerObj;
import java.io.IOException;
import java.util.ArrayList;
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
         * Holds data from client
        **/
        String data = "";

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
                SurfboxConfigManager.runConfigManager();
            } catch (IOException | ParseException ex) {
                Logger.getLogger(SurfboxServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        // Main program welcome message
        System.out.println("Surfbox 3 - Smart Aquarium Controller Software\n");

        /*
         * Initialize probes and devices
        **/
        Configuration config = new Configuration();
        ArrayList<ProbeObj> probes = new ArrayList<>();
        ArrayList<Device> devices = new ArrayList<>();

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

        while (run) {

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

                sc = new Scanner(data);

                String head = sc.next();
                int index = sc.nextInt();

                try {
                    if (head.equals("p")) {
                        server.write(cmd.parsecmd(probes.get(index), sc, index));
                    } else if (head.equals("d")) {
                        server.write(cmd.parsecmd(devices.get(index), sc, index));
                    } else {
                        System.out.println("<13> No probe or device specified");
                    }
                } catch (Exception ex) {
                    server.write("<14> Corrupt request");
                }
            }
        }
    }
}
