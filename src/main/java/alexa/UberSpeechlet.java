package alexa;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.*;
import uber.UberClient;

/**
 * This sample shows how to create a simple speechlet for handling intent requests and managing
 * session interactions.
 */
public class UberSpeechlet extends BaseSpeechlet {

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

        if ("UberTime".equals(intentName)) {
            return handleUberTime(intent);
        }
        else {
            throw new SpeechletException("Invalid Intent");
        }
    }

    public SpeechletResponse handleUberTime(Intent intent) {
        int estimate = UberClient.getEstimate();
        return buildSpeechletResponse("Uber",
                "The nearest Uber is " + estimate + " seconds away.",
                "", true);
    }

}