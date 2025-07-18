import {getPlayer, getCurrentPlayer} from '../partieScript.js';

export default function executeEntersCommand(args) {

    let id = args[2];
    switch (args[3]) {
        case "N":
            console.log(`Value : ${getPlayer(id).getBoard().network}`)
            getPlayer(id).getBoard().network = parseInt(args[4])
            break;
        case "H":
            console.log(`Value : ${getPlayer(id).getBoard().longestRoad}`)
            getPlayer(id).getBoard().longestRoad = parseInt(args[4])
            break;
        case "R":
            console.log(`Value : ${getPlayer(id).getBoard().longestRail}`)
            getPlayer(id).getBoard().longestRail = parseInt(args[4])
            break;
        case "C":
            console.log(`Value : ${getPlayer(id).getBoard().centerCell}`)
            getPlayer(id).getBoard().centerCell = parseInt(args[4])
            break;
        case "X":
            console.log(`Value : ${getPlayer(id).getBoard().penalties}`)
            getPlayer(id).getBoard().penalties = parseInt(args[4])
            break;
        case "TOTAL":
            console.log(`Value : ${getPlayer(id).getBoard().total}`)
            getPlayer(id).getBoard().total = parseInt(args[4])
            break;
        default:
            break;
    }
    if(getCurrentPlayer().name === id) {
        getCurrentPlayer().select();
    }
}