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
     * Get timer configuration
    **/
    public String getTimerConfig(int index, String key);
    
    /*
     * Get pulser configuration
    **/
    public String getPulserConfig(int index, String key);
    
    /*
     * Get dosing pump configuration
    **/
    public String getDosingpumpsConfig(int index, String key);
    
    /*
     * Get device configuration
    **/
    public String getSettingsConfig(String key);
    
    /*
     * Update probe configuration
    **/
    public void updateProbeConfiguration(int index, String key, String value);
    
    /*
     * Update device configuration
    **/
    public void updateDeviceConfiguration(int index, String key, String value);
    
    /*
     * Update timer configuration
    **/
    public void updateTimerConfiguration(int index, String key, String value);
    
    /*
     * Update pulser configuration
    **/
    public void updatePulserConfiguration(int index, String key, String value);
    
    /*
     * Update dosing pump configuration
    **/
    public void updateDosingpumpsConfiguration(int index, String key, String value);
    
    /*
     * Update settings object
    **/
    public void updateSettingsConfiguration(String key, String value);
    
    /*
     * New logger entry
    **/
    public void updateLogger(String key, String value, String name);
    
    /*
     * Get probe count
    **/
    public int getProbeCount();
    
    /*
     * Get device count
    **/
    public int getDeviceCount();
    
    /*
     * Get timer count
    **/
    public int getTimerCount();
    
    /*
     * Get pulser count
    **/
    public int getPulserCount();
    
    /*
     * Get dosing pump count
    **/
    public int getDosingpumpsCount();
    
    /*
     * Print new JSON API
    **/
    public void updateAPI();
    
    
}
