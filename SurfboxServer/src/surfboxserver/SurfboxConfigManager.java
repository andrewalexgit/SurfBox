package surfboxserver;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/*
*	Surfbox 3 - Configuration Manager
*
*	Developed by, Andrew C.
*       
*       Additional credits to Clifton Labs for JSON support
**/

public class SurfboxConfigManager {
    
    /*
     *  Main program flag
    **/
    private static boolean run = true;
    
    /*
     *  Keyboard input
    **/
    private static Scanner keyboard = new Scanner(System.in);
    
    /*
     *  Command parse
    **/
    private static Scanner sc;
    
    /*
     *  JSON Parser Object
    **/
    private static JSONParser parser = new JSONParser();

    public static void runConfigManager(String apiPath) throws FileNotFoundException, IOException, ParseException {

        Object obj = parser.parse(new FileReader(apiPath));
        JSONObject jsonObject = (JSONObject) obj;

        /*
         *  Array of probe objects
        **/
        JSONArray probes = (JSONArray) jsonObject.get("probes");
        
        /*
         *  Array of device objects
        **/
        JSONArray devices = (JSONArray) jsonObject.get("devices");

        
        /*
         *  Main program loop
        **/
        
        while (run) {

            System.out.print("$ > ");
            String input = keyboard.nextLine();

            try {
                sc = new Scanner(input);
                String head = sc.next();

                /*
                 * Probe manager chunk
                **/
                if (head.equals("p")) {

                    String command = sc.next();

                    /*
                     * Add new blank probe
                    **/
                    if (command.equals("add")) {
                        JSONObject newProbe = new JSONObject();
                        newProbe.put("name", "blank probe");
                        newProbe.put("type", "no type");
                        newProbe.put("max", "00.0");
                        newProbe.put("min", "00.0");
                        newProbe.put("tol", "00.0");
                        newProbe.put("target", "00.0");
                        probes.add(newProbe);
                    } 
                    
                    /*
                     * Remove probe by address
                    **/
                    else if (command.equals("remove")) {
                        probes.remove(sc.nextInt());
                    } else {
                        
                        JSONObject probe = (JSONObject) probes.get(sc.nextInt());

                        /*
                         * Get probe by address
                        **/
                        if (command.equals("get")) {
                            System.out.println((String) probe.get("name") + " (" + (String) probe.get("type") + ")");
                            System.out.println("Maximum: " + (String) probe.get("max"));
                            System.out.println("Minimum: " + (String) probe.get("min"));
                            System.out.println("Tolerance: " + (String) probe.get("tol"));
                            System.out.println("Target: " + (String) probe.get("target"));
                        } 
                        
                        /*
                         * Update probe by (key, value)
                        **/
                        else if (command.equals("update")) {
                            probe.put(sc.next(), sc.next());
                        }
                    }
                    
                /*
                 * Device manager chunk
                **/
                } else if (head.equals("d")) {

                    String command = sc.next();

                    /*
                     * Add new blank device
                    **/
                    if (command.equals("add")) {
                        JSONObject newDevice = new JSONObject();
                        newDevice.put("name", "blank device");
                        newDevice.put("type", "no type");
                        newDevice.put("status", "0");
                        newDevice.put("relay", "0");
                        devices.add(newDevice);
                    } 
                    
                    /*
                     * Remove device by address
                    **/
                    else if (command.equals("remove")) {
                        devices.remove(sc.nextInt());
                    } else {
                        
                        JSONObject device = (JSONObject) devices.get(sc.nextInt());
                        
                        /*
                         * Get device by address
                        **/
                        if (command.equals("get")) {
                            System.out.println((String) device.get("name") + " (" + (String) device.get("type") + ")");
                            System.out.println("Relay: " + (String) device.get("relay"));
                            System.out.println("Status: " + (String) device.get("status"));
                        } 
                        
                        /*
                         * Update device by (key, value)
                        **/
                        else if (command.equals("update")) {
                            device.put(sc.next(), sc.next());
                        }
                    }
                } 
                
                /*
                 * Print list of probe and device objects
                **/
                else if (head.equals("ls")) {
                    
                    int i = 0;
                    
                    System.out.println("\nPROBES");
                    for (Object o : probes) {

                        JSONObject probe = (JSONObject) o;

                        System.out.println("[ ADDRESS: " + i + " ]");
                        System.out.println((String) probe.get("name") + " (" + (String) probe.get("type") + ")");
                        System.out.println("Maximum: " + (String) probe.get("max"));
                        System.out.println("Minimum: " + (String) probe.get("min"));
                        System.out.println("Tolerance: " + (String) probe.get("tol"));
                        System.out.println("Target: " + (String) probe.get("target"));
                        System.out.println();
                        i++;
                    }
                    
                    i = 0;
                    
                    System.out.print("\nDEVICES\n");
                    for (Object o : devices) {

                        JSONObject device = (JSONObject) o;

                        System.out.println("[ ADDRESS: " + i + " ]");
                        System.out.println((String) device.get("name") + " (" + (String) device.get("type") + ")");
                        System.out.println("Relay: " + (String) device.get("relay"));
                        System.out.println("Status: " + (String) device.get("status"));
                        System.out.println();
                        i++;
                    }

                
                /*
                 * Save and exit SBSCM3
                **/
                } else if (head.equals("exit")) {
                    try (PrintWriter writer = new PrintWriter(new File(apiPath))) {
                        writer.print("");
                        writer.print(jsonObject);
                    }
                    run = false;
                }
            } catch (Exception e) {
                System.out.println("Invalid command");
            }
        }
    }
}
