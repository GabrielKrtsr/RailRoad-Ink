package board;

import cell.BorderCell;
import cell.Cell;
import cell.CentralCell;
import cell.ClassicCell;
import util.Path;
import util.util_for_nodes.Side;

/**
 * The ClassicBoardInitializer class is responsible for initializing the board structure
 * with classic configuration, including setting up the cells, border cells, and establishing
 * relationships between the board and its cells.
 */
public class ClassicBoardInitializer {
    /**
     * Represents a game board that is initialized and utilized within the ClassicBoardInitializer class.
     * This field is immutable and holds the reference to the Board object being managed and configured.
     */
    private final Board board;

    /**
     * Constructs a ClassicBoardInitializer with the specified board.
     *
     * @param board the board to be initialized
     */
    public ClassicBoardInitializer(Board board) {
        this.board = board;
    }

    /**
     * Initializes the board by setting up its cells, defining the border cells, and
     * linking each cell to the board.
     */
    public void initialize() {
        initializeCells();
        initializeBorderCells();
        connectCellsToBoard();
    }

    /**
     * Initializes the cells of the board by populating the central region with central cells
     * and the remaining areas with classic cells.
     */
    private void initializeCells() {
        Cell[][] cells = board.getCells();
        int size = cells.length;
        int sizeMiddle = size / 2;
        int blockSize = 3;

        int start = sizeMiddle - blockSize / 2;
        int end = sizeMiddle + blockSize / 2;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i >= start && i <= end && j >= start && j <= end) {
                    cells[i][j] = new CentralCell();
                } else {
                    cells[i][j] = new ClassicCell();
                }
            }
        }
    }

    /**
     * Initializes the border and central cells of the game board.
     */
    private void initializeBorderCells() {
        Cell[][] cells = board.getCells();

        cells[0][1] = new BorderCell(Side.TOP, Path.ROAD, true);
        cells[0][3] = new BorderCell(Side.TOP, Path.RAIL, true);
        cells[0][5] = new BorderCell(Side.TOP, Path.ROAD, true);

        cells[1][0] = new BorderCell(Side.LEFT, Path.RAIL, true);
        cells[3][0] = new BorderCell(Side.LEFT, Path.ROAD, true);
        cells[5][0] = new BorderCell(Side.LEFT, Path.RAIL, true);

        cells[1][6] = new BorderCell(Side.RIGHT, Path.RAIL, true);
        cells[3][6] = new BorderCell(Side.RIGHT, Path.ROAD, true);
        cells[5][6] = new BorderCell(Side.RIGHT, Path.RAIL, true);

        cells[6][1] = new BorderCell(Side.BOTTOM, Path.ROAD, true);
        cells[6][3] = new BorderCell(Side.BOTTOM, Path.RAIL, true);
        cells[6][5] = new BorderCell(Side.BOTTOM, Path.ROAD, true);

        cells[2][3] = new CentralCell();
        cells[3][3] = new CentralCell();
        cells[4][3] = new CentralCell();

        cells[2][2] = new CentralCell();
        cells[3][2] = new CentralCell();
        cells[4][2] = new CentralCell();

        cells[2][4] = new CentralCell();
        cells[3][4] = new CentralCell();
        cells[4][4] = new CentralCell();
    }

    /**
     * Links each cell of the board to its parent board instance.
     */
    private void connectCellsToBoard() {
        Cell[][] cells = board.getCells();
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[0].length; j++) {
                cells[i][j].setBoard(board);
            }
        }
    }
}