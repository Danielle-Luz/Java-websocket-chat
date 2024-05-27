package websockets.chat;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/chat")
public class ChatWebsocket {

  ChatSessionHandler chatSessionHandler = ChatSessionHandlerFactory.getChatSessionHandler();

  @OnOpen
  public void onClientConnect(Session newSession) {
    chatSessionHandler.addSession(newSession);
  }

  @OnClose
  public void onClientDesconnect(Session finishedSession) {
    chatSessionHandler.removeSession(finishedSession);
  }

  @OnMessage
  public void onReceiveMessage(String message, Session senderSession) {
    ChatWebsocketService.sendMessageToSessionsInChat(message, senderSession);
  }
}
