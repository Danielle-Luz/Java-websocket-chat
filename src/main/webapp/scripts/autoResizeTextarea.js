document.addEventListener("DOMContentLoaded", () => {
  const textarea = document.querySelector("textarea");

  function autoResize() {
      this.style.height = 'auto';
      this.style.height = (this.scrollHeight) + 'px';
  }

  textarea.addEventListener('input', autoResize);
  
  autoResize.call(textarea);
});