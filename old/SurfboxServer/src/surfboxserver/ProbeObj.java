package surfboxserver;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Date;

/*
*	Instance of Probe Objects
*
*	Developed by, Andrew C.
**/

public abstract class ProbeObj {
    
    /*
     * Name of probe
    **/
    String type;
    
    /*
     * Minimum safe operating range
    **/
    float min;
    
    /*
     * Maximum safe operating range
    **/
    float max;
    
    /*
     * Target reading
    **/
    float target;
    
    /*
     * Acceptable tolerance for probe reading
    **/
    float tolerance;
    
    /*
     * Holds probes last read value
    **/
    float reading;
    
    /*
     * Hash-table for readings and corrosponding times
    **/
    HashMap<String, Float> history = new HashMap<>();
    
    protected ProbeObj(String name, float min, float max, float target, float tolerance) {
        this.type = name;
        this.min = min;
        this.max = max;
        this.target = target;
        this.tolerance = tolerance;
    }
    
    /*
     * Loads the most recently read value into memory and creates new entry into the history hash-table.
    **/
    public void setReading(float reading) {
        this.reading = reading;
        history.put(getDateTime(), reading);
    }
    
    /*
     * Checks if reading is high out of range
    **/
    public boolean isHigh() {
        return (reading > max);
    }
    
    /*
     * Checks if reading is low out of range
    **/
    public boolean isLow() {
        return (reading < min);
    }
    
    /*
     * Checks if reading is within tolerance
    **/
    public boolean isWithinTolerance() {
        boolean b = true;
        if (reading > (target+tolerance)) {
            b = false;
        } else if (reading < (target-tolerance)) {
            b = false;
        }
        return b;
    }
    
    /*
     * Gets most recent reading
    **/
    public float getReading() {
        return reading;
    }
    
    /*
     * Overload method to get reading on specific date and time
    **/
    public float getReading(String date) {
        try {
            return history.get(date);
        } catch (Exception ex) {
            return -1;
        }
    }
    
    /*
     * Stores new readings into memory
    **/
    public void newReading(float reading) {
        setReading(reading);
        history.put(getDateTime(), reading);
    }
    
    /*
     * Probe type identification
    **/
    public String type() {
        return type;
    }
    
    /*
     * Compare probe types
    **/
    public boolean equalsType(String otherType) {
        return (type.equals(otherType));
    }
    
    /*
     * Provides date and time string
    **/
    private String getDateTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
        Date date = new Date();  
        return formatter.format(date);  
    }
    
    public abstract String paramsCheck();
}
