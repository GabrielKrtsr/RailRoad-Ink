package score.detection;

import board.Board;
import cell.Cell;
import cell.ClassicCell;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.util_for_nodes.Node;
import util.util_for_nodes.Side;


import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class NetworksDetectorTest {
    private Board board;

    @BeforeEach
    void setUp() {
        board = new Board(3) {
            @Override
            protected void initBorderCell() {
            }
        };
    }

    @Test
    void testDetectNetworks_EmptyBoard() {
        List<List<Cell>> networks = NetworksDetector.detectNetworks(board);
        assertTrue(networks.isEmpty());
    }

    @Test
    void testSingleCellNoConnections() {
        ClassicCell cell = new ClassicCell();
        cell.setBoard(board);

        Node node = new Node();
        node.setId(1);
        cell.putNode(Side.TOP, node);

        Set<Node> innerSet = new HashSet<>();
        innerSet.add(node);
        cell.getInnerConnections().add(innerSet);

        board.updateVertex(List.of(innerSet));

        board.getCells()[0][0] = cell;

        assertFalse(board.getGraph().getVertices().isEmpty());

        List<List<Cell>> networks = NetworksDetector.detectNetworks(board);

        assertEquals(1, networks.size());
        assertEquals(1, networks.get(0).size());
        assertTrue(networks.get(0).contains(cell));
    }
}