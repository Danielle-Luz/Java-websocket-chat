package services;

import database.DatabaseConnector;
import java.sql.ResultSet;
import java.sql.SQLException;
import models.User;
import utils.EncryptUtils;

public class UserService {

  public static User createUser(User newUser) {
    try {
      String encryptedPassword = EncryptUtils.encryptValue(
        newUser.getPassword()
      );

      String insertUserQuery = String.format(
        "INSERT INTO user(username, password) VALUES ('%s', '%s')",
        newUser.getUsername(),
        encryptedPassword
      );

      ResultSet createdRow = DatabaseConnector.executeDml(insertUserQuery);

      return new User(
        createdRow.getInt("id"),
        createdRow.getString("username"),
        createdRow.getString("password")
      );
    } catch (SQLException e) {
      e.printStackTrace(System.err);
      return null;
    }
  }
}
