function onCopyLinkButtonClick() {
  const copyLinkButton = document.getElementById("copy-link");
  const copiedLink = document.querySelector(".chat-link").href;

  copyLinkButton.addEventListener("click", async () => {
    await navigator.clipboard.writeText(copiedLink);
    alert("The text was successfully copied");
  });
}

function setChatLink() {
  const chatId = document.getElementById("chat-id").innerText;
  const chatLinkElement = document.querySelector(".chat-link");

  const baseUrl = window.location.host;
  const chatLinkUrl = `http://${baseUrl}/pages/chat/?chatId=${chatId}`;

  chatLinkElement.href = chatLinkUrl;
}

setChatLink();
onCopyLinkButtonClick();
