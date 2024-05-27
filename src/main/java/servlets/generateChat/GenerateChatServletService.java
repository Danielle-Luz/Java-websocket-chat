package servlets.generateChat;

import java.io.IOException;
import java.util.UUID;
import javax.servlet.http.HttpServletResponse;

public class GenerateChatServletService {

  public static void redirectToNewChat(HttpServletResponse response) {
    try {
      String randomChatId = UUID.randomUUID().toString();
      response.sendRedirect("/pages/chat?chatId=" + randomChatId);
    } catch (IOException e) {
      System.out.println("An error occurred");
    }
  }
}
