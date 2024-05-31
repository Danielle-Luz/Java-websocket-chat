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
