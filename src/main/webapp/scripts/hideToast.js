(function hideToast() {
  setTimeout(() => {
    const toast = document.querySelector(".toast");

    if(toast == null) return;
    
    toast.classList.add("hide-toast");
  }, 3000);
})();
