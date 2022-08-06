// var port="";
// function connect() {
//     // connect to local program com.a.chrome_interface
//     port = chrome.extension.connectNative('com.coporateeye.handler');
//     //port.onMessage.addListener(onNativeMessage);
//    // port.onDisconnect.addListener(onDisconnected);
// }

// chrome.extension.onRequest.addListener(function(data, sender) {
//     if (data.length > 0) {
//         if(!port)
//         {
//             connect();
//         }
//         sendNativeMessage(data);
//     }
// });

// function connect() {
//     // connect to local program com.a.chrome_interface
//   port = chrome.runtime.connectNative('com.coporateeye.handler');
//   port.onMessage.addListener(function(msg) {
//     console.log("Received" + msg);
//   });
//   port.onDisconnect.addListener(function() {
//     console.log("Disconnected");
//   });
//   port.postMessage({ text: "Hello, my_application" });
// }

var port;
function initWorker() {
  port = chrome.runtime.connectNative("com.coporateeye.handler");

  port.onMessage.addListener(function (msg) {
    console.log("Received" + msg);
  });
  port.onDisconnect.addListener(function () {
    initWorker()
  });
}

initWorker();
chrome.runtime.onMessage.addListener(function (request, sender, sendResponse) {
  if (request.emails.length > 0) port.postMessage(request.emails);

  sendResponse({ datax: request.emails });
});

// connect();
