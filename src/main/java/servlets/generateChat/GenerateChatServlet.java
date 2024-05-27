package servlets.generateChat;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/generateChat")
public class GenerateChatServlet extends HttpServlet {

  public void doGet(HttpServletRequest request, HttpServletResponse response) {
    GenerateChatServletService.redirectToNewChat(response);
  }
}
