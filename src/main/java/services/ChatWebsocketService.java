package services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.websocket.Session;

import org.json.JSONObject;

import factories.ChatSessionServiceFactory;

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
    JSONObject messageAsJson,
    Session senderSession
  ) {
    List<Session> chatSessionsList = addSessionToChatSessionList(
      messageAsJson,
      senderSession
    );

    String message = messageAsJson.getString("message");

    chatSessionsList
      .stream()
      .forEach(session -> {
        try {
          if (session != senderSession) {
            session.getBasicRemote().sendText(message);
          }
        } catch (IOException e) {
          System.out.println("Error sending message");
        }
      });
  }
}
