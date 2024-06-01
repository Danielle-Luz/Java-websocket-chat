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
    
    User createdUser = UserService.createUser(request, response);

    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");

    if (createdUser == null) {
      return;
    }

    response.setStatus(201);
    response.getWriter().println(createdUser.getJson().toString());
    response.sendRedirect("/views/login/index.jsp?statusCode=201");
  }
}
