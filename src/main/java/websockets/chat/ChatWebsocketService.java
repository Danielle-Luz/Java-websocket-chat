package websockets.chat;

import java.io.IOException;
import java.util.List;

import javax.websocket.Session;

public class ChatWebsocketService {

  private final static ChatSessionHandler chatSessionHandler = ChatSessionHandlerFactory.getChatSessionHandler();

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
