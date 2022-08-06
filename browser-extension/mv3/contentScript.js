function checkEmail() {
  var searchInThisString = document.getElementsByClassName("afV");
  var foundEmails = [];

  for (var i = 0; i < searchInThisString.length; i++) {
    foundEmails.push(searchInThisString[i].getAttribute("data-hovercard-id"));
  }
  console.log(foundEmails);
}
function eventSetter() {
  var inputs = document.querySelectorAll("input");
  for (var i = 0; i < inputs.length; i++) {
    inputs[i].removeEventListener("keyup", checkEmail, false);
    inputs[i].addEventListener("keyup", checkEmail, false);
  }
  window.removeEventListener("click", checkEmail, false);
  window.addEventListener("click", checkEmail, false);
}
const init = function () {
  setInterval(eventSetter, 5000);
  eventSetter();
};
init();
