package surfboxserver;

/*
*	Device Control Interface
*
*	Developed by, Andrew C.
**/

public interface Controllable {
    
    public void setDevice(boolean status);
    public boolean deviceStatus();
    
}
