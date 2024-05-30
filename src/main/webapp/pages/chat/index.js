const chatWebsocket = new WebSocket("ws://localhost:8080/chat");
chatWebsocket.onmessage = receiveMessage;
chatWebsocket.onopen = () => sendChatId("New session in the chat");

function sendChatId(type) {
  const chatId = document.getElementById("chat-id").innerText;
  const messageContent = { chatId, type };
  chatWebsocket.send(JSON.stringify(messageContent));
}

function receiveMessage(event) {
  const receivedMessage = event.data;
  appendMessage(receivedMessage, (isCurrentUserSending = false));
}

function sendMessage() {
  const sender = "";
  const messageInput = document.querySelector(".message-input");
  const chatId = document.getElementById("chat-id").innerText;
  const messageText = messageInput.value;

  const messageContent = {
    chatId,
    sender,
    type: "New message sent",
    message: messageText,
  };

  appendMessage(messageText, (isCurrentUserSending = true));

  chatWebsocket.send(JSON.stringify(messageContent));

  messageInput.value = "";
}

function sendMessageOnPressingEnter() {
  if (event.key == "Enter") {
    sendMessage();
  }
}

function appendMessage(message, isCurrentUserSending) {
  const messageSection = document.querySelector(".messages-section");
  const messageContainer = document.createElement("article");
  const messageText = document.createElement("p");

  const senderDependentStyle = isCurrentUserSending
    ? "current-user-message"
    : "other-user-message";

  messageContainer.className = `message ${senderDependentStyle}`;

  messageText.innerText = message;

  messageContainer.appendChild(messageText);
  messageSection.appendChild(messageContainer);
}
