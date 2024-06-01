package models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Message {
  private int id;
  private String content;
  private int senderId;
  private String chatId;
}
