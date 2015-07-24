package projector;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * Created by hubspot on 7/24/15.
 */
public class ProjectorClient {
    public static void turnOn() {
        if (true) return;
        try {
            Unirest.get("http://146.115.86.220:85/event?projector").asString();
        } catch (UnirestException e) {
            e.printStackTrace();
        }


    }

    public static void turnOff() {
        if (true) return;
        try {
            Unirest.get("http://146.115.86.220:85/event?projector").asString();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Unirest.get("http://146.115.86.220:85/event?projector").asString();
        }
        catch (Exception e) {

        }
    }
}
