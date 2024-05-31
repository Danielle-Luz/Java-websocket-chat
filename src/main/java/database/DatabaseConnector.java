package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnector {

  private static final String DATABASE_URL = "jdbc:sqlite:easychat.db";

  public static void createTables() {
    try (Connection connection = DriverManager.getConnection(DATABASE_URL)) {
      Statement statement = connection.createStatement();

      statement.setQueryTimeout(30);

      statement.executeUpdate("drop table if exists user");
      statement.executeUpdate(
        "CREATE TABLE user (id INTEGER PRIMARY KEY AUTOINCREMENT, username VARCHAR NOT NULL, password VARCHAR NOT NULL);"
      );

      statement.executeUpdate("drop table if exists chat");
      statement.executeUpdate(
        "CREATE TABLE chat (id INTEGER PRIMARY KEY AUTOINCREMENT, creator_id INTEGER NOT NULL, FOREIGN KEY(creator_id) REFERENCES user(id));"
      );

      statement.executeUpdate("drop table if exists chat_members");
      statement.executeUpdate(
        "CREATE TABLE chat_members (id INTEGER PRIMARY KEY AUTOINCREMENT, member_id INTEGER NOT NULL, chat_id INTEGER NOT NULL, FOREIGN KEY(member_id) REFERENCES user(id), FOREIGN KEY(chat_id) REFERENCES chat(id));"
      );

      statement.executeUpdate("drop table if exists message");
      statement.executeUpdate(
        "CREATE TABLE message (id INTEGER PRIMARY KEY AUTOINCREMENT, sender_id INTEGER NOT NULL, chat_id INTEGER NOT NULL, FOREIGN KEY(sender_id) REFERENCES user(id), FOREIGN KEY(chat_id) REFERENCES chat(id));"
      );
    } catch (SQLException e) {
      e.printStackTrace(System.err);
    }
  }

  public static ResultSet executeQuery(String sql) {
    try (Connection connection = DriverManager.getConnection(DATABASE_URL)) {
      Statement statement = connection.createStatement();
      ResultSet queryResults = statement.executeQuery(sql);

      return queryResults;
    } catch (Exception e) {
      e.printStackTrace(System.err);
    }

    return null;
  }

  public static boolean executeDml(String sql) {
    try (Connection connection = DriverManager.getConnection(DATABASE_URL)) {
      Statement statement = connection.createStatement();
      statement.execute(sql);
      return true;
    } catch (Exception e) {
      e.printStackTrace(System.err);
      return false;
    }
  }
}