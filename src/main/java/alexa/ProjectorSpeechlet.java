package alexa;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.*;
import hue.LightingClient;
import projector.ProjectorClient;

/**
 * This sample shows how to create a simple speechlet for handling intent requests and managing
 * session interactions.
 */
public class ProjectorSpeechlet extends BaseSpeechlet {

    public SpeechletResponse onLaunch(final LaunchRequest request, final Session session)
            throws SpeechletException {

        return buildSpeechletResponse("Uber",
                "Hello, do you want to call an uber?",
                "Say a command.",
                false);
    }

    public SpeechletResponse onIntent(final IntentRequest request, final Session session)
            throws SpeechletException {

        Intent intent = request.getIntent();
        String intentName = (intent != null) ? intent.getName() : null;

        if ("WatchTVIntent".equals(intentName)) {
            return handleTurnOn(intent);
        }
        else if ("WatchTVOffIntent".equals(intentName)) {
            return handleTurnOff(intent);
        }
        else {
            throw new SpeechletException("Invalid Intent");
        }
    }

    public SpeechletResponse handleTurnOn(Intent intent) {
        ProjectorClient.turnOn();
        LightingClient.fireDimRequest(false, 60000);


        return buildSpeechletResponse("Projector",
                "Warming up the projector. Lighting will dim in one minute.",
                "", true);
    }


    public SpeechletResponse handleTurnOff(Intent intent) {
        ProjectorClient.turnOff();
        LightingClient.fireDimRequest(true, 10000);
        return buildSpeechletResponse("Uber",
                "Okay",
                "", true);
    }

}