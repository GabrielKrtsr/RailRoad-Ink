package board;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import cell.BorderCell;
import cell.Cell;
import cell.CentralCell;
import cell.ClassicCell;
import util.Path;
import util.util_for_nodes.Side;

public class ClassicBoardInitializerTest {

    private Board board;
    private ClassicBoardInitializer initializer;


    @Before
    public void setUp() {
        board = new BoardGame();
        initializer = new ClassicBoardInitializer(board);
        initializer.initialize();
    }


    @Test
    public void testAllCellsInitialized() {
        Cell[][] cells = board.getCells();
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[0].length; j++) {
                assertNotNull( cells[i][j]);
            }
        }
    }


    @Test
    public void testCentralCellsPlacement() {
        Cell[][] cells = board.getCells();
        for (int i = 2; i <= 4; i++) {
            for (int j = 2; j <= 4; j++) {
                assertTrue(cells[i][j] instanceof CentralCell);
            }
        }
    }

    @Test
    public void testClassicCellsPlacement() {
        Cell[][] cells = board.getCells();
        assertTrue( cells[1][1] instanceof ClassicCell);
        assertTrue(cells[1][5] instanceof ClassicCell);
        assertTrue( cells[5][1] instanceof ClassicCell);
        assertTrue(cells[5][5] instanceof ClassicCell);
    }


    @Test
    public void testBorderCellsPlacement() {
        Cell[][] cells = board.getCells();

        assertTrue(cells[0][1] instanceof BorderCell);
        assertEquals(Side.TOP,cells[0][1].getBorder().getType1());
        assertEquals(Path.ROAD,cells[0][1].getBorder().getType2());

        assertTrue(cells[0][3] instanceof BorderCell);
        assertEquals(Side.TOP,cells[0][3].getBorder().getType1());
        assertEquals(Path.RAIL, cells[0][3].getBorder().getType2());

        assertTrue(cells[3][0] instanceof BorderCell);
        assertEquals(Side.LEFT, cells[3][0].getBorder().getType1());
        assertEquals(Path.ROAD, cells[3][0].getBorder().getType2());

        assertTrue(cells[3][6] instanceof BorderCell);
        assertEquals(Side.RIGHT, cells[3][6].getBorder().getType1());
        assertEquals(Path.ROAD,cells[3][6].getBorder().getType2());

        assertTrue(cells[6][3] instanceof BorderCell);
        assertEquals(Side.BOTTOM,cells[6][3].getBorder().getType1());
        assertEquals(Path.RAIL,cells[6][3].getBorder().getType2());
    }

    @Test
    public void testCellsBoardConnection() {
        Cell[][] cells = board.getCells();
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[0].length; j++) {
                assertSame(board, cells[i][j].getBoard());
            }
        }
    }


    @Test
    public void testCellCounts() {
        Cell[][] cells = board.getCells();
        int classicCount = 0;
        int centralCount = 0;
        int borderCount = 0;

        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[0].length; j++) {
                if (cells[i][j] instanceof ClassicCell && !(cells[i][j] instanceof BorderCell)) {
                    classicCount++;
                } else if (cells[i][j] instanceof CentralCell) {
                    centralCount++;
                } else if (cells[i][j] instanceof BorderCell) {
                    borderCount++;
                }
            }
        }

        assertEquals(9, centralCount);
        assertEquals(12, borderCount);
        assertEquals(28, classicCount);
    }
}