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

    public static boolean isBedroom = false;

    static {
        nameToId.put("light green", "afd39e8fa-on-0");
        nameToId.put("white", "e69affaad-on-0");
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
    }

    public static HashMap<String, String> getIdHashMap() {
        return isBedroom ? nameToBedroomId : nameToId;
    }

    public static void turnOffLights() {
        fireLightRequest(isBedroom ? "699524d17-on-0" : "90fc10b39-on-0");
    }

    public static String changeLights(String sceneInput) {

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

        try {
            fireLightRequest(getIdHashMap().get(sceneGuess));
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return sceneGuess;

    }

    public static void fireLightRequest(String scene) {
        try {
            JSONObject body = new JSONObject();
            body.put("scene", scene);

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
