package services;

import java.io.IOException;
import java.security.Key;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import database.DatabaseConnector;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import models.User;
import utils.EncryptUtils;

public class UserService {

  public static User createUser(
    HttpServletRequest request,
    HttpServletResponse response
  ) throws java.io.IOException {
    try {
      String username = request.getParameter("username");
      String password = request.getParameter("password");

      String encryptedPassword = EncryptUtils.encryptValue(password);

      String insertUserQuery = String.format(
        "INSERT INTO user(username, password) VALUES ('%s', '%s')",
        username,
        encryptedPassword
      );

      ResultSet createdRow = DatabaseConnector.executeDml(insertUserQuery);
      int userId = createdRow.getInt(1);

      return new User(userId, username, encryptedPassword);
    } catch (Exception e) {
      if (e.getMessage().contains("SQLITE_CONSTRAINT_UNIQUE")) {
        response.setStatus(409);
        response.getWriter().write("{\"message\": \"Username already taken\"}");
        response.sendRedirect("/views/createUser/index.jsp?statusCode=409");
      } else {
        response.setStatus(500);
        response
          .getWriter()
          .write(
            "{\"message\": \"An error ocurred and the user was not created\"}"
          );
        response.sendRedirect("/views/createUser/index.jsp?statusCode=500");
      }
      e.printStackTrace(System.err);
      return null;
    }
  }

  public static JSONObject login(
    HttpServletRequest request,
    HttpServletResponse response
  ) throws IOException {
    try {
      String username = request.getParameter("username");
      String password = request.getParameter("password");

      String userByUsernameQuery = String.format(
        "SELECT id, password FROM user WHERE username = '%s'",
        username
      );

      ResultSet foundUser = DatabaseConnector.executeQuery(userByUsernameQuery);
      String foundUserId = foundUser.getString("id");
      String foundUserEncryptedPassword = foundUser.getString("password");

      boolean isProvidedPasswordValid = EncryptUtils.isValueEqualToEncrypted(
        password,
        foundUserEncryptedPassword
      );

      if (isProvidedPasswordValid) {
        JSONObject token = new JSONObject();
        token.put("token", generateToken(foundUserId));
        return token;
      }

      response.sendRedirect("/views/login/index.jsp?statusCode=401");
    } catch (SQLException e) {
      response.sendRedirect("/views/login/index.jsp?statusCode=500");
      e.printStackTrace(System.err);
    }

    return null;
  }

  private static String generateToken(String username) {
    Key secretKey = Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS256);
    Date expirationDate = new Date(System.currentTimeMillis() + 3600000);

    return Jwts
      .builder()
      .subject(username)
      .expiration(expirationDate)
      .signWith(secretKey)
      .compact();
  }
}
