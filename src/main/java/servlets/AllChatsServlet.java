package servlets;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Chat;
import utils.RedirectUtils;

@WebServlet("/allChats")
public class AllChatsServlet extends HttpServlet {

  public void service(HttpServletRequest request, HttpServletResponse response)
    throws ServletException {
    ServletContext context = request.getServletContext();
    String allChatsPagePath = "/views/allChats/index.jsp";

    List<Chat> userChatList = new ArrayList<>();
    request.setAttribute("userChatList", userChatList);

    RedirectUtils.redirectToServlet(
      request,
      response,
      context,
      allChatsPagePath
    );
  }
}
