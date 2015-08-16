package alexa;

import com.amazon.speech.speechlet.*;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.Reprompt;
import com.amazon.speech.ui.SimpleCard;

import javax.servlet.http.HttpServlet;

/**
 * Created by hubspot on 8/16/15.
 */
public abstract class BaseSpeechlet extends HttpServlet implements Speechlet {

    public void onSessionStarted(final SessionStartedRequest request, final Session session)
            throws SpeechletException {

        System.out.println("Starting Alexa session.");

    }

    public void onSessionEnded(final SessionEndedRequest request, final Session session)
            throws SpeechletException {

    }

    /**
     * Creates and returns the visual and spoken response with shouldEndSession flag
     *
     * @param title
     *            title for the companion application home card
     * @param output
     *            output content for speech and companion application home card
     * @param repromptText
     *            the text the should be spoken to the user if they user either fails to reply to a
     *            question or says something that was not understood
     * @param shouldEndSession
     *            should the session be closed
     * @return SpeechletResponse spoken and visual response for the given input
     */
    SpeechletResponse buildSpeechletResponse(final String title, final String output,
                                                     final String repromptText, final boolean shouldEndSession) {
        // Create the Simple card content.
        SimpleCard card = new SimpleCard();
        card.setTitle(String.format("SessionSpeechlet - %s", title));
        card.setContent(String.format("SessionSpeechlet - %s", output));

        // Create the plain text output.
        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText(output);

        // Create the speechlet response.
        SpeechletResponse response = new SpeechletResponse();
        response.setShouldEndSession(shouldEndSession);
        response.setOutputSpeech(speech);
        response.setCard(card);
        response.setReprompt(buildReprompt(repromptText));
        return response;
    }

    /**
     * Builds the Reprompt object to be used as part of the SpeechletResponse.
     *
     * @param repromptText
     *            The text that will be spoken to the user when a reprompt occurs
     * @return Reprompt
     */
    Reprompt buildReprompt(final String repromptText) {
        PlainTextOutputSpeech repromptSpeech = new PlainTextOutputSpeech();
        repromptSpeech.setText(repromptText);

        Reprompt reprompt = new Reprompt();
        reprompt.setOutputSpeech(repromptSpeech);

        return reprompt;
    }
}
