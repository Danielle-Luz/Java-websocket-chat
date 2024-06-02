package services;

import database.DatabaseConnector;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;

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

  public static List<JSONObject> getAllChatMessages(
    HttpServletRequest request
  ) {
    String chatId = request.getParameter("chatId");

    String token = (String) request.getSession().getAttribute("token");
    int loggedUserId = Integer.parseInt(
      UserService.validateTokenAndGetSubject(token)
    );

    String getAllMessagesQuery = String.format(
      "SELECT message.content, message.sender_id, user.username FROM message INNER JOIN user ON message.sender_id = user.id WHERE message.chat_id = '%s'",
      chatId
    );

    List<Map<String, Object>> chatMessages = DatabaseConnector.executeQuery(
      getAllMessagesQuery
    );

    List<JSONObject> chatMessagesAsJson = new ArrayList<>();
    chatMessages
      .stream()
      .forEach(chatMessage -> {
        chatMessage.put(
          "isFromLoggedUser",
          ((Integer) chatMessage.get("sender_id")) == loggedUserId
        );
        chatMessagesAsJson.add(new JSONObject(chatMessage));
      });

    return chatMessagesAsJson;
  }
}
