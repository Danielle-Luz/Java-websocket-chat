export const hideToast = (buttonToEnable) =>
  new Promise((resolve) => {
    setTimeout(() => {
      const toast = document.querySelector(".toast");

      if (toast == null) return;

      toast.classList.add("hide-toast");

      if(buttonToEnable) buttonToEnable.disabled = false;

      setTimeout(() => resolve(), 1500);
    }, 3000);
  });

hideToast();
