package surfboxserver;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/*
*	Surfbox 3 - Configuration Handler
*
*	Developed by, Andrew C.
*       
*       Additional credits to Clifton Labs for JSON support
**/

public class Configuration implements Configurable {

    /*
     * Holds probe configuration settings
    **/
    JSONArray probes;

    /*
     * Holds device configuration settings
    **/
    JSONArray devices;
    
    /*
     * Holds timer configuration settings
    **/
    JSONArray timers;
    
    /*
     * Logger data
    **/
    JSONArray logger;
    
    /*
     * Main server settings
    **/
    JSONObject settings;

    /*
     *  JSON Parser Object
    **/
    final private static JSONParser PARSER = new JSONParser();

    /*
     * JSON Object
    **/
    JSONObject configurationObject;

    /*
     * Root object
    **/
    Object obj;
    
    /*
     * Path to API
    **/
    String apiPath;

    public Configuration(String apiPath) {
        try {
            this.apiPath = apiPath;
            obj = PARSER.parse(new FileReader(apiPath));
            configurationObject = (JSONObject) obj;
            probes = (JSONArray) configurationObject.get("probes");
            devices = (JSONArray) configurationObject.get("devices");
            timers = (JSONArray) configurationObject.get("timers");
            logger = (JSONArray) configurationObject.get("logger");
            settings = (JSONObject) configurationObject.get("settings");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Configuration.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | ParseException ex) {
            Logger.getLogger(Configuration.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /*
     * Get probe configuration
    **/
    @Override
    public String getProbeConfig(int index, String key) {
        JSONObject probe = (JSONObject) probes.get(index);
        return (String) probe.get(key);
    }

    /*
     * Get device configuration
    **/
    @Override
    public String getDeviceConfig(int index, String key) {
        JSONObject device = (JSONObject) devices.get(index);
        return (String) device.get(key);
    }
    
    /*
     * Get timer configuration
    **/
    @Override
    public String getTimerConfig(int index, String key) {
        JSONObject timer = (JSONObject) timers.get(index);
        return (String) timer.get(key);
    }
    
    /*
     * Get device configuration
    **/
    @Override
    public String getSettingsConfig(String key) {
        return (String) settings.get(key);
    }
    
    /*
     * New logger entry
    **/
    @Override
    public void updateLogger(String key, String value, String name) {
        JSONObject log = new JSONObject();
        log.put("name", name);
        log.put(key, value);
        logger.add(log);
    }
    
    /*
     * Clear logger for new program run
    **/
    public void clearLogger() {
        logger.clear();
        updateAPI();
    }

    /*
     * Update probe configuration
    **/
    @Override
    public void updateProbeConfiguration(int index, String key, String value) {
        JSONObject probe = (JSONObject) probes.get(index);
        probe.put(key, value);
    }

    /*
     * Update device configuration
    **/
    @Override
    public void updateDeviceConfiguration(int index, String key, String value) {
        JSONObject device = (JSONObject) devices.get(index);
        device.put(key, value);
    }
    
    /*
     * Update timer configuration
    **/
    @Override
    public void updateTimerConfiguration(int index, String key, String value) {
        JSONObject timer = (JSONObject) timers.get(index);
        timer.put(key, value);
    }
    
    /*
     * Update settings object
    **/
    @Override
    public void updateSettingsConfiguration(String key, String value) {
        settings.put(key, value);
    }
    
    /*
     * Get probe count
    **/
    @Override
    public int getProbeCount() {
        return probes.size();
    }
    
    /*
     * Get device count
    **/
    @Override
    public int getDeviceCount() {
        return devices.size();
    }
    
    /*
     * Get timer count
    **/
    @Override
    public int getTimerCount() {
        return timers.size();
    }

    /*
     * Print new JSON API
    **/
    @Override
    public void updateAPI() {
        try (PrintWriter writer = new PrintWriter(new File(apiPath))) {
            writer.print("");
            writer.print(configurationObject);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Configuration.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}