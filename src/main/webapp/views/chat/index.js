const chatWebsocket = new WebSocket("ws://localhost:8080/chat");
chatWebsocket.onmessage = receiveMessage;
chatWebsocket.onopen = () => sendChatId("New session in the chat");

async function sendChatId(type) {
  const chatId = document.getElementById("chat-id").innerText;
  const messageContent = { chatId, type };
  chatWebsocket.send(JSON.stringify(messageContent));
}

async function receiveMessage(event) {
  const receivedMessage = event.data;
  appendMessage(receivedMessage, (isCurrentUserSending = false));
}

async function sendMessage() {
  const sender = "";
  const messageTextarea = document.querySelector(".message-input");
  const chatId = document.getElementById("chat-id").innerText;
  const messageText = messageTextarea.value;

  const messageContent = {
    chatId,
    sender,
    type: "New message sent",
    message: messageText,
  };

  appendMessage(messageText, (isCurrentUserSending = true));

  chatWebsocket.send(JSON.stringify(messageContent));

  messageTextarea.value = "";
}

function sendMessageOnPressingEnter() {
  const messageTextarea = document.querySelector(".message-input");

  messageTextarea.addEventListener("input", (event) => {
    if (event.inputType == "insertLineBreak") {
      sendMessage();
    }
  });
};

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

sendMessageOnPressingEnter();