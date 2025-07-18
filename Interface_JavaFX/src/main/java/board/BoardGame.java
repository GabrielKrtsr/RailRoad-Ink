package board;

import cell.BorderCell;
import cell.CentralCell;
import util.Path;
import util.util_for_nodes.Side;

/**
 * Represents a concrete implementation of a board game.
 */
public class BoardGame extends Board{


    /**
     * Constructor of the BoardGame
     */
    public BoardGame() {
        super(7);
        new NodeBoardInitializer(this).initializeNodes();
    }

    @Override
    protected void initBorderCell() {
        this.cells[0][1] = new BorderCell(Side.TOP,Path.ROAD,true);
        this.cells[0][3] = new BorderCell(Side.TOP,Path.RAIL,true);
        this.cells[0][5] = new BorderCell(Side.TOP,Path.ROAD,true);

        this.cells[1][0] = new BorderCell(Side.LEFT,Path.RAIL,true);
        this.cells[3][0] = new BorderCell(Side.LEFT,Path.ROAD,true);
        this.cells[5][0] = new BorderCell(Side.LEFT,Path.RAIL,true);

        this.cells[1][6] = new BorderCell(Side.RIGHT,Path.RAIL,true);
        this.cells[3][6] = new BorderCell(Side.RIGHT,Path.ROAD,true);
        this.cells[5][6] = new BorderCell(Side.RIGHT,Path.RAIL,true);

        this.cells[6][1] = new BorderCell(Side.BOTTOM,Path.ROAD,true);
        this.cells[6][3] = new BorderCell(Side.BOTTOM,Path.RAIL,true);
        this.cells[6][5] = new BorderCell(Side.BOTTOM,Path.ROAD,true);

        this.cells[2][3] = new CentralCell();
        this.cells[3][3] = new CentralCell();
        this.cells[4][3] = new CentralCell();

        this.cells[2][2] = new CentralCell();
        this.cells[3][2] = new CentralCell();
        this.cells[4][2] = new CentralCell();

        this.cells[2][4] = new CentralCell();
        this.cells[3][4] = new CentralCell();
        this.cells[4][4] = new CentralCell();

        //
        for(int i = 0; i < this.cells.length; i++){
            for(int j = 0; j < this.cells[i].length; j++){
                this.cells[i][j].setBoard(this);
                this.cells[i][j].setId(incrementId);
                incrementId++;
            }
        }

    }
}
