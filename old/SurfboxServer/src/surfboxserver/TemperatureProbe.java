package surfboxserver;

/*
*	Temperature Probe Object
*
*	Developed by, Andrew C.
**/

public class TemperatureProbe extends ProbeObj {
    
    /*
     * 0 - Fharenheit, 1 - Celcius, 2 - Kelvin
    **/
    private byte unit;

    public TemperatureProbe(float min, float max, float target, float tolerance, byte unit) {
        super("temp", min, max, target, tolerance);
        this.unit = unit;
    }
    
    public TemperatureProbe() {
        this(74.0f, 82.0f, 78.0f, 1.0f, (byte) 0);
    }
    
    /*
     * Checks the status of all parameters
    **/
    @Override
    public String paramsCheck() {
        
        String message = "Temperature is ";
        
        if (isHigh()) {
            message += "high out of range";
        }
        
        else if (isLow()) {
            message += "low out of range";
        }
        
        else if (reading == target) {
            message += "perfect";
        }
        
        else if (isWithinTolerance()) {
            message += "within tolerance";
        }
        
        else if (!isWithinTolerance()) {
            message += "out of tolerance";
        }
        
        return message;
    }
}
