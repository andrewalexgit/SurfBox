package surfboxserver;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/*
*	Server Configuration
*
*	Developed by, Andrew C.
**/

public class Configuration {
    
    /*
     * Properties object
    **/
    private Properties properties;
    
    /*
     * Input Stream for reading properties file
    **/
    private InputStream input;
    
    public Configuration(String filename) {
        
        properties = new Properties();
        
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
     * Returns value stored in properties file associated with a specific key
    **/
    public String getConfig(String key) {
        return properties.getProperty(key);
    }
}
