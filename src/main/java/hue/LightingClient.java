package hue;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by hubspot on 6/16/15.
 */
public class LightingClient {

    private static HashMap<String, String> nameToId = new HashMap<String, String>();
    private static HashMap<String, String> nameToBedroomId = new HashMap<String, String>();
    private static HashMap<String, Integer> nameToLight = new HashMap<String, Integer>();

    public static boolean isBedroom = false;

    public static String currentScene = "white";

    static {
        nameToId.put("light green", "afd39e8fa-on-0");
        nameToId.put("white", "9f3b39306-on-0");
        nameToId.put("bright", "e69affaad-on-0");
        nameToId.put("green", "6f4eec56f-on-0");
        nameToId.put("red", "cc0afa4b2-on-0");
        nameToId.put("pink", "97d596458-on-0");
        nameToId.put("yellow", "c141feb9f-on-0");
        nameToId.put("primaries", "b7a556b43-on-0");
        nameToId.put("turquoise", "0dd0eb23b-on-0");
        nameToId.put("rainbow", "a36922ffb-on-0");
        nameToId.put("red gradients", "27a3f34d1-on-0");
        nameToId.put("sunrise", "4a26e7ccc-on-0");
        nameToId.put("sunset", "4a26e7ccc-on-0");
        nameToId.put("underwater", "0b58d5a80-on-0");
        nameToId.put("blue", "0b58d5a80-on-0");
        nameToId.put("blue and white", "0b58d5a80-on-0");

        nameToBedroomId.put("sunset", "db4cf55c3-on-0");
        nameToBedroomId.put("turqoise", "33e6af684-on-0");
        nameToBedroomId.put("rainbow", "2cda827f7-on-0");
        nameToBedroomId.put("night mode", "8dc47d2bf-on-0");

        nameToLight.put("bar", 10);
    }

    public static HashMap<String, String> getIdHashMap() {
        return isBedroom ? nameToBedroomId : nameToId;
    }

    public static void turnOffLights() {
        turnOffLights(4);
    }

    public static void turnOffLights(int transitionTime) {
        fireSceneRequest(isBedroom ? "699524d17-on-0" : "90fc10b39-on-0", transitionTime);
    }

    public static void turnOnLights() {
        fireSceneRequest(getIdHashMap().get(currentScene));
    }

    public static void turnOnLight(String light) {

    }

    public static void turnOnLight(String light, int h, int s, int b, int time) {
        fireSingleLightRequest(nameToLight.get(light), h, s, b, time);
    }

    public static void turnOffLight(String light) {

    }

    public static String changeToNearestSceneMatchingNAme(String sceneInput) {

        if (sceneInput == null)
            sceneInput = "";

        String sceneGuess = "";
        int lowestSimilarity = Integer.MAX_VALUE;
        for (String scene : getIdHashMap().keySet()) {
            int dist = StringUtils.getLevenshteinDistance(scene, sceneInput);
            if (dist < lowestSimilarity) {
                lowestSimilarity = dist;
                sceneGuess = scene;
            }
        }

        currentScene = sceneGuess;

        try {
            fireSceneRequest(getIdHashMap().get(sceneGuess));
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return sceneGuess;

    }

    public static void fireSingleLightRequest(int light, float hue, float sat, float bri, int time) {
        try {
            JSONObject body = new JSONObject();
            body.put("on", bri > 0);
            body.put("bri", bri);
            body.put("hue", hue);
            body.put("time", time / 100);

            System.out.println("Firing light request.");


            HttpResponse<JsonNode> jsonResponse = Unirest.put("http://146.115.86.220:86/api/newdeveloper/lights/" + light + "/state")
                    .header("Content-Type", "application/json")
                    .body(new JsonNode(body.toString()))
                    .asJson();

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void fireSceneRequest(String scene) {
        fireSceneRequest(scene, 4);
    }

    public static void fireSceneRequest(String scene, int transitionTime) {
        try {
            JSONObject body = new JSONObject();
            body.put("scene", scene);
            body.put("transitiontime", transitionTime);

            HttpResponse<JsonNode> jsonResponse = Unirest.put("http://146.115.86.220:86/api/newdeveloper/groups/0/action")
                    .header("Content-Type", "application/json")
                    .body(new JsonNode(body.toString()))
                    .asJson();

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void fireDimAllLightsRequest(boolean on, int time) {
        try {
            JSONObject body = new JSONObject();
            body.put("bri", on? 255 : 0);
            body.put("on", on);
            body.put("transitiontime", time / 100);

            HttpResponse<JsonNode> jsonResponse = Unirest.put("http://146.115.86.220:86/api/newdeveloper/groups/0/action")
                    .header("Content-Type", "application/json")
                    .body(new JsonNode(body.toString()))
                    .asJson();

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
