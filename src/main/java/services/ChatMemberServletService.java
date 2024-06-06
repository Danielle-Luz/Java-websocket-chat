package services;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.DatabaseConnector;

public class ChatMemberServletService {

  public static void addLoggedUserAsChatMember(
    HttpServletRequest request,
    HttpServletResponse response
  ) throws  IOException {
    try {
      String token = (String) request.getSession().getAttribute("token");
      String chatToJoinId = request.getParameter("chatId");
      int loggedUserId = Integer.parseInt(
        UserService.validateTokenAndGetSubject(token)
      );

      Map<String, Object> foundChat = ChatService.getChatById(chatToJoinId);

      String insertChatMemberQuery = String.format(
        "INSERT INTO chat_member (chat_id, member_id) VALUES ('%s', %d)",
        chatToJoinId,
        loggedUserId
      );

      DatabaseConnector.executeDml(insertChatMemberQuery);
      
      response.sendRedirect("/allChats");
    } catch (SQLException e) {
      System.out.println(e.getMessage());
      e.printStackTrace(System.err);
    } catch (IndexOutOfBoundsException e) {
      request.getSession().setAttribute("addMemberStatusCode", 404);
      System.out.println("setou 404");
      response.sendRedirect("/allChats");
    } catch (IOException e) {
      System.out.println(e.getMessage());
      e.printStackTrace(System.err);
    }
  }
}
