import {getMessage} from '../messages.js'
import { getPlayer } from '../partieScript.js';

export default function executeBlamesCommand(args) {
    console.log(`Blames receive for message ${args[2]} -> ${getMessage(parseInt(args[2]))}`);
    let message = getMessage(parseInt(args[2]));
    let cell = message.split(" ")[4];
    let player = getPlayer(message.split(" ")[0]);
    console.log(`Remove image from player ${player.name} for cell ${cell}`)
    let container = player.getBoard().container;
    console.log(`Container : ${container}`)
    let targetElement = container.querySelector(`#${cell}`);
    console.log(`Target element : ${targetElement}`)
    // let image = targetElement.querySelector("image");
    // console.log(`Image : ${image}`)
    // //image.remove();
    // image.parentNode.removeChild(image);
    // console.log(`Image après suppression :`, targetElement.querySelector("image"));

    player.getBoard().addBlame(cell);
    console.warn(`BLAME FOR PLACING ${cell} !!! !!!`)

}
