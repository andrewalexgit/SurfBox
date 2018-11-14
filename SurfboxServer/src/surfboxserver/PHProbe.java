package surfboxserver;

/*
*	PH Probe Object
*
*	Developed by, Andrew C.
**/

public class PHProbe extends ProbeObj{
    
    public PHProbe(float min, float max, float target, float tolerance) {
        super("ph", min, max, target, tolerance);
    }
    
    public PHProbe() {
        this(7.0f, 9.0f, 8.5f, 0.2f);
    }
    
    /*
     * Checks the status of all parameters
    **/
    @Override
    public String paramsCheck() {
        
        String message = "PH is ";
        
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
