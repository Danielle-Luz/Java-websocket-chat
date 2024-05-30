<!DOCTYPE html>
<html lang="pt-BR">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <meta http-equiv="X-UA-Compatible" content="ie=edge" />
    <title>Easy chat</title>
    <link rel="stylesheet" href="./index.css" type="text/css" />
    <link rel="stylesheet" href="./../../styles/globalStyles.css" type="text/css" />
    <script src="./index.js" type="text/javascript" defer></script>
    <script src="./../../scripts/autoResizeTextarea.js" type="text/javascript" defer></script>
  </head>

  <body class="outer-container">
    <main class="main-container">
      <section class="messages-section">
      </section>
      <div class="input-external-wrapper">
        <section class="input-container">
          <%
            String chatId = request.getParameter("chatId");
            out.println("<span id='chat-id' hidden='true'>" + chatId + "</span>");
          %>
          <textarea rows="1" class="message-input" onkeydown="sendMessageOnPressingEnter(this)"></textarea>
          <button class="send-message-button" onclick="sendMessage()">
            <img 
              width="24"
              height="24" 
              src="https://img.icons8.com/material-rounded/24/FFFFFF/filled-sent.png" alt="Paper airplane icon indicating that a message will be sent"
            />
          </button>
        </section>
      </div>
    </main>
  </body>
</html>