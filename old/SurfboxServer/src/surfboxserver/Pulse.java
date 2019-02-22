package surfboxserver;

public class Pulse extends TimerObj implements Runnable {

    // Static id of timer
    private static int id = 0;

    /*
     * Devices on time
    **/
    private int pulsewidth;

    /*
     * Pulser start time reference
    **/
    long timereference = System.currentTimeMillis();

    /*
     * Timer thread
    **/
    private Thread thread;

    public Pulse(int pulsewidth, Configuration config, int deviceAddr, Device device) {
        super(config, deviceAddr, device);
        this.pulsewidth = pulsewidth;
        id++;
    }

    /*
     * Change the on pulse width of timer
    **/
    public void changePulsewidth(int pulsewidth) {
        this.pulsewidth = pulsewidth;
    }

    /*
     * Get on pulse width
    **/
    public int getPulsewidth() {
        return pulsewidth;
    }

    // THREAD //
    @Override
    public void start() {
        if (thread == null) {
            System.out.println("Started pulse thread");
            thread = new Thread(this, "timer" + id);
            thread.start();
        }
    }

    @Override
    public void run() {

        while (true) {
            if ((timereference + pulsewidth) == System.currentTimeMillis() && fired == false) {
                config.updateDeviceConfiguration(deviceAddr, "status", "1");
                System.out.println(config.getDeviceConfig(deviceAddr, "name") + " fired on!");
                System.out.println("API Updated status code " + config.getDeviceConfig(deviceAddr, "status"));
                device.status = true;
                fired = true;
                timereference = System.currentTimeMillis();
            } else if ((timereference + pulsewidth) == System.currentTimeMillis() && fired == true) {
                config.updateDeviceConfiguration(deviceAddr, "status", "0");
                System.out.println(config.getDeviceConfig(deviceAddr, "name") + " fired off!");
                System.out.println("API Updated status code " + config.getDeviceConfig(deviceAddr, "status"));
                device.status = true;
                fired = false;
                timereference = System.currentTimeMillis();
            }
        }
    }
}
