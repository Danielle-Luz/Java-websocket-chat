(function enableRegisterButtonOnFillAllInputs() {
  const areAllInputFilled = false;
  const inputs = document.querySelectorAll("input");
  const registerButton = document.querySelector(".register-button");
  let filledInputNumber = 0;

  inputs.forEach(input => {
    input.addEventListener("input", () => {
      const areAllInputsFilled = Array.from(inputs).every(input => input.value !== "");

      if(areAllInputsFilled) {
        registerButton.removeAttribute("disabled");
      } else {
        registerButton.setAttribute("disabled", true);
      }
    });
  })
})();