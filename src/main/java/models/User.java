package models;

import org.json.JSONObject;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
  private int id;
  private String username;
  private String password;

  public JSONObject getJson() {
    JSONObject userAsJson = new JSONObject();

    userAsJson.put("id", this.id);
    userAsJson.put("username", this.username);
    userAsJson.put("password", this.password);

    return userAsJson;
  }
}
