package services;

import java.io.IOException;
import java.security.Key;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.DatabaseConnector;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import models.User;
import utils.EncryptUtils;

public class UserService {

  private static final Key SECRET_KEY = Keys.secretKeyFor(
    io.jsonwebtoken.SignatureAlgorithm.HS256
  );

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

      int userId = (Integer) DatabaseConnector.executeDml(insertUserQuery);

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

  public static String login(
    HttpServletRequest request,
    HttpServletResponse response
  ) throws IOException {
    String username = request.getParameter("username");
    String password = request.getParameter("password");

    String userByUsernameQuery = String.format(
      "SELECT id, password FROM user WHERE username = '%s'",
      username
    );

    Map<String, Object> foundUserMap = DatabaseConnector
      .executeQuery(userByUsernameQuery)
      .get(0);

    String foundUserId = foundUserMap.get("id").toString();
    String foundUserEncryptedPassword = (String) foundUserMap.get("password");

    boolean isProvidedPasswordValid = EncryptUtils.isValueEqualToEncrypted(
      password,
      foundUserEncryptedPassword
    );

    if (isProvidedPasswordValid) {
      return generateToken(foundUserId);
    }

    response.sendRedirect("/views/login/index.jsp?statusCode=401");

    return null;
  }

  private static String generateToken(String storedValue) {
    Date expirationDate = new Date(System.currentTimeMillis() + 3600000);

    return Jwts
      .builder()
      .subject(storedValue)
      .expiration(expirationDate)
      .signWith(SECRET_KEY)
      .compact();
  }

  public static String validateTokenAndGetSubject(String token) {
    Claims claims = Jwts
      .parser()
      .setSigningKey(SECRET_KEY)
      .build()
      .parseClaimsJws(token)
      .getBody();

    return claims.getSubject();
  }
}
