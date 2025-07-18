import {addMessage, getMessage, clearMessages} from '../../messages.js'
import { getActions, getID, getNumberOfTilesToPlace, incrementNumberOfTilesToPlace, decrementNumberOfTilesToPlace } from '../../jeuScript.js';

export default function executeBlamesCommand(args) {
    console.log(`Blames receive for message ${args[2]} -> ${getMessage(parseInt(args[2]))}`);
    let message = getMessage(parseInt(args[2]));

    document.getElementById("blameError").textContent = `${document.getElementById("blameError").textContent} ${message}`
    document.getElementById("blameError").style.display = "flex";

    console.log(`Cond 1 : ${message.split(" ")[0] === getID()}`)
    console.log(`Cond 2 : ${message.split(" ")[1] === "PLACES"}`)
    if(message.split(" ")[0] === getID() && message.split(" ")[1] === "PLACES") {
        let cell = message.split(" ")[4];
        console.log(`Blame for cell : ${cell}`);
        let image = document.getElementById(cell).querySelector("image");
        const divFace = document.querySelector('.face');
        const imgb = document.createElement('img');
        imgb.setAttribute('draggable', 'true');
        imgb.setAttribute('id', image.getAttribute("id"));
        imgb.setAttribute('src', `./images/tiles/${message.split(" ")[2]}.svg`);
        imgb.setAttribute('alt', image.getAttribute("alt")); 
        imgb.setAttribute('style', 'width: 3%; height: 3%;');
        imgb.setAttribute('data-tile', image.getAttribute("data-tile"));
        divFace.appendChild(imgb);
        image.remove();

        incrementNumberOfTilesToPlace();
        if (getNumberOfTilesToPlace() > 0) {
            document.getElementById("next").disabled = true;
        }

        getActions().splice(getActions().indexOf(cell),1);
        console.log(`Element removed from array`);

    }
}
