import { hideToast } from "../../scripts/hideToast.js";

const chatWebsocket = new WebSocket("ws://localhost:8080/chat");
chatWebsocket.onmessage = receiveMessage;
chatWebsocket.onopen = () => sendChatId("New session in the chat");

async function sendChatId(type) {
  const chatId = document
    .querySelector(".selected-chat")
    .getAttribute("data-chat-id");
  const token = document.getElementById("token").innerText;
  const messageContent = { chatId, token, type };
  chatWebsocket.send(JSON.stringify(messageContent));
}

async function receiveMessage(event) {
  const receivedMessage = event.data;
  const messagesSection = document.querySelector(".current-chat-content");

  appendMessage(receivedMessage, messagesSection);
}

async function sendMessage() {
  const messageTextarea = document.getElementById("message-input");
  const chatId = document
    .getElementById(".selected-chat")
    .getAttribute("data-chat-id");
  const messageText = messageTextarea.value;
  const token = document.getElementById("token").innerText;

  const messageContent = {
    chatId,
    type: "New message sent",
    message: messageText,
    token,
  };

  chatWebsocket.send(JSON.stringify(messageContent));
}

(function sendMessageToAllSections() {
  const messageForm = document.getElementById("message-form");
  messageForm.addEventListener("submit", (event) => {
    sendMessage();
  });
});

(function onClickShowModalButton() {
  const openModalButtons = document.querySelectorAll("[data-modal-button]");

  openModalButtons.forEach((button) => {
    button.addEventListener("click", () => {
      const modalContainer = document.getElementById(
        button.getAttribute("data-modal-id")
      );

      modalContainer.classList.remove("hide-modal");

      const modalInput = modalContainer.querySelector("input");
      modalInput.value = "";

      const modalButton = modalContainer.querySelector("[submit-modal-button]");
      modalButton.disabled = true;
    });
  });
})();

(function closeModal() {
  const closeModalButtons = document.querySelectorAll(
    "[data-close-modal-button]"
  );

  closeModalButtons.forEach((button) => {
    button.addEventListener("click", () => {
      const modalContainer = button.parentNode.parentNode;
      modalContainer.classList.add("hide-modal");
    });
  });
})();

(function onSelectChat() {
  const allChats = Array.from(
    document.querySelectorAll(".all-chats-list-item")
  );

  allChats.forEach((selectedChat) => {
    selectedChat.addEventListener("click", () => {
      addSelectedChatStyle(allChats, selectedChat);
      showSelectedChatMessages();
      updateChatIdInputValue();

      sessionStorage.setItem(
        "lastChatSelectedId",
        selectedChat.getAttribute("data-chat-id")
      );
    });
  });
})();

(function enableInputs() {
  const modals = document.querySelectorAll(".modal-external-container");

  modals.forEach((modal) => {
    const modalInput = modal.querySelector("input");
    const modalButton = modal.querySelector("[submit-modal-button]");

    modalInput.addEventListener("input", () => {
      modalButton.disabled = modalInput.value == "";
    });
  });
})();

(function selectLastSelectedChat() {
  const lastSelectedChatId = sessionStorage.getItem("lastChatSelectedId");
  const lastSelectedChat = document.querySelector(
    `[data-chat-id="${lastSelectedChatId}"]`
  );

  if (lastSelectedChat == null) return;

  lastSelectedChat.click();
})();

function addSelectedChatStyle(allChats, selectedChat) {
  const previouslySelectedChat = allChats.find((chat) =>
    chat.classList.contains("selected-chat")
  );
  previouslySelectedChat.classList.remove("selected-chat");
  selectedChat.classList.add("selected-chat");
}

function updateChatIdInputValue() {
  const selectedChat = document.querySelector(".selected-chat");

  if (selectedChat == null) return;

  const chatIdInput = document.getElementById("chat-id-input");

  chatIdInput.value = selectedChat.getAttribute("data-chat-id");
}

(function enableMessageInputWhenChatExists() {
  const selectedChat = document.querySelector(".selected-chat");

  if (selectedChat != null) {
    const messageInput = document.getElementById("message-input");
    messageInput.disabled = false;

    const sendMessageButton = document.getElementById("send-message-button");
    sendMessageButton.disabled = false;
  }
})();

async function showSelectedChatMessages() {
  const selectedChat = document.querySelector(".selected-chat");

  if (selectedChat == null) return;

  const selectedChatId = selectedChat.getAttribute("data-chat-id");

  const allChatMessages = await getAllSelectedChatMessages(selectedChatId);

  appendAllChatMessages(allChatMessages);
}

async function getAllSelectedChatMessages(chatId) {
  const getAllMessagesEndpoint = "/message?chatId=" + chatId;

  const response = await fetch(getAllMessagesEndpoint, { method: "GET" });
  const allChatMessages = await response.json();

  return allChatMessages;
}

function appendAllChatMessages(allChatMessages) {
  const messagesSection = document.querySelector(".current-chat-content");
  messagesSection.innerHTML = "";

  allChatMessages.forEach((message) => appendMessage(message, messagesSection));
}

function appendMessage(message, messagesSection) {
  const messageContainer = document.createElement("article");
  const messageSenderName = document.createElement("h3");
  const messageText = document.createElement("p");

  messageContainer.className = "new-chat-message-container";
  messageSenderName.className = "new-chat-message-sender";
  messageText.className = "new-chat-message-text";

  if (message.isFromLoggedUser == false) {
    messageContainer.classList.add("another-user-message");
  }

  messageText.innerText = message.content;
  messageSenderName.innerText = message.username;

  messageContainer.appendChild(messageSenderName);
  messageContainer.appendChild(messageText);

  messagesSection.appendChild(messageContainer);
}

(function sendMessageOnPressingEnter() {
  const messageForm = document.getElementById("message-form");
  const messageTextarea = document.getElementById("message-input");

  messageTextarea.addEventListener("input", (event) => {
    if (event.inputType == "insertLineBreak") {
      messageForm.submit();
    }
  });
})();

(function copyChatIdOnShareButtonClick() {
  const shareButton = document.querySelector(".share-chat-button");

  shareButton.addEventListener("click", async () => {
    const chatId = document.getElementById("chat-id-input").value;

    const popupArticle = document.createElement("article");
    popupArticle.className = "toast";
    popupArticle.innerHTML =
      "<img class='toast-icon' src='https://img.icons8.com/ios/50/20C997/ok--v1.png' alt='Confirm icon'/><h2 class='toast-title'>The chat id was coppied, paste it to share</h2>";

    const body = document.querySelector("body");
    body.appendChild(popupArticle);

    await navigator.clipboard.writeText(
      `Insert this chat id after clicking on the join chat button: ${chatId}`
    );

    shareButton.disabled = true;

    await hideToast(shareButton);
    popupArticle.remove();
  });
})();

updateChatIdInputValue();
showSelectedChatMessages();
