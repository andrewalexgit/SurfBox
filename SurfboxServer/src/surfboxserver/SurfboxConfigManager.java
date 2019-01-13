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
    private static final Scanner KEYBOARD = new Scanner(System.in);

    /*
     *  Command parse
    **/
    private static Scanner sc;

    /*
     *  JSON Parser Object
    **/
    private static final JSONParser PARSER = new JSONParser();

    public static void runConfigManager(String apiPath) throws FileNotFoundException, IOException, ParseException {

        Object obj = PARSER.parse(new FileReader(apiPath));
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
         *  Array of timer objects
        **/
        JSONArray timers = (JSONArray) jsonObject.get("timers");

        /*
         * Settings object
        **/
        JSONObject settings = (JSONObject) jsonObject.get("settings");

        /*
         *  Main program loop
        **/
        while (run) {

            System.out.print("$ > ");
            String input = KEYBOARD.nextLine();
            String command;

            try {
                sc = new Scanner(input);
                String head = sc.next();

                switch (head) {

                    /*
                         * Handle timers
                        **/
                    case "t":

                        command = sc.next();
                        
                           switch (command) {
                            /*
                     * Remove timer by address
                     **/
                            case "add":
                                JSONObject newTimer = new JSONObject();
                                newTimer.put("ontime", "12.00.00");
                                newTimer.put("offtime", "12.00.01");
                                newTimer.put("deviceaddr", "0");
                                timers.add(newTimer);
                                break;
                            case "remove":
                                timers.remove(sc.nextInt());
                                break;
                            default:
                                JSONObject timer = (JSONObject) timers.get(sc.nextInt());
                                /*
                            * Get timer by address
                            **/
                                if (command.equals("get")) {
                                    System.out.println((String) "ontime: "+ timer.get("ontime"));
                                    System.out.println((String) "offtime: "+ timer.get("offtime"));
                                    System.out.println((String) "deviceaddr: "+ timer.get("deviceaddr"));
                                } /*
                            * Update timer by (key, value)
                            **/ else if (command.equals("update")) {
                                    timer.put(sc.next(), sc.next());
                                }
                                break;
                        }
                        break;

                    case "p": {
                        command = sc.next();
                        /*
                     * Add new blank probe
                     **/
                        switch (command) {
                            case "add":
                                JSONObject newProbe = new JSONObject();
                                newProbe.put("name", "blank probe");
                                newProbe.put("type", "no type");
                                newProbe.put("max", "00.0");
                                newProbe.put("min", "00.0");
                                newProbe.put("tol", "00.0");
                                newProbe.put("target", "00.0");
                                probes.add(newProbe);
                                break;
                        
                            case "remove":
                                probes.remove(sc.nextInt());
                                break;
                            default:
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
                                } /*
                            * Update probe by (key, value)
                            **/ else if (command.equals("update")) {
                                    probe.put(sc.next(), sc.next());
                                }
                                break;
                        }
                        break;
                    }
                    case "d": {
                        command = sc.next();
                        /*
                     * Add new blank device
                     **/
                        switch (command) {
                            /*
                     * Remove device by address
                     **/
                            case "add":
                                JSONObject newDevice = new JSONObject();
                                newDevice.put("name", "blank device");
                                newDevice.put("type", "no type");
                                newDevice.put("status", "0");
                                newDevice.put("relay", "0");
                                devices.add(newDevice);
                                break;
                            case "remove":
                                devices.remove(sc.nextInt());
                                break;
                            default:
                                JSONObject device = (JSONObject) devices.get(sc.nextInt());
                                /*
                            * Get device by address
                            **/
                                if (command.equals("get")) {
                                    System.out.println((String) device.get("name") + " (" + (String) device.get("type") + ")");
                                    System.out.println("Relay: " + (String) device.get("relay"));
                                    System.out.println("Status: " + (String) device.get("status"));
                                } /*
                            * Update device by (key, value)
                            **/ else if (command.equals("update")) {
                                    device.put(sc.next(), sc.next());
                                }
                                break;
                        }
                        break;
                    }
                    case "ls":
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

                        i = 0;
                        System.out.print("\nTIMERS\n");
                        for (Object o : timers) {

                            JSONObject timer = (JSONObject) o;

                            System.out.println("[ Timer-" + i + " ]");
                            System.out.println("ontime: " + (String) timer.get("ontime"));
                            System.out.println("offtime: " + (String) timer.get("offtime"));
                            System.out.println("deviceaddr: " + (String) timer.get("deviceaddr"));
                            System.out.println();
                            i++;
                        }

                        break;

                    /*
                        * Save and exit SBSCM3
                        **/
                    case "exit":
                        try (PrintWriter writer = new PrintWriter(new File(apiPath))) {
                            writer.print("");
                            writer.print(jsonObject);
                        }
                        run = false;
                        break;

                    case "port":
                        settings.put("port", sc.next());
                        break;

                    case "getport":
                        System.out.println((String) settings.get("port"));
                        break;
                    default:
                        break;
                }
            } catch (Exception e) {
                System.out.println("Invalid command");
            }
        }
    }
}
