package factories;

import services.ChatSessionService;

public class ChatSessionServiceFactory {

  private static ChatSessionService chatSessionHandler;

  public static ChatSessionService getChatSessionService() {
    if (chatSessionHandler == null) {
      chatSessionHandler = new ChatSessionService();
    }

    return chatSessionHandler;
  }
}
