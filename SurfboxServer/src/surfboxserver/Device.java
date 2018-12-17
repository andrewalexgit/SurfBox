package surfboxserver;

/*
*	Instance of Device Objects
*
*	Developed by, Andrew C.
**/

public abstract class Device {
    
    /*
     * Name of Device
    **/
    String name;
    
    /*
     * Relay number
    **/
    byte relay;
    
    /*
     * Status of relay
    **/
    boolean status;
    
    protected Device(String name, byte relay, boolean status) {
        this.name = name;
        this.relay = relay;
        this.status = status;
    }
    
    public abstract void setDevice(boolean status); // Change device status on or off
    
}
