package utils;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RedirectUtils {

  public static void redirectToServlet(
    HttpServletRequest request,
    HttpServletResponse response,
    ServletContext context,
    String servletPath
  ) {
    try {
      RequestDispatcher redirector = context.getRequestDispatcher(servletPath);
      redirector.forward(request, response);
    } catch (ServletException | IOException e) {
      e.printStackTrace(System.err);
    }
  }
}
