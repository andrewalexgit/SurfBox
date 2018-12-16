package surfboxserver;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
*	Server Configuration
*
*	Developed by, Andrew C.
**/

public class Configuration implements Configurable {
    
    /*
     * Properties object
    **/
    private Properties properties;
    
    /*
     * Input Stream for reading properties file
    **/
    private InputStream input;
    
    /*
     * Output Stream for saving properties file
    **/
    private FileOutputStream out;
    
    /*
     * Stores configuration filepath
    **/
    private String filename;
    
    public Configuration(String filename) {
        
        properties = new Properties();
        
        this.filename = filename;
        
        try {
            input = new FileInputStream(filename);
            properties.load(input);
        } catch (FileNotFoundException ex) {
            //<10> Properties file now found
            System.out.println("<10>");
        } catch (IOException ex) {
            //<11> Error loading properties file
            System.out.println("<11>");
        }
    }
    
    /*
     *  Returns value stored in properties file associated with a specific key
    **/
    @Override
    public String getConfig(String key) {
        return properties.getProperty(key);
    }
    
    /*
     *  Updates value stored in properties file associated with a specific key
    **/
    @Override
    public void setConfig(String key, String value) {
        properties.setProperty(key, value);
    }
    
    /*
     *  Save configration changes
    **/
    @Override
    public void save() {
        try {
            properties.store(new FileOutputStream(filename), "SBSCM3");
        } catch (IOException ex) {
            Logger.getLogger(Configuration.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
