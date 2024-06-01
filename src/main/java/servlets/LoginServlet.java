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
    JSONObject token = UserService.login(request, response);

    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");

    if (token == null) {
      return;
    }

    response.setStatus(200);
    response.getWriter().println(token.toString());
    response.sendRedirect("/views/allChats");
  }
}
