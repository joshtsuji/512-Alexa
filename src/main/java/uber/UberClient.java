package uber;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;

/**
 * Created by hubspot on 6/22/15.
 */
public class UberClient {
    public static int getEstimate() {
        try {
            HttpResponse<JsonNode> jsonResponse = Unirest.get(
                    "https://api.uber.com/v1/estimates/time?server_token=bv0xotfnQ9IoCfkugQcyU4t1tvQ0EvMeoOfW9H0C&start_longitude=-71.097997&start_latitude=42.343682")
                    .header("Content-Type", "application/json")
                    .asJson();

            return jsonResponse.getBody().getArray().getJSONObject(0).getInt("estimate");
        }
        catch (Exception e) {

        }

        return 0;
    }
}
