package servlets;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import utils.RedirectUtils;

@WebServlet("/allChats")
public class AllChatsServlet extends HttpServlet {

  public void service(HttpServletRequest request, HttpServletResponse response)
    throws ServletException {
    ServletContext context = request.getServletContext();
    String allChatsPagePath = "/views/allChats/index.jsp";
    RedirectUtils.redirectToServlet(
      request,
      response,
      context,
      allChatsPagePath
    );
  }
}
