(function hideToast() {
  setTimeout(() => {
    const toast = document.querySelector(".toast");
    toast.classList.add("hide-toast");
  }, 3000);
})();
