package services;

import java.io.IOException;
import java.sql.SQLException;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.DatabaseConnector;

public class ChatService {

  public static void createChat(
    HttpServletRequest request,
    HttpServletResponse response
  ) {
    try {
      String chatName = request.getParameter("chatName");
      String token = (String) request.getSession().getAttribute("token");

      String randomChatId = UUID.randomUUID().toString();

      int chatCreatorId = Integer.parseInt(
        UserService.validateTokenAndGetSubject(token)
      );

      String insertChatQuery = String.format(
        "INSERT INTO chat(id, name, creator_id) VALUES ('%s', '%s', %d)",
        randomChatId,
        chatName,
        chatCreatorId
      );

      DatabaseConnector.executeDml(insertChatQuery);

      request.getSession().setAttribute("chatCreationStatusCode", null);
      response.sendRedirect("/allChats");
    } catch (SQLException | IOException e) {
      e.printStackTrace(System.err);
      request.getSession().setAttribute("chatCreationStatusCode", 500);
    }
  }
}
