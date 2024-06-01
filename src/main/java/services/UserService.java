package services;

import java.security.Key;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import database.DatabaseConnector;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import models.User;
import utils.EncryptUtils;

public class UserService {

  public static User createUser(String username, String password) {
    try {
      String encryptedPassword = EncryptUtils.encryptValue(password);

      String insertUserQuery = String.format(
        "INSERT INTO user(username, password) VALUES ('%s', '%s')",
        username,
        encryptedPassword
      );

      ResultSet createdRow = DatabaseConnector.executeDml(insertUserQuery);
      int userId = createdRow.getInt(1);

      return new User(userId, username, encryptedPassword);
    } catch (SQLException e) {
      e.printStackTrace(System.err);
      return null;
    }
  }

  public static User login(String username, String password) {
    try {
      String userByUsernameQuery = String.format(
        "SELECT * FROM user WHERE username = '%s'",
        username
      );

      ResultSet foundUser = DatabaseConnector.executeQuery(userByUsernameQuery);

      boolean isProvidedPasswordValid = EncryptUtils.isValueEqualToEncrypted(
        password,
        foundUser.getString("password")
      );

      if (isProvidedPasswordValid) {
        return new User(
          foundUser.getInt("id"),
          foundUser.getString("username"),
          foundUser.getString("password")
        );
      } else {
        return null;
      }
    } catch (SQLException e) {
      e.printStackTrace(System.err);
      return null;
    }
  }

  public String generateToken(String username) {
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
