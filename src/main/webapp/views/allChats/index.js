/*(function showModal() {
  const openModalButton = document.getElementById("new-chat-button");

  openModalButton.addEventListener("click", () => {
    const modalContainer = document.querySelector(".modal-external-container");
    modalContainer.classList.remove("hide-modal");
  });
})();*/

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

  if(lastSelectedChat == null) return;

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

  appendMessages(allChatMessages);
}

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
  });
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

updateChatIdInputValue();
showSelectedChatMessages();
