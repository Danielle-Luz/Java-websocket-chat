<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-BR">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <meta http-equiv="X-UA-Compatible" content="ie=edge" />
    <title>Easychat</title>
    <link rel="stylesheet" href="./../../views/allChats/index.css" type="text/css" />
    <link rel="stylesheet" href="./../../styles/global.css" type="text/css" />
    <link rel="stylesheet" href="./../../styles/buttons.css" type="text/css" />
    <link rel="stylesheet" href="./../../styles/inputs.css" type="text/css" />
    <link rel="stylesheet" href="./../../styles/modal.css" type="text/css" />
    <script src="./../../views/allChats/index.js" type="text/javascript" defer></script>
    <script
      src="./../../scripts/autoResizeTextarea.js"
      type="text/javascript"
      defer
    ></script>
  </head>
  <body class="content-container">
    <div class="modal-external-container hide-modal">
      <div class="modal-container">
        <button class="icon-button" id="close-modal-button">
          <img
            class="icon-button-image"
            src="https://img.icons8.com/ios/50/fefefe/cancel.png"
            alt="cancel"
          />
        </button>
        <form class="modal" action="/chat" method="POST">
          <h2 class="modal-title">New chat</h2>
          <input
            id="modal-input"
            id="chatname"
            name="chatName"
            class="input-2"
            type="text"
            placeholder="Type the chat name..."
          />
          <button class="primary-button">Create chat</button>
        </form>
      </div>
    </div>
    <aside class="all-chats-wrapper">
      <button id="new-chat-button" class="secondary-button">
        <img
          class="secondary-button-icon"
          src="https://img.icons8.com/ios-filled/50/004efc/add--v1.png"
          alt="add icon"
        />
        <span>New chat</span>
      </button>
        <% 
          if(true) {
        %>
        <article class="empty-message-container">
          <img class="empty-message-icon" src="https://img.icons8.com/ios-filled/50/add6f7/mailbox-opened-flag-down.png" alt="mailbox-opened-flag-down"/>
          <h2 class="empty-message-title">You didn't join any chats</h2>
          <p  class="empty-message-text">Create a new chat or send a message in a existent one</p>
        </article>
        <% } else { %>
        <section class="all-chats-container">
          <h2 class="all-chats-title">All chats</h2>
          <ul class="all-chats-list">
            <li class="all-chats-list-item">
              <h3 class="chat-name">Chat 1</h3>
              <p class="chat-last-message">Last message</p>
            </li>
            <li class="all-chats-list-item">
              <h3 class="chat-name">Chat 1</h3>
              <p class="chat-last-message">Last message</p>
            </li>
            <li class="all-chats-list-item">
              <h3 class="chat-name">Chat 1</h3>
              <p class="chat-last-message">Last message</p>
            </li>
            <li class="all-chats-list-item">
              <h3 class="chat-name">Chat 1</h3>
              <p class="chat-last-message">Last message</p>
            </li>
            <li class="all-chats-list-item">
              <h3 class="chat-name">Chat 1</h3>
              <p class="chat-last-message">Last message</p>
            </li>
            <li class="all-chats-list-item selected-chat">
              <h3 class="chat-name">Chat 1</h3>
              <p class="chat-last-message">Last message</p>
            </li>
            <li class="all-chats-list-item">
              <h3 class="chat-name">Chat 1</h3>
              <p class="chat-last-message">Last message</p>
            </li>
          </ul>
        </section>
        <% } %>
    </aside>
    <main class="current-chat-container">
      <section class="current-chat-content">
        <article class="new-chat-message-container">
          <p class="new-chat-message-text">
            catarro roxo branco acerola cozida marionete papagaio lupa bola
            basquete tucano lúcida uberaba malemolência escrevin errado
          </p>
        </article>
        <article class="new-chat-message-container another-user-message">
          <p class="new-chat-message-text">
            catarro roxo branco acerola cozida marionete papagaio lupa bola
            basquete tucano lúcida uberaba malemolência escrevin errado
          </p>
        </article>
      </section>
      <section class="message-external-wrapper">
        <article class="input-1-container">
          <textarea disabled="true" class="input-1" placeholder="Type your message...">
          </textarea>
          <button disabled="true" class="primary-button">
            <span class="primary-button-text">Send</span>
            <img
              class="primary-button-icon"
              src="https://img.icons8.com/material-rounded/24/fefefe/sent.png"
              alt="Send message icon"
            />
          </button>
        </article>
      </section>
    </main>
  </body>
</html>
