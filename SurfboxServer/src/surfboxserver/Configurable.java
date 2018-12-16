package surfboxserver;

/*
*	Configurable Interface
*
*	Developed by, Andrew C.
**/

public interface Configurable {
    
    public String getConfig(String key); // Get Property
    
    public void setConfig(String key, String value); // Updates Property Value
    
    public void save(); // Saves all configuration changes
    
}
