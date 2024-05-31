package services;

import database.DatabaseConnector;
import java.sql.ResultSet;
import java.sql.SQLException;
import models.User;

public class UserService {

  public static User createUser(User newUser) {
    try {
      String insertSql = String.format(
        "INSERT INTO user(username, password) VALUES ('%s', '%s')",
        newUser.getUsername(),
        newUser.getPassword()
      );

      ResultSet createdRow = DatabaseConnector.executeDml(insertSql);

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
