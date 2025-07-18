package board;

import cell.BorderCell;
import cell.CentralCell;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BoardGameTest extends BoardTest {

    @Override
    protected Board createBoard() {
        return new BoardGame();
    }

    @Test
    void testBorderCellInitialization() {
        assertTrue(board.getCell(0, 1) instanceof BorderCell);
        assertTrue(board.getCell(0, 3) instanceof BorderCell);
        assertTrue(board.getCell(0, 5) instanceof BorderCell);
        assertTrue(board.getCell(6, 1) instanceof BorderCell);
        assertTrue(board.getCell(6, 3) instanceof BorderCell);
        assertTrue(board.getCell(6, 5) instanceof BorderCell);
    }

    @Test
    void testCentralCellInitialization() {
        assertTrue(board.getCell(2, 3) instanceof CentralCell);
        assertTrue(board.getCell(3, 3) instanceof CentralCell);
        assertTrue(board.getCell(4, 3) instanceof CentralCell);
        assertTrue(board.getCell(2, 2) instanceof CentralCell);
        assertTrue(board.getCell(3, 2) instanceof CentralCell);
        assertTrue(board.getCell(4, 2) instanceof CentralCell);
        assertTrue(board.getCell(2, 4) instanceof CentralCell);
        assertTrue(board.getCell(3, 4) instanceof CentralCell);
        assertTrue(board.getCell(4, 4) instanceof CentralCell);
    }
}
