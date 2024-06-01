package models;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ChatMember {
  private int id;
  private int memberId;
  private String chatId;
}
