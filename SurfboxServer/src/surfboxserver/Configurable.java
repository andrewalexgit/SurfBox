package surfboxserver;

/*
*	Surfbox 3 - Configurable Interface
*
*	Developed by, Andrew C.
*       
**/

public interface Configurable {
    
    /*
     * Get probe configuration
    **/
    public String getProbeConfig(int index, String key);
    
    /*
     * Get device configuration
    **/
    public String getDeviceConfig(int index, String key);
    
    /*
     * Update probe configuration
    **/
    public void updateProbeConfiguration(int index, String key, String value);
    
    /*
     * Update device configuration
    **/
    public void updateDeviceConfiguration(int index, String key, String value);
    
    /*
     * Get probe count
    **/
    public int getProbeCount();
    
    /*
     * Get device count
    **/
    public int getDeviceCount();
    
    /*
     * Print new JSON API
    **/
    public void updateAPI();
    
    
}
