package servlets.chatJoinLink;

import java.util.UUID;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/chatJoinLink")
public class ChatJoinLinkServlet extends HttpServlet {
  public void doGet(HttpServletRequest request, HttpServletResponse response) {
    String randomChatId = UUID.randomUUID().toString();
  }
}
