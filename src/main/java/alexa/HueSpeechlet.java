package alexa;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.slu.Slot;
import com.amazon.speech.speechlet.*;
import hue.LightingClient;

import java.util.Map;

/**
 * This sample shows how to create a simple speechlet for handling intent requests and managing
 * session interactions.
 */
public class HueSpeechlet extends BaseSpeechlet {

    boolean isBedroom = false;

    public HueSpeechlet(boolean isBedroom) {
        this.isBedroom = isBedroom;
    }

    public SpeechletResponse onLaunch(final LaunchRequest request, final Session session)
            throws SpeechletException {

        return buildSpeechletResponse("Lighting",
                "Entering lighting control. Ask to change to a scene, or to turn areas on or off.",
                "Say a command.",
                false);
    }

    public SpeechletResponse onIntent(final IntentRequest request, final Session session)
            throws SpeechletException {

        Intent intent = request.getIntent();
        String intentName = (intent != null) ? intent.getName() : null;

        LightingClient.isBedroom = this.isBedroom;

        if ("ChangeScene".equals(intentName)) {
            return handleChangeScene(intent);
        }
        else if ("TurnOff".equals(intentName)) {
            return handleLightsOff(intent);
        }
        else if ("TurnOn".equals(intentName)) {
            return handleLightsOn(intent);
        }
        else {
            throw new SpeechletException("Invalid Intent");
        }
    }

    public SpeechletResponse handleChangeScene(Intent intent) {
        Map<String, Slot> slots = intent.getSlots();
        Slot sceneSlot = slots.get("SceneName");
        String reqScene = sceneSlot.getValue();

        String scene = LightingClient.changeLights(reqScene);

        return buildSpeechletResponse("Lighting",
                "Okay.",
                "", true);
    }

    public SpeechletResponse handleLightsOff(Intent intent) {
        LightingClient.turnOffLights();
        return buildSpeechletResponse("Lighting",
                "Okay.",
                "", true);
    }

    public SpeechletResponse handleLightsOn(Intent intent) {
        LightingClient.turnOnLights();
        return buildSpeechletResponse("Lighting",
                "Okay." + LightingClient.currentScene,
                "", true);
    }

}