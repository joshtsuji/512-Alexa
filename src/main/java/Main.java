import alexa.HueSpeechlet;
import alexa.ProjectorSpeechlet;
import alexa.UberSpeechlet;
import com.amazon.speech.speechlet.Speechlet;
import com.amazon.speech.speechlet.servlet.SpeechletServlet;
import event.EventServlet;
import hue.HueServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.*;

public class Main extends HttpServlet {

  public static final String HOME_IP = "209.6.233.80";

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    if (req.getRequestURI().endsWith("/db"))
      showDatabase(req,resp);
    else {
      showHome(req, resp);
    }
  }

  private void showHome(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    resp.getWriter().print("Hello from Alexa!");
  }

  private void showDatabase(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    Connection connection = null;
    try {
      connection = getConnection();

      Statement stmt = connection.createStatement();
      stmt.executeUpdate("CREATE TABLE IF NOT EXISTS ticks (tick timestamp)");
      stmt.executeUpdate("INSERT INTO ticks VALUES (now())");
      ResultSet rs = stmt.executeQuery("SELECT tick FROM ticks");

      String out = "Hello!\n";
      while (rs.next()) {
          out += "Read from DB: " + rs.getTimestamp("tick") + "\n";
      }

      resp.getWriter().print(out);
    } catch (Exception e) {
      resp.getWriter().print("There was an error: " + e.getMessage());
    } finally {
      if (connection != null) try{connection.close();} catch(SQLException e){}
    }
  }

  private Connection getConnection() throws URISyntaxException, SQLException {
    URI dbUri = new URI(System.getenv("DATABASE_URL"));

    String username = dbUri.getUserInfo().split(":")[0];
    String password = dbUri.getUserInfo().split(":")[1];
    int port = dbUri.getPort();

    String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ":" + port + dbUri.getPath();

    return DriverManager.getConnection(dbUrl, username, password);
  }

  public static void main(String[] args) throws Exception {

    Server server = new Server(Integer.valueOf(System.getenv("PORT")));

    ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
    context.setContextPath("/");
    server.setHandler(context);
    context.addServlet(new ServletHolder(new HueServlet()), "/huecontrol");
    context.addServlet(new ServletHolder(new EventServlet()), "/notify");
    context.addServlet(new ServletHolder(createServlet(new HueSpeechlet(false))), "/hue");
    context.addServlet(new ServletHolder(createServlet(new HueSpeechlet(true))), "/huebedroom");
    context.addServlet(new ServletHolder(createServlet(new UberSpeechlet())), "/uber");
    context.addServlet(new ServletHolder(createServlet(new ProjectorSpeechlet())), "/projector");
    server.start();
    server.join();
  }

  private static SpeechletServlet createServlet(final Speechlet speechlet) {
    SpeechletServlet servlet = new SpeechletServlet();
    servlet.setSpeechlet(speechlet);
    return servlet;
  }
}
