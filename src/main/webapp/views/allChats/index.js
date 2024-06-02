(function showModal() {
  const openModalButton = document.getElementById("new-chat-button");

  openModalButton.addEventListener("click", () => {
    const modalContainer = document.querySelector(".modal-external-container");
    modalContainer.classList.remove("hide-modal");
  });
})();

(function closeModal() {
  const closeModalButton = document.getElementById("close-modal-button");

  closeModalButton.addEventListener("click", () => {
    const modalContainer = document.querySelector(".modal-external-container");
    modalContainer.classList.add("hide-modal");
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
    });
  });
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

  appendMessages(allChatMessages);
};

async function getAllSelectedChatMessages(chatId) {
  const getAllMessagesEndpoint = "/message?chatId=" + chatId;

  const response = await fetch(getAllMessagesEndpoint, { method: "GET" });
  const allChatMessages = await response.json();

  return allChatMessages;
}

function appendMessages(allChatMessages) {
  const messagesSection = document.querySelector(".current-chat-content");
  messagesSection.innerHTML = "";

  allChatMessages.forEach((message) => {
    const messageContainer = document.createElement("article");
    const messageText = document.createElement("p");

    messageContainer.className = "new-chat-message-container";
    messageText.className = "new-chat-message-text";

    if (message.isFromLoggedUser == false) {
      messageContainer.classList.add("another-user-message");
    }

    messageText.innerText = message.content;

    messageContainer.appendChild(messageText);

    messagesSection.appendChild(messageContainer);
  });
}

updateChatIdInputValue();
showSelectedChatMessages();