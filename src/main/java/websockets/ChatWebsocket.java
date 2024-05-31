package websockets;

import java.io.IOException;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import factories.ChatSessionServiceFactory;
import services.ChatSessionService;
import services.ChatWebsocketService;

@ServerEndpoint("/chat")
public class ChatWebsocket {

  ChatSessionService chatSessionHandler = ChatSessionServiceFactory.getChatSessionService();

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
