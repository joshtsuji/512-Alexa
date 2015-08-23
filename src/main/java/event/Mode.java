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
            LightingClient.turnOnLight("bar", 0, 255, 255, 3000);
        }
        else {
            LightingClient.turnOnLight("bar", 0, 0, 0, 10000);
        }
    }
}
