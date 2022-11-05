var port;
var imn=0;
function execMessage() {
//   chrome.runtime.sendNativeMessage(
//     "com.coporateeye.handler",
//     { text: "Hello" },
//     function (response) {
//       console.log("Received " + response);
//     }
//   );
  // port = chrome.runtime.connectNative('com.coporateeye.handler');
  // port.onMessage.addListener(function(msg) {
  //   console.log("Received" + msg);
  // });
  // port.onDisconnect.addListener(function() {
  //   console.log("Disconnected");
  // });
  // port.postMessage({ text: "Hello, my_application" });

  msg = "hello world";
  var hostName = "com.coporateeye.handler";

  console.log("Connecting to host: " + hostName);
  port = chrome.runtime.connectNative(hostName);

  message = {"text": msg};
  port.postMessage(message);

  console.log("Sent message: " + JSON.stringify(message));
  
  setInterval(printmessage,2000);

}
function printmessage()
{
  imn++;
  port.postMessage(message + imn );
}
// var host_name = "com.coporateeye.handler";
// var port = null;


// function connectToNative() {
//     alert("this");
//     alert('Connecting to native host: ' + host_name);
//     port = chrome.runtime.connectNative("com.coporateeye.handler");
//     port.onMessage.addListener(onNativeMessage);
//     port.onDisconnect.addListener(onDisconnected);
//     sendNativeMessage("test");
// }

// function sendNativeMessage(msg) {
//     message = {"text" : msg};
//     console.log('Sending message to native app: ' + JSON.stringify(message));
//     port.postMessage(message);
//     console.log('Sent message to native app: ' + msg);
// }

// function onNativeMessage(message) {
//     console.log('recieved message from native app: ' + JSON.stringify(msg));
// }

// function onDisconnected() {
//     console.log(chrome.runtime.lastError);
//     console.log('disconnected from native app.');
//     port = null;
// }
document.getElementById("dcl").addEventListener("click", execMessage, false);
