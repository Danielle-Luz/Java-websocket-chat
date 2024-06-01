package servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import services.UserService;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException {
    String username = request.getParameter("username");
    String password = request.getParameter("password");

    UserService.login(username, password);
  }
}
