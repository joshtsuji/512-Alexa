package event;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by hubspot on 8/16/15.
 */
public class EventServlet extends HttpServlet {

    Mode activeMode = new Mode();

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws ServletException, IOException
    {
        String event = request.getParameter("event");

        if (event.equals("barproximity")) {
            boolean active = request.getParameter("active").equals("true");
            System.out.println("Handling bar proximity.");
            activeMode.handleBarProximity(active);
        }
    }

    public void destroy() {

    }
}
