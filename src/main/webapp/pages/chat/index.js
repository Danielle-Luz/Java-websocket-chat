const chatWebsocket = new WebSocket("ws://localhost:8080/chat");
chatWebsocket.onmessage = receiveMessage;

function receiveMessage(event) {
  const messageContent = JSON.parse(event.data);
  appendMessage(messageContent, (isCurrentUserSending = false));
}

function sendMessage() {
  const sender = "";
  const messageInput = document.querySelector(".message-input");
  const messageText = messageInput.value;

  const messageContent = {
    sender,
    message: messageText,
  };

  appendMessage(messageContent, (isCurrentUserSending = true));

  chatWebsocket.send(JSON.stringify(messageContent));

  messageInput.value = "";
}

function sendMessageOnPressingEnter() {
  if (event.key == "Enter") {
    sendMessage();
  }
}

function appendMessage(messageContent, isCurrentUserSending) {
  const messageSection = document.querySelector(".messages-section");
  const messageContainer = document.createElement("article");
  const messageText = document.createElement("p");

  const senderDependentStyle = isCurrentUserSending
    ? "current-user-message"
    : "other-user-message";

  messageContainer.className = `message ${senderDependentStyle}`;

  messageText.innerText = messageContent.message;

  messageContainer.appendChild(messageText);
  messageSection.appendChild(messageContainer);
}
