<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="services.ChatService" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
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
    <link rel="stylesheet" href="./../../styles/toasts.css" type="text/css" />
    <script src="./../../views/allChats/index.js" type="module" defer></script>
    <script
      src="./../../scripts/autoResizeTextarea.js"
      type="text/javascript"
      defer
    ></script>
    <script src="./../../scripts/hideToast.js" type="module" defer></script>
  </head>
  <body class="content-container">
    <%
     String addMemberStatusCode = (String) request.getSession().getAttribute("addMemberStatusCode");
      if(addMemberStatusCode != null) {
        String toastMessage = addMemberStatusCode.equals("404") ? 
          "No chat with the provided id was found" : 
          "You are already a member of the chat with the provided id";
      
        out.println("<article class=\"toast\"><img class=\"toast-icon\" src=\"https://img.icons8.com/material-outlined/24/FA5252/cancel--v1.png\" alt=\"Error icon\"/><h2 class=\"toast-title\">"+ toastMessage +"</h2></article>");

        request.getSession().removeAttribute("addMemberStatusCode");
      }
    %>
    <div id="new-chat-modal" class="modal-external-container hide-modal">
      <div class="modal-container">
        <button class="icon-button" data-close-modal-button>
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
            autocomplete="off"
            value=""
          />
          <button class="primary-button" disabled="true" submit-modal-button>Create chat</button>
        </form>
      </div>
    </div>
    <div id="join-chat-modal" class="modal-external-container hide-modal">
      <div class="modal-container">
        <button class="icon-button" data-close-modal-button>
          <img
            class="icon-button-image"
            src="https://img.icons8.com/ios/50/fefefe/cancel.png"
            alt="cancel"
          />
        </button>
        <form class="modal" action="/chatMember" method="POST">
          <h2 class="modal-title">Join existent chat</h2>
          <input
            id="chatId"
            name="chatId"
            class="input-2"
            type="text"
            placeholder="Insert the chat id you want to join..."
            autocomplete="off"
            value=""
          />
          <button class="primary-button" disabled="true" submit-modal-button>Join chat</button>
        </form>
      </div>
    </div>
    <aside class="all-chats-wrapper">
      <button id="new-chat-button" class="secondary-button" data-modal-button data-modal-id="new-chat-modal">
        <img
          class="secondary-button-icon"
          src="https://img.icons8.com/ios-filled/50/004efc/add--v1.png"
          alt="add icon"
        />
        <span>New chat</span>
      </button>
      <button id="join-chat-button" class="secondary-button" data-modal-button data-modal-id="join-chat-modal">
        <img class="secondary-button-icon" src="https://img.icons8.com/material-rounded/300/004efc/enter-2.png" alt="Join chat arrow icon"/>
        <span>Join existent chat</span>
      </button>
      <c:set var="userRelatedChats" value='<%= ChatService.getAllRelatedChats((String) request.getSession().getAttribute("token")) %>' scope="session" />
      <c:choose>
        <c:when test="${userRelatedChats.isEmpty()}">
          <article class="empty-message-container">
            <img class="empty-message-icon" src="https://img.icons8.com/ios-filled/50/add6f7/mailbox-opened-flag-down.png" alt="mailbox-opened-flag-down"/>
            <h2 class="empty-message-title">You didn't join any chats</h2>
            <p  class="empty-message-text">Create a new chat or send a message in a existent one</p>
          </article>
        </c:when>
        <c:otherwise>
          <section class="all-chats-container">
            <h2 class="all-chats-title">All chats</h2>
            <ul class="all-chats-list">
              <c:forEach items="${userRelatedChats}" var="chat" varStatus="item">
                <c:choose>
                  <c:when test="${item.index == 0}">
                    <li class="all-chats-list-item selected-chat" data-chat-id="${chat.id}">
                      <h3 class="chat-name">${chat.name}</h3>
                    </li>
                  </c:when>
                  <c:otherwise>
                    <li class="all-chats-list-item" data-chat-id="${chat.id}">
                      <h3 class="chat-name">${chat.name}</h3>
                    </li>
                  </c:otherwise>
                </c:choose>
              </c:forEach>
            </ul>
          </section>
        </c:otherwise>
      </c:choose>
    </aside>
    <main class="current-chat-container">
      <section class="current-chat-content">
      </section>
      <section class="message-external-wrapper">
        <span id="token" hidden="true"><%= request.getSession().getAttribute("token") %></span>
        <form id="message-form" class="input-1-container" action="/message" method="POST">
          <textarea id="message-input" name="content" disabled="true" class="input-1" placeholder="Type your message..."  autocomplete="off"></textarea>
          <input id="chat-id-input" name="chatId" type="hidden" autocomplete="off" />
          <button id="send-message-button" disabled="true" class="primary-button">
            <span class="primary-button-text">Send</span>
            <img
              class="primary-button-icon"
              src="https://img.icons8.com/material-rounded/24/fefefe/sent.png"
              alt="Send message icon"
            />
          </button>
        </form>
      </section>
    </main>
    <button class="share-chat-button">
      <img 
        class="primary-button-icon share-icon-button"
        src="https://img.icons8.com/ios-glyphs/30/004efc/share--v1.png" 
        alt="share--v1"
      />
    </button>
  </body>
</html>
