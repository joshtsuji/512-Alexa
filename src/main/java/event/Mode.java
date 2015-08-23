package event;

import hue.LightingClient;

/**
 * Created by hubspot on 8/23/15.
 */
public class Mode {
    public void handleBarProximity(boolean active) {
        if (active) {
            LightingClient.turnOnLight("bar", 255, 0, 0);
        }
        else {
            LightingClient.turnOnLight("bar", 0, 255, 0);
        }
    }
}
