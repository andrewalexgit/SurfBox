package surfboxserver;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
/*
*	Timer Thread
*
*	Developed by, Andrew C.
**/
public class Timer implements Runnable {

    // Static id of timer
    private static int id = 0;
    
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
     * Timestamp Format
    **/
    private static final SimpleDateFormat stampformat = new SimpleDateFormat("HH.mm.ss");

    /*
     * Devices on time
    **/
    private String onTime;

    /*
     * Devices off time
    **/
    private String offTime;

    /*
     * Timer thread
    **/
    private Thread thread;
    
    /*
     * Timer status
    **/
    boolean fired = false;

    public Timer(String onTime, String offTime, Configuration config, int deviceAddr, Device device) {
        this.onTime = onTime;
        this.offTime = offTime;
        this.deviceAddr = deviceAddr;
        this.config = config;
        this.device = device;
        id++;
    }

    /*
     * Change the on time of the device
    **/
    public void changeOnTime(String onTime) {
        this.onTime = onTime;
    }

    /*
     * Change the off time of the device
    **/
    public void changeOffTime(String offTime) {
        this.offTime = offTime;
    }

    /*
     * Get on time
    **/
    public String getOnTime() {
        return onTime;
    }

    /*
     * Get off time
    **/
    public String getOffTime() {
        return offTime;
    }

    // THREAD //
    public void start() {
        if (thread == null) {
            thread = new Thread(this, "timer" + id);
            thread.start();
        }
    }

    @Override
    public void run() {

        while (true) {
            
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            
            if (onTime.equals(stampformat.format(timestamp)) && fired == false) {
                config.updateDeviceConfiguration(deviceAddr, "status", "1");
                System.out.println(config.getDeviceConfig(deviceAddr, "name") + " fired on!");
                System.out.println("API Updated status code " + config.getDeviceConfig(deviceAddr, "status"));
                device.status = true;
                fired = true;
            } else {
                if (offTime.equals(stampformat.format(timestamp)) && fired == true) {
                    config.updateDeviceConfiguration(deviceAddr, "status", "0");
                    System.out.println(config.getDeviceConfig(deviceAddr, "name") + " fired off!");
                    System.out.println("API Updated status code " + config.getDeviceConfig(deviceAddr, "status"));
                    device.status = true;
                    fired = false;
                }
            }
        }
    }
}
