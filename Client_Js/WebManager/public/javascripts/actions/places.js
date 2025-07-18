import {getPlayer} from '../partieScript.js'

export default function executePlacesCommand(args) {
    let tile = args[2];
    let rotation = args[3];
    let position = args[4];
    console.log(`Let's place this tile in ${position}`);
    getPlayer(args[0]).placeTile(position,tile,rotation);
}
