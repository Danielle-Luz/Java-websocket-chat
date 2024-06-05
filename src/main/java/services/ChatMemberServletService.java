package services;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.DatabaseConnector;

public class ChatMemberServletService {

  public static void addLoggedUserAsChatMember(
    HttpServletRequest request,
    HttpServletResponse response
  ) {
    try {
      String token = (String) request.getSession().getAttribute("token");
      String chatToJoinId = request.getParameter("chatId");
      int loggedUserId = Integer.parseInt(
        UserService.validateTokenAndGetSubject(token)
      );

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
    } catch (IOException e) {
      System.out.println(e.getMessage());
      e.printStackTrace(System.err);
    }
  }
}
