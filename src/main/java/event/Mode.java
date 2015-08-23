package event;

import hue.LightingClient;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

/**
 * Created by hubspot on 8/23/15.
 */
public class Mode {
    Date timeActive = new Date();
    long turnOffDelay = 1000 * 10;
    public void handleBarProximity(boolean active) {
        if (active) {
            timeActive = new Date();
            LightingClient.changeSingleLight("bar", 0, 255, 255, 1000);
        }
        else {
            if (new Date().getTime() - timeActive.getTime() < turnOffDelay) {
                Timer timer = new Timer((int)(new Date().getTime() - timeActive.getTime()), new ActionListener() {

                    public void actionPerformed(ActionEvent arg0) {
                        LightingClient.changeSingleLight("bar", 0, 0, 0, 10000);
                    }
                });
                timer.setRepeats(false);
                timer.start();
            }
            else {
                LightingClient.changeSingleLight("bar", 0, 0, 0, 10000);
            }
        }
    }
}
