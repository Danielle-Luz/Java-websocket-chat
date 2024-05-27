package websockets.chat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.websocket.Session;

import org.json.JSONObject;

public class ChatWebsocketService {

  private static final ChatSessionHandler chatSessionHandler = ChatSessionHandlerFactory.getChatSessionHandler();

  public static List<Session> addSessionToChatSessionList(
    String message,
    Session messageSenderSession
  ) {
    JSONObject messageAsJson = new JSONObject(message);
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

  public static void sendMessageToSessionsInChat(
    String message,
    Session senderSession
  ) {
    List<Session> chatSessionsList = addSessionToChatSessionList(
      message,
      senderSession
    );

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
