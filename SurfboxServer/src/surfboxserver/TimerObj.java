package surfboxserver;

/*
*	Instance of Timer Objects
*
*	Developed by, Andrew C.
**/

public abstract class TimerObj {
    
    /*
     * Holds the device object to be affected by timer
    **/
    int deviceAddr;
    
    /*
     * Access to API Configuration manager
    **/
    Configuration config;
    
    /*
     * Access to the device object
    **/
    Device device;

    /*
     * Timer thread
    **/
    private Thread thread;
    
    /*
     * Timer status
    **/
    boolean fired = false;
    
    protected TimerObj(Configuration config, int deviceAddr, Device device) {
        this.deviceAddr = deviceAddr;
        this.config = config;
        this.device = device;
    }
    
    public abstract void start(); // Thread initializer
    
}
