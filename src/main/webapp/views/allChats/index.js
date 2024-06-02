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
  const allChats = Array.from(document.querySelectorAll(".all-chats-list-item"));

  allChats.forEach(selectedChat => {
    selectedChat.addEventListener("click", () => {
      addSelectedChatStyle(allChats, selectedChat);
    });
  });
})();

function addSelectedChatStyle(allChats, selectedChat) {
  const previouslySelectedChat = allChats.find(chat => chat.classList.contains("selected-chat"));
  previouslySelectedChat.classList.remove("selected-chat");
  selectedChat.classList.add("selected-chat");
}

(function enableMessageInputWhenChatExists() {
  const selectedChat = document.querySelector(".selected-chat");

  if(selectedChat != null) {
    const messageInput = document.getElementById("message-input");
    messageInput.disabled = false;

    const sendMessageButton = document.getElementById("send-message-button");
    sendMessageButton.disabled = false;
  }
})();