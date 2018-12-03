package surfboxserver;

/*
*	EC Probe Object
*
*	Developed by, Andrew C.
**/

public class ECProbe extends ProbeObj {
    
    public ECProbe(float min, float max, float target, float tolerance) {
        super("ec", min, max, target, tolerance);
    }
    
    public ECProbe() {
        this(1.022f, 1.030f, 1.026f, 0.001f);
    }

    /*
     * Checks the status of all parameters
    **/
    @Override
    public String paramsCheck() {
        
        String message = "EC is ";
        
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
