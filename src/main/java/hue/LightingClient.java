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

    static {
        nameToId.put("light green", "afd39e8fa-on-0");
        nameToId.put("primaries", "b7a556b43-on-0");
        nameToId.put("turquoise", "0dd0eb23b-on-0");
        nameToId.put("rainbow", "a36922ffb-on-0");
    }

    public static String changeLights(String sceneInput) {

        String sceneGuess = "";
        int lowestSimilarity = Integer.MAX_VALUE;
        for (String scene : nameToId.keySet()) {
            int dist = StringUtils.getLevenshteinDistance(scene, sceneInput);
            if (dist < lowestSimilarity) {
                lowestSimilarity = dist;
                sceneGuess = scene;
            }
        }

        try {
            JSONObject body = new JSONObject();
            body.put("scene", nameToId.get(sceneGuess));
            body.put("transitiontime", 10);

            HttpResponse<JsonNode> jsonResponse = Unirest.put("http://146.115.86.220:86/api/newdeveloper/groups/0/action")
                    .header("Content-Type", "application/json")
                    .body(new JsonNode(body.toString()))
                    .asJson();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return sceneGuess;

    }

    /*
    // Build the post string from an object
    var post_data = "{\"scene\": \"" + sceneId + "\"}"

    // An object of options to indicate where to post to
    var post_options = {
        host: '146.115.86.220',
        port: '86',
        path: '',
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
            'Content-Length': post_data.length
        }
    };

    // Set up the request
    var post_req = http.request(post_options, function(res) {
        res.setEncoding('utf-8');
        res.on('data', function (chunk) {
            console.log('Response: ' + chunk);
            postResponse({
                title: "Changing scene.",
                speech: "Changing scene to " + sceneName,
                reprompt: "Which scene?",
                end: true
            }, callback);
        });
    });

    // post the data
    post_req.write(post_data);
    post_req.end();
     */
}
