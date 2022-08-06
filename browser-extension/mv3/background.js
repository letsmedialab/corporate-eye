var port;
function initWorker() {
  port = chrome.runtime.connectNative("com.coporateeye.handler");

  port.onMessage.addListener(function (msg) {
    console.log("Received" + msg);
  });
  port.onDisconnect.addListener(function () {
    initWorker();
  });
}

initWorker();

chrome.runtime.onMessage.addListener(function (request, sender, sendResponse) {
  dataTS = {
    messages: request.messages,
    subjects: request.subjects,
    emails: request.emails,
  };
  //if (dataTS.length > 0) {
    port.postMessage(dataTS);
  //}

  sendResponse({ datax: request.emails });
});

// connect();
