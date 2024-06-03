package services;

import factories.ChatSessionServiceFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.websocket.Session;
import models.User;
import org.json.JSONObject;

public class ChatWebsocketService {

  private static final ChatSessionService chatSessionHandler = ChatSessionServiceFactory.getChatSessionService();

  public static void handleMessageByType(
    String message,
    Session senderSession
  ) {
    JSONObject messageAsJson = new JSONObject(message);
    String messageType = messageAsJson.getString("type");

    switch (messageType) {
      case "New message sent":
        sendMessageToSessionsInChat(messageAsJson, senderSession);
        break;
      case "New session in the chat":
        addSessionToChatSessionList(messageAsJson, senderSession);
        break;
    }
  }

  private static List<Session> addSessionToChatSessionList(
    JSONObject messageAsJson,
    Session messageSenderSession
  ) {
    String chatId = messageAsJson.getString("chatId");

    Map<String, List<Session>> sessionsByChatId = chatSessionHandler.getSessionsByChatId();
    List<Session> sessionsInChat = sessionsByChatId.get(chatId);

    if (sessionsInChat == null) {
      sessionsInChat = new ArrayList<>(Arrays.asList(messageSenderSession));
      sessionsByChatId.put(chatId, sessionsInChat);
    } else {
      boolean isSessionInList = sessionsInChat
        .stream()
        .anyMatch(existentSession -> existentSession == messageSenderSession);

      if (!isSessionInList) {
        sessionsInChat.add(messageSenderSession);
      }
    }

    return sessionsInChat;
  }

  private static void sendMessageToSessionsInChat(
    JSONObject receivedMessageAsJson,
    Session senderSession
  ) {
    String token = receivedMessageAsJson.getString("token");
    int loggedUserId = Integer.parseInt(
      UserService.validateTokenAndGetSubject(token)
    );
    User loggedUser = UserService.getUserById(loggedUserId);

    List<Session> chatSessionsList = addSessionToChatSessionList(
      receivedMessageAsJson,
      senderSession
    );

    String message = receivedMessageAsJson.getString("message");
    JSONObject messageSentToSessions = new JSONObject();

    messageSentToSessions.put("content", message);
    messageSentToSessions.put("username", loggedUser.getUsername());
    messageSentToSessions.put("isFromLoggedUser", false);

    chatSessionsList
      .stream()
      .forEach(session -> {
        try {
          if (session != senderSession) {
            session.getBasicRemote().sendText(messageSentToSessions.toString());
          }
        } catch (IOException e) {
          System.out.println("Error sending message");
        }
      });
  }
}
