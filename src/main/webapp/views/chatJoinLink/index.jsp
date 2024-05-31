<!DOCTYPE html>
<html lang="pt-BR">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <meta http-equiv="X-UA-Compatible" content="ie=edge" />
    <title>Easy Chat | Share your chat link</title>

    <link rel="stylesheet" href="./../../styles/globalStyles.css" type="text/css" />
    <link rel="stylesheet" href="./../../styles/buttons.css" type="text/css" />
    <link rel="stylesheet" href="./views/chatJoinLink/index.css" type="text/css" />

    <script src="./views/chatJoinLink/index.js" type="text/javascript" defer></script>
  </head>

  <body class="outer-container">
    <main class="center-container">
      <h2>Chat generated with success!</h2>
      <p class="share-link-text">Access or copy and share your chat link</p>
      <span id="chat-id" hidden="true">${chatId}</span>
      <a class="primary-button chat-link" href="">Join chat</a>
      <button class="primary-button" id="copy-link">Copy chat link</button>
    </main>
  </body>
</html>