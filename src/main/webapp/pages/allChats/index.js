(function showModal() {
  const openModalButton = document.querySelector(".new-chat-button");

  openModalButton.addEventListener("click", () => {
    const modalContainer = document.querySelector(".modal-external-container");
    modalContainer.classList.remove("hide-modal");
  });
})();

(function closeModal() {
  const closeModalButton = document.querySelector(".close-modal-button");

  closeModalButton.addEventListener("click", () => {
    const modalContainer = document.querySelector(".modal-external-container");
    console.log("clicou");
    modalContainer.classList.add("hide-modal");
  });
})();
