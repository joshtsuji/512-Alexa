package projector;

import com.mashape.unirest.http.Unirest;

/**
 * Created by hubspot on 7/24/15.
 */
public class ProjectorClient {
    public static void turnOn() {
        Unirest.get("http://146.115.86.220:85/event?projector");

    }

    public static void turnOff() {
        Unirest.get("http://146.115.86.220:85/event?projector");
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Unirest.get("http://146.115.86.220:85/event?projector");
    }
}
