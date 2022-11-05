function checkEmail(data) {
  console.log(data.target.textContent);
}

function eventSetter() {
  emails = document.getElementsByClassName("VOlRn");

  for (var i = 0; i < emails.length; i++) {
    emails[i].removeEventListener("DOMSubtreeModified", checkEmail, false);
    emails[i].addEventListener("DOMSubtreeModified", checkEmail, false);
    
  }
}
const init = function () {
  setInterval(eventSetter, 5000);
  eventSetter();
};
init();