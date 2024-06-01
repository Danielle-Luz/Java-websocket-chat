package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseConnector {

  private static final String DATABASE_URL = "jdbc:sqlite:easychat.db";

  public static void createTables() {
    try (Connection connection = DriverManager.getConnection(DATABASE_URL)) {
      Statement statement = connection.createStatement();

      statement.setQueryTimeout(30);

      statement.executeUpdate("drop table if exists user");
      statement.executeUpdate(
        "CREATE TABLE user (id INTEGER PRIMARY KEY AUTOINCREMENT, username VARCHAR NOT NULL UNIQUE, password VARCHAR NOT NULL);"
      );

      statement.executeUpdate("drop table if exists chat");
      statement.executeUpdate(
        "CREATE TABLE chat (id VARCHAR PRIMARY KEY, name VARCHAR NOT NULL, creator_id INTEGER NOT NULL, FOREIGN KEY(creator_id) REFERENCES user(id));"
      );

      statement.executeUpdate("drop table if exists chat_members");
      statement.executeUpdate(
        "CREATE TABLE chat_members (id INTEGER PRIMARY KEY AUTOINCREMENT, member_id INTEGER NOT NULL, chat_id INTEGER NOT NULL, FOREIGN KEY(member_id) REFERENCES user(id), FOREIGN KEY(chat_id) REFERENCES chat(id));"
      );

      statement.executeUpdate("drop table if exists message");
      statement.executeUpdate(
        "CREATE TABLE message (id INTEGER PRIMARY KEY AUTOINCREMENT, content TEXT NOT NULL, sender_id INTEGER NOT NULL, chat_id VARCHAR NOT NULL, FOREIGN KEY(sender_id) REFERENCES user(id), FOREIGN KEY(chat_id) REFERENCES chat(id));"
      );
    } catch (SQLException e) {
      e.printStackTrace(System.err);
    }
  }

  public static List<Map<String, Object>> executeQuery(String sql) {
    try {
      Connection connection = DriverManager.getConnection(DATABASE_URL);
      Statement statement = connection.createStatement();
      ResultSet queryResults = statement.executeQuery(sql);

      List<Map<String, Object>> clonnedResultSet = cloneResultSet(queryResults);

      connection.close();

      return clonnedResultSet;
    } catch (Exception e) {
      e.printStackTrace(System.err);
    }

    return null;
  }

  private static List<Map<String, Object>> cloneResultSet(ResultSet rs)
    throws SQLException {
    List<Map<String, Object>> rows = new ArrayList<>();
    ResultSetMetaData metaData = rs.getMetaData();
    int columnCount = metaData.getColumnCount();

    while (rs.next()) {
      Map<String, Object> row = new HashMap<>();
      for (int i = 1; i <= columnCount; i++) {
        row.put(metaData.getColumnName(i), rs.getObject(i));
      }
      rows.add(row);
    }

    return rows;
  }

  public static Object executeDml(String sql) throws SQLException {
    Connection connection = DriverManager.getConnection(DATABASE_URL);
    PreparedStatement statement = connection.prepareStatement(
      sql,
      PreparedStatement.RETURN_GENERATED_KEYS
    );
    statement.executeUpdate();

    ResultSet dmlResult = statement.getGeneratedKeys();
    dmlResult.next();

    Object affectedRowId = dmlResult.getObject(1);

    connection.close();
    dmlResult.close();

    return affectedRowId;
  }
}
