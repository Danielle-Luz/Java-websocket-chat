package websockets.chat;

import java.io.IOException;

import javax.websocket.OnClose;
import javax.websocket.OnError;
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
  public void onClientDesconnect(Session finishedSession) throws IOException {
    chatSessionHandler.removeSession(finishedSession);
  }

  @OnMessage
  public void onReceiveMessage(String message, Session senderSession) {
    ChatWebsocketService.handleMessageByType(message, senderSession);
  }

  @OnError
  public void onError(Throwable error) {
    System.out.println(error.getMessage());
  }
}
