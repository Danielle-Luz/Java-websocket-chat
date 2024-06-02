package services;

import database.DatabaseConnector;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MessageService {

  public static void sendMessage(
    HttpServletRequest request,
    HttpServletResponse response
  ) {
    try {
      String token = (String) request.getSession().getAttribute("token");
      int messageSenderId = Integer.parseInt(
        UserService.validateTokenAndGetSubject(token)
      );

      String messageContent = request.getParameter("content");
      String chatId = request.getParameter("chatId");

      String insertMessageQuery = String.format(
        "INSERT INTO message(chat_id, content, sender_id) VALUES ('%s', '%s', %d)",
        chatId,
        messageContent,
        messageSenderId
      );

      DatabaseConnector.executeDml(insertMessageQuery);
      response.sendRedirect("/allChats");
    } catch (SQLException | IOException e) {
      e.printStackTrace(System.err);
      request.getSession().setAttribute("messageSendingStatusCode", 500);
    }
  }
}
