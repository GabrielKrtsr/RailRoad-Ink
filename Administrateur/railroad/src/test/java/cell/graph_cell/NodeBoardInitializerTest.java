package cell.graph_cell;

import board.Board;
import board.BoardGame;
import cell.Cell;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.util_for_nodes.Side;

import static org.junit.jupiter.api.Assertions.*;

public class NodeBoardInitializerTest {

    private Board board;

    @BeforeEach
    public void setUp() {
        board = new BoardGame();
    }

    @Test
    public void testInitializeNodes() {
        Cell[][] cells = board.getCells();
        for (int row =0; row < cells.length; row++) {
            for (int col = 0; col < cells[row].length; col++) {
                Cell cell = cells[row][col];
                assertNotNull(cell.getNode(Side.CENTER), "Node at Side.CENTER should not be null");

                Cell bottomNeighbour = board.getNeighbour(row, col, Side.BOTTOM);
                if (bottomNeighbour != null) {
                    assertNotNull(cell.getNode(Side.BOTTOM), "Node at Side.BOTTOM should not be null");
                    assertNotNull(bottomNeighbour.getNode(Side.BOTTOM.getOppositeSide()), "Node at opposite side of Side.BOTTOM should not be null in neighbour cell");
                    assertSame(cell.getNode(Side.BOTTOM),bottomNeighbour.getNode(Side.TOP));
                }

                Cell rightNeighbour = board.getNeighbour(row, col, Side.RIGHT);
                if (rightNeighbour != null) {
                    assertNotNull(cell.getNode(Side.RIGHT), "Node at Side.RIGHT should not be null");
                    assertNotNull(rightNeighbour.getNode(Side.RIGHT.getOppositeSide()), "Node at opposite side of Side.RIGHT should not be null in neighbour cell");
                    assertSame(cell.getNode(Side.RIGHT),rightNeighbour.getNode(Side.LEFT));
                }
            }
        }
    }

    /*
    @Test
    public void testBorderCellsContainOnlyRelevantSides() {
        nodeBoardInitializer.initializeNodes();
        Cell[][] cells = board.getCells();
        int length = cells.length;

        for (int row = 0; row < length; row++) {
            for (int col = 0; col < length; col++) {
                Cell cell = cells[row][col];

                if (row == 0 && cell instanceof BorderCell) {
                    assertNotNull(cell.getNode(Side.BOTTOM), "Top border cell should only contain Bottom side");
                    assertNull(cell.getNode(Side.TOP), "Top border cell should not contain Top side");
                    assertNull(cell.getNode(Side.LEFT), "Top border cell should not contain Left side");
                    assertNull(cell.getNode(Side.RIGHT), "Top border cell should not contain Right side");
                }

                if (row == length - 1 && cell instanceof BorderCell) {
                    assertNotNull(cell.getNode(Side.TOP), "Bottom border cell should only contain Top side");
                    assertNull(cell.getNode(Side.BOTTOM), "Bottom border cell should not contain Bottom side");
                    assertNull(cell.getNode(Side.LEFT), "Bottom border cell should not contain Left side");
                    assertNull(cell.getNode(Side.RIGHT), "Bottom border cell should not contain Right side");
                }

                if (col == 0 && cell instanceof BorderCell) {
                    assertNotNull(cell.getNode(Side.RIGHT), "Left border cell should only contain Right side");
                    assertNull(cell.getNode(Side.TOP), "Left border cell should not contain Top side");
                    assertNull(cell.getNode(Side.BOTTOM), "Left border cell should not contain Bottom side");
                    assertNull(cell.getNode(Side.LEFT), "Left border cell should not contain Left side");
                }

                if (col == length - 1 && cell instanceof BorderCell) {
                    assertNotNull(cell.getNode(Side.LEFT), "Right border cell should only contain Left side");
                    assertNull(cell.getNode(Side.TOP), "Right border cell should not contain Top side");
                    assertNull(cell.getNode(Side.BOTTOM), "Right border cell should not contain Bottom side");
                    assertNull(cell.getNode(Side.RIGHT), "Right border cell should not contain Right side");
                }
            }
        }
    }

     */

}