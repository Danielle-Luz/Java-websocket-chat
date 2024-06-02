export function enableSubmitButtonOnFillAllInputs(buttonIdentifier) {
  const inputs = document.querySelectorAll("input");
  const submitButton = document.querySelector(buttonIdentifier);

  inputs.forEach(input => {
    input.addEventListener("input", () => {
      const areAllInputsFilled = Array.from(inputs).every(input => input.value !== "");

      if(areAllInputsFilled) {
        submitButton.removeAttribute("disabled");
      } else {
        submitButton.setAttribute("disabled", true);
      }
    });
  })
}