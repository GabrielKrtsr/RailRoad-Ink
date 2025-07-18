package cell;

import board.Board;
import board.BoardGame;
import face.AbstractFace;
import face.HighwayFace;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.util_for_nodes.Node;
import util.util_for_nodes.Side;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public abstract class CellTest {

    protected Cell cell;
    protected Board mockBoard;


    @Test
    public void testGetPoints() {
        assertEquals(expectedPoints(), cell.getPoints());
    }


    @Test
    public void testIsExit() {
        assertEquals(expectedIsExit(), cell.isExit());
    }

    @Test
    public void testEqualsAndHashCode() {
        Cell anotherCell = createCellWithSameProperties();
        assertEquals(cell, anotherCell);
        assertEquals(cell.hashCode(), anotherCell.hashCode());
    }

    @Test
    public void testNotEquals() {
        Cell differentCell = createDifferentCell();
        assertNotEquals(cell, differentCell);
    }

    @Test
    public void testPutAndGetNode() {
        Node mockNode = new Node();
        cell.putNode(Side.TOP, mockNode);
        assertSame(mockNode, cell.getNode(Side.TOP));
    }

    @Test
    public void testConnectNodes() {
        Node node1 = new Node();
        Node node2 = new Node();
        cell.connectNodes(node1, node2, null);
        assertTrue(node1.isConnected(node2));
        assertTrue(node2.isConnected(node1));
    }

    @Test
    public void testToString(){
        assertEquals(cell.toString(), this.expectedSting());
    }


    @Test
    public void testAreTwoNodesConnected() {
        Node node1 = new Node();
        Node node2 = new Node();
        cell.connectNodes(node1, node2, null);
        assertTrue(cell.areTwoNodesConnected(node1, node2));
    }

    @Test
    public void testSendNotification() {
        mockBoard = new BoardGame();
        cell.setBoard(mockBoard);
        cell.sendNotification();
        assertNotNull(cell.getBoard());
    }

    @Test
    public void testGetInnerConnectionsGraph() {
        assertNotNull(cell.getInnerConnectionsGraph());
    }

    @Test
    public void testGetInnerConnections() {
        assertNotNull(cell.getInnerConnections());
        assertTrue(cell.getInnerConnections().isEmpty());
    }

    @Test
    public void testInitializeNodeMap() {
        for (Side side : Side.values()) {
            assertNull(cell.getNode(side));
        }
    }



    @Test
    public void testContainsNodeInInnerConnections() {
        Node testNode = new Node();
        Set<Node> nodeSet = new HashSet<>();
        nodeSet.add(testNode);

        cell.getInnerConnections().add(nodeSet);

        assertTrue(cell.containsNodeInInnerConnections(testNode));
        assertFalse(cell.containsNodeInInnerConnections(new Node()));
    }

    protected abstract int expectedPoints();

    protected abstract boolean expectedIsExit();

    protected abstract Cell createCellWithSameProperties();

    protected abstract Cell createDifferentCell();

    protected abstract String expectedSting();
}
