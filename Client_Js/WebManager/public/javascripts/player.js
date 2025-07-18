import Board from "./board.js";
import { getCurrentPlayer } from "./partieScript.js";

export default class Player {
    constructor(name) {
        this.name = name;
        this.board = new Board();
    }

    placeTile(position, tile, rotation) {
        this.board.addTile(position, tile, rotation);
        if(getCurrentPlayer() === this) {
            this.select();
        }
    }

    select() {
        this.board.showBoard();
    }

    getBoard() {
        return this.board;
    }
}