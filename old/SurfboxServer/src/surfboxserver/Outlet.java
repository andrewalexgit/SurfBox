package surfboxserver;

/*
*	Instance of Device Objects
*
*	Developed by, Andrew C.
**/

public class Outlet extends Device {
    
    public Outlet(String name, byte relay, boolean status) {
        super(name, relay, status);
    }
    
    /*
     * Change device status on or off
    **/
    @Override
    public void setDevice(boolean status) {
        super.status = status;
    }
}
