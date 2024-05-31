package services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.websocket.Session;

public class ChatSessionService {

  private final Map<String, List<Session>> sessionsByChatId = new HashMap<>();
  private final List<Session> openSessions = new ArrayList<>();

  public void addSession(Session newSession) {
    this.openSessions.add(newSession);
  }

  public void removeSession(Session closedSession) throws IOException {
    this.openSessions.remove(closedSession);
    this.sessionsByChatId.values()
      .stream()
      .filter(chatSessions -> chatSessions.contains(closedSession))
      .forEach(chatWithRemovedSession ->
        chatWithRemovedSession.remove(closedSession)
      );
  }

  public List<Session> getOpenSessions() {
    return this.openSessions;
  }

  public Map<String, List<Session>> getSessionsByChatId() {
    return this.sessionsByChatId;
  }
}
