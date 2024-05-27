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

  public static void addSessionToChat(
    String message,
    Session messageSenderSession
  ) {
    JSONObject messageAsJson = new JSONObject(message);
    String chatId = messageAsJson.getString("chatId");

    Map<String, List<Session>> sessionsByChatId = chatSessionHandler.getSessionsByChatId();
    List<Session> sessionsInChat = sessionsByChatId.get(chatId);

    if (sessionsInChat == null) {
      sessionsByChatId.put(
        chatId,
        new ArrayList<>(Arrays.asList(messageSenderSession))
      );

      return;
    }

    boolean isSessionInList = sessionsInChat
      .stream()
      .anyMatch(existentSession -> existentSession == messageSenderSession);

    if (!isSessionInList) {
      sessionsInChat.add(messageSenderSession);
    }
  }

  public static void sendMessageToAllSessions(
    String message,
    Session senderSession
  ) {
    List<Session> openSessions = chatSessionHandler.getOpenSessions();
    openSessions
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
