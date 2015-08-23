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

    Timer safetyTimer;


    public void handleBarProximity(boolean active) {
        if (active) {
            timeActive = new Date();
            LightingClient.changeSingleLight("bar", 0, 255, 255, 1000);

            System.out.println("Going fully active");

            if (safetyTimer != null)
                safetyTimer.stop();

            safetyTimer = new Timer((int) turnOffDelay, new ActionListener() {

                public void actionPerformed(ActionEvent arg0) {
                    System.out.println("Firing safety timer");
                    LightingClient.changeSingleLight("bar", 0, 0, 0, 5000);
                }
            });
            safetyTimer.setRepeats(false);
            safetyTimer.start();
        }
    }
}
