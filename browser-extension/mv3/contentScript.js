var port;
var prevemails=[];

 function arraysEqual(a, b) {
  if (a === b) return true;
  if (a == null || b == null) return false;
  if (a.length !== b.length) return false;

  // If you don't care about the order of the elements inside
  // the array, you should sort both arrays here.
  // Please note that calling sort on an array will modify that array.
  // you might want to clone your array first.

  for (var i = 0; i < a.length; ++i) {
    if (a[i] !== b[i]) return false;
  }
  return true;
}

function checkEmail() {
  var searchInThisString = document.getElementsByClassName("afV");
  var foundEmails = [];

  for (var i = 0; i < searchInThisString.length; i++) {
    foundEmails.push(searchInThisString[i].getAttribute("data-hovercard-id"));
  }

  if(foundEmails.length>0)
  chrome.runtime.sendMessage({emails: foundEmails}, function(response) {
    prevemails = foundEmails;
  });
  
  //console.log();
 // message = {"text": foundEmails};
 // port.postMessage(message);
}
function eventSetter() {
  var inputs = document.querySelectorAll("input");
  var messageBody = document.querySelectorAll('[aria-label="Message Body"]');;


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

  // msg = "hello world";
  // var hostName = "com.coporateeye.handler";
  // port = chrome.runtime.connectNative(hostName);

  setInterval(eventSetter, 5000);
  eventSetter();
 
 

  //console.log("Connecting to host: " + hostName);
 

  
};
init();
