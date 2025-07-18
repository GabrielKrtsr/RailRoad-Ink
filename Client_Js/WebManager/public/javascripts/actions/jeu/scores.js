import {getID} from '../../jeuScript.js';
//import {getPlayer} from '../../partieScript.js';

export default function executeScoresCommand(args) {

    let id = args[2];
    if(id === getID()) {
        document.getElementById(`score-${args[3]}`).textContent = args[4];
        // switch (args[3]) {
        //     case "N":
        //         getPlayer(getID()).board.network = parseInt(args[4])
        //         break;
        //     case "H":
        //         getPlayer(getID()).board.longestRoad = parseInt(args[4])
        //         break;
        //     case "R":
        //         getPlayer(getID()).board.longestRail = parseInt(args[4])
        //         break;
        //     case "C":
        //         getPlayer(getID()).board.centerCell = parseInt(args[4])
        //         break;
        //     case "X":
        //         getPlayer(getID()).board.penalties = parseInt(args[4])
        //         break;
        //     case "TOTAL":
        //         getPlayer(getID()).board.total = parseInt(args[4])
        //         break;
        //     default:
        //         break;
        // }
    }
}