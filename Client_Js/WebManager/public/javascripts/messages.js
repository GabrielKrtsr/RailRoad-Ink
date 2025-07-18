let messages = Array();
let numberOfMessages = 0;

function addMessage(message) {
    messages.push(message);
    console.log(`New message added to the array : ${message} (${messages.length})`);
}

function getMessage(id) {
    console.log(`ID : ${id} / ${numberOfMessages}`)
    return messages.at((id));
}

function clearMessages() {
    numberOfMessages += messages.length;
    messages = messages.splice(0);
}

export {addMessage, getMessage, clearMessages};