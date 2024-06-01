package servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import services.UserService;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

  public void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, java.io.IOException {
    String username = request.getParameter("username");
    String password = request.getParameter("password");

    JSONObject token = UserService.login(username, password);

    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");

    if (token == null) {
      response.sendError(401);
      response
        .getWriter()
        .println("{\"message\": \"Invalid login credentials\"}");

      return;
    }

    response.setStatus(200);
    response.getWriter().println(token.toString());
    response.sendRedirect("/views/allChats");
  }
}
