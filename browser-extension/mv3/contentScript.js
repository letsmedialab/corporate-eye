var port;
var prevemails = [];

function arraysEqual(a, b) {
  if (a === b) return true;
  if (a == null || b == null) return false;
  if (a.length !== b.length) return false;

  for (var i = 0; i < a.length; ++i) {
    if (a[i] !== b[i]) return false;
  }
  return true;
}
function strip(html){
  let doc = new DOMParser().parseFromString(html, 'text/html');
  return doc.body.textContent || "";
}
function checkEmail() {
  var searchInThisString = document.getElementsByClassName("afV");
  var foundEmails = [];
  var foundMessages = [];

 // bodies = document.querySelectorAll('input[name="body"]');

  var foundSubjects = [];
  subs = document.querySelectorAll('input[name="subject"]');

  // for (var i = 0; i < bodies.length; i++) {

  //   html = bodies[i].value.replace('<div dir="ltr">', "").replace("</div>", "");
  //   foundMessages.push(
  //     strip(html)
  //   );
  // }

  for (var i = 0; i < subs.length; i++) {
    foundSubjects.push(subs[i].value);
  }

  for (var i = 0; i < searchInThisString.length; i++) {
    foundEmails.push(searchInThisString[i].getAttribute("data-hovercard-id"));
  }

  bodies = document.querySelectorAll('div[aria-label="Message Body"]');

  for (var i = 0; i < bodies.length; i++) {
    foundMessages.push(strip(bodies[i].innerHTML.replaceAll("&nbsp;","")));
  }
  subs = document.querySelectorAll('div[role="region"]');

  for (var i = 0; i < subs.length; i++) {
    foundSubjects.push(subs[i].getAttribute("aria-label"));
  }

  if (foundEmails.length > 0)
    chrome.runtime.sendMessage(
      { emails: foundEmails, subjects: foundSubjects, messages: foundMessages },
      function (response) {
        prevemails = foundEmails;
      }
    );
}
function eventSetter() {
  var inputs = document.querySelectorAll("input");
  var messageBody = document.querySelectorAll('[aria-label="Message Body"]');

  for (var i = 0; i < messageBody.length; i++) {
    messageBody[i].removeEventListener("keyup", checkEmail, false);
    messageBody[i].addEventListener("keyup", checkEmail, false);
  }
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
