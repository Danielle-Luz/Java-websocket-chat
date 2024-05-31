package services;

import java.sql.ResultSet;
import java.sql.SQLException;

import database.DatabaseConnector;
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

      return new User(
        userId,
        username,
        encryptedPassword
      );
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
}
