package websockets.chat;

public class ChatSessionHandlerFactory {

  private static ChatSessionHandler chatSessionHandler;

  public static ChatSessionHandler getChatSessionHandler() {
    if (chatSessionHandler == null) {
      chatSessionHandler = new ChatSessionHandler();
    }

    return chatSessionHandler;
  }
}
