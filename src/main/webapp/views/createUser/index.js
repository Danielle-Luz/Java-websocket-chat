(function showToastOnSubmitForm() {
  const form = document.querySelector(".form");
  const registerButton = document.querySelector(".register-button");

  form.addEventListener("submit", (event) => {
    const toast = document.querySelector(".toast");

    registerButton.setAttribute("disabled", true);

    if(toast.classList.contains("hidden-toast")) {
      toast.classList.remove("hidden-toast");
      event.preventDefault();
    }

    setTimeout(() => form.submit(), 3000);
  })
})();