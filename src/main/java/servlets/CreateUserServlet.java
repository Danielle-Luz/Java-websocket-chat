package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.User;
import services.UserService;

@WebServlet("/createUser")
public class CreateUserServlet extends HttpServlet {

  public void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
    String username = request.getParameter("username");
    String password = request.getParameter("password");

    User createdUser = UserService.createUser(username, password);

    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");

    if (createdUser == null) {
      response.sendError(500);
      response
        .getWriter()
        .write("{\"message\": \"An error ocurred and the user was not created\"}");
      return;
    }

    response.setStatus(201);
    response.getWriter().println(createdUser.getJson().toString());
    response.sendRedirect("/views/login");
  }
}
