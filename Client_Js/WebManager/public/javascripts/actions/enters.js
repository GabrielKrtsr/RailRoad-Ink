import {addPlayer, getCurrentPlayer, selectPlayer, getPlayer} from '../partieScript.js'

export default function executeEntersCommand(args) {
    console.log(`Check admin : ${args[0] == "admin"}`)
    if(getPlayer(args[0]) == null && args[0] != "admin") {
        console.log("Add new player")
        let playerDiv = document.getElementById("players");
        let button = document.createElement("button");
        button.setAttribute("id",args[0]);
        button.addEventListener("click", (event) => {
            selectPlayer(args[0]);
        });
        button.setAttribute("disabled","true");
        button.style.backgroundColor = "lightgrey";
        button.textContent = args[0];
        playerDiv.appendChild(button);
        addPlayer(args[0]);
        if(getCurrentPlayer() == null) {
            selectPlayer(args[0]);
        }
    } else {
        console.log("Player already exists or player is admin")
    }
}
