package servlets.generateChat;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GenerateChatServletService {

  public static void redirectToNewChat(
    HttpServletRequest request,
    HttpServletResponse response,
    ServletContext context
  ) {
    try {
      RequestDispatcher redirector = context.getRequestDispatcher(
        "/pages/chatJoinLink/index.jsp"
      );

      String randomChatId = UUID.randomUUID().toString();
      request.setAttribute("chatId", randomChatId);

      redirector.forward(request, response);
    } catch (ServletException | IOException e) {
      System.out.println("An error occurred");
    }
  }
}
