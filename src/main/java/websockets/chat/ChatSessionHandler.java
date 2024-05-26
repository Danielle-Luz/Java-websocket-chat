package websockets.chat;

import java.util.ArrayList;
import java.util.List;

import javax.websocket.Session;

public class ChatSessionHandler {

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
}
