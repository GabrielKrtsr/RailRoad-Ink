import interpreter from "./interpreterPartie.js";
import Player from './player.js';
import {addMessage} from './messages.js'

let players = {}
let currentPlayer = null;

let messageQueue = [];
let isProcessing = false;

// window.onload = function() {
//     currentPlayer.select();
//     console.error("Select current player");
// };

function processMessageQueue() {
    if (messageQueue.length > 0 && !isProcessing) {
        isProcessing = true;
        let nextMessage = messageQueue.shift();
        interpreter.interpret(nextMessage)
            .then(() => {
                isProcessing = false;
                processMessageQueue();
            })
            .catch((error) => {
                console.error("Erreur de traitement du message :", error);
                isProcessing = false;
                processMessageQueue();
            });
    }
}

const getPlayer = (id) => {
    return players[id];
}

const getCurrentPlayer = () => {
    return currentPlayer;
}

const setCurrentPlayer = (player) => {
    currentPlayer = players[player];
}

const addPlayer = (id) => {
    players[id] = new Player(id);
}

const selectPlayer = (playerId) => {
    if (players[playerId]) {
        if(currentPlayer != null) {
            document.getElementById(currentPlayer.name).style.color = "#ECF0F1";
        }
        setCurrentPlayer(playerId);
        document.getElementById(currentPlayer.name).style.color = "red";
        players[playerId].select();
    }
}

const displayGrid = () => {
    document.getElementById("svg-container").style.display = "flex";
}

const hideGrid = () => { 
    document.getElementById("svg-container").style.display = "none";
}

const socket = new WebSocket("ws://localhost:8000");
socket.addEventListener("open", () => {
    console.log("Connexion etablie !");
    document.getElementById("error").style.display = "none";
    document.getElementById("score").style.display = "flex";
    displayGrid();
});

socket.addEventListener("message", (event) => {
    console.log(`Message recu : ${event.data}`);
    addMessage(event.data);
    messageQueue.push(event.data);
    processMessageQueue();
});

socket.addEventListener("error", (error) => {
    console.log(`Erreur Websocket : ${error}`);
});

socket.addEventListener("close", () => {
    console.log(`Connexion fermée`);
    document.getElementById("error").textContent = "Aucune partie n'est en cours";
    hideGrid();
});

export {getPlayer, addPlayer, getCurrentPlayer, selectPlayer}