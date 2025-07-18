package board;

import cell.BorderCell;
import cell.Cell;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.util_for_nodes.Node;
import util.util_for_nodes.Side;
import util.Path;

import static org.junit.jupiter.api.Assertions.*;

class NodeBoardInitializerTest {
    private Board board;
    private NodeBoardInitializer nodeBoardInitializer;

    @BeforeEach
    void setUp() {
        board = new BoardGame();
        nodeBoardInitializer = new NodeBoardInitializer(board);
    }

    @Test
    void testInitializeNodes() {
        nodeBoardInitializer.initializeNodes();

        Cell topLeftCell = board.getCell(0, 1);
        assertNotNull(topLeftCell.getNode(Side.TOP));
        assertTrue(topLeftCell.getNode(Side.TOP).isExitNode());

        Cell centerCell = board.getCell(1, 1);
        assertNotNull(centerCell.getNode(Side.CENTER));
        assertFalse(centerCell.getNode(Side.CENTER).isExitNode());
    }

    @Test
    void testAddNodes() {
        nodeBoardInitializer.initializeNodes();

        for (int i = 0; i < board.getCells().length; i++) {
            for (int j = 0; j < board.getCells()[i].length; j++) {
                Cell cell = board.getCell(i, j);
                Node centralNode = cell.getNode(Side.CENTER);
                assertNotNull(centralNode);
                assertTrue(centralNode.isCentralNode());
            }
        }

        Cell topLeftCell = board.getCell(0, 0);
        assertNotNull(topLeftCell.getNode(Side.TOP));
        assertNotNull(topLeftCell.getNode(Side.LEFT));

        Cell bottomRightCell = board.getCell(2, 2);
        assertNotNull(bottomRightCell.getNode(Side.BOTTOM));
        assertNotNull(bottomRightCell.getNode(Side.RIGHT));

        Cell cell1 = board.getCell(1, 1);
        Cell cell2 = board.getCell(1, 2);
        Node sharedRightNode = cell1.getNode(Side.RIGHT);
        Node sharedLeftNode = cell2.getNode(Side.LEFT);
        assertSame(sharedRightNode, sharedLeftNode);
    }

    @Test
    void testAddExitNodes() {
        nodeBoardInitializer.initializeNodes();

        Cell topLeftCell = board.getCell(0, 1);
        assertTrue(topLeftCell.getNode(Side.TOP).isExitNode());
        assertFalse(topLeftCell.getNode(Side.LEFT).isExitNode());

        Cell bottomRightCell = board.getCell(0, 3);
        assertTrue(bottomRightCell.getNode(Side.TOP).isExitNode());
        assertFalse(bottomRightCell.getNode(Side.RIGHT).isExitNode());

        Cell centerCell = board.getCell(1, 1);
        assertFalse(centerCell.getNode(Side.CENTER).isExitNode());

        Cell bottomCell = board.getCell(6,1);
        assertTrue(bottomCell.getNode(Side.BOTTOM).isExitNode());

        Cell rightCell = board.getCell(1,6);
        assertTrue(rightCell.getNode(Side.RIGHT).isExitNode());


    }


}