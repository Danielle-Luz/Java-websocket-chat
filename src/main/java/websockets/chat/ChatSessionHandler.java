package websockets.chat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.websocket.Session;

public class ChatSessionHandler {

  private final Map<String, List<Session>> sessionsByChatId = new HashMap<>();
  private final List<Session> openSessions = new ArrayList<>();

  public void addSession(Session newSession) {
    this.openSessions.add(newSession);
  }

  public void removeSession(Session closedSession) {
    this.openSessions.remove(closedSession);
  }

  public List<Session> getOpenSessions() {
    return this.openSessions;
  }

  public Map<String, List<Session>> getSessionsByChatId() {
    return this.sessionsByChatId;
  }
}
