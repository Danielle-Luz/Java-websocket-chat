function redirectToChatIdGenerator() {
  const baseUrl = window.location.host;
  window.location.href = `http://${baseUrl}/generateChat`;
}