package event;

import hue.LightingClient;

import java.util.Date;

/**
 * Created by hubspot on 8/23/15.
 */
public class Mode {
    Date timeActive = new Date();
    public void handleBarProximity(boolean active) {
        if (active) {
            timeActive = new Date();
            LightingClient.changeSingleLight("bar", 0, 255, 255, 0);
        }
        else {
            LightingClient.changeSingleLight("bar", 0, 0, 0, 10000);
        }
    }
}
