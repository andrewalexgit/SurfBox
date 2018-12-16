package surfboxserver;

import java.util.*;

/*
*	The main class for SurfBox2 - Server
*
*	Developed by, Andrew C.
**/

public class SurfboxConfigManager {
    
    /*
     *  Main program flag
    **/
    private static boolean run = false;
    
    /*
     *  Keyboard input
    **/
    private static Scanner keyboard = new Scanner(System.in);
    
    /*
     *  Command parse
    **/
    private static Scanner parse;
    
    /*
     *  Command line store
    **/
    private static String[] inputs = { " ", " ", " " };
    
    public static void run() {
        
        System.out.println("SURFBOX 3 CONFIGURATION MODE");
        System.out.print("Loading properties file...");
        Configuration config = new Configuration("/Users/andrewcampagna/NetBeansProjects/SurfboxServer/src/surfboxserver/config.properties");
        System.out.print("Done!\n");
        run = true;
        
        System.out.println("The server will begin upon exiting the configuration manager");
        
        /*
         *  Main Program Loop
        **/
        
        while(run) {
            
            System.out.print("$ > ");
            String data = keyboard.nextLine();
            parse = new Scanner(data);
            
            try {
                inputs[0] = parse.next();
                inputs[1] = parse.next();
                inputs[2] = parse.next();
            } catch (NoSuchElementException nse) {
                // Ignore exception, not every command involves 3 lines of input
            } 
            
            if (inputs[0].equals("get")) {
                System.out.println(inputs[1] + "=" + config.getConfig(inputs[1]));
            } else if (inputs[0].equals("update")) {
                config.setConfig(inputs[1], inputs[2]);
                System.out.println(inputs[1] + "=" + config.getConfig(inputs[1]));
            } else if (inputs[0].equals("exit")) {
                System.out.println("Exiting configuration manager and starting program \n");
                config.save();
                run = false;
            } else {
                System.out.println("Invalid command");
            }
        }
    }
}
