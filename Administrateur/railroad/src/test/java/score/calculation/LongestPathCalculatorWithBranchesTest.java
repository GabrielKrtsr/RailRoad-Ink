package score.calculation;

import org.junit.jupiter.api.Test;
import util.Path;
import util.util_for_nodes.Node;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LongestPathCalculatorWithBranchesTest extends LongestPathCalculatorTest{

    @Override
    protected LongestPathCalculator createCaulator(Path path) {
        return new LongestPathCalculatorWithBranches(sumOperation,path.name(),path);
    }

    @Test
    void testEmptyBoardRail() {
        assertEquals(0, calculatorR.calculateScore(board));
    }

    @Test
    void testSingleNodeRail() {
        Node node = new Node();
        Set<Node> nodeSet = new HashSet<>(Set.of(node));
        board.getGraph().addVertex(nodeSet);

        assertEquals(0, calculatorR.calculateScore(board));
    }

    @Test
    void testTwoConnectedNodesRail() {
        Node node1 = new Node();
        Node node2 = new Node();
        connectNodes(node1, node2, Path.RAIL);

        assertEquals(1.0, calculatorR.calculateScore(board));
    }

    @Test
    void testThreeConnectedNodesRail() {
        Node node1 = new Node();
        Node node2 = new Node();
        Node node3 = new Node();

        connectNodes(node1, node2, Path.RAIL);
        connectNodes(node2, node3, Path.RAIL);

        assertEquals(2.0, calculatorR.calculateScore(board));
    }

    @Test
    void testCircularPathRail() {
        Node node1 = new Node();
        Node node2 = new Node();
        Node node3 = new Node();

        connectNodes(node1, node2, Path.RAIL);
        connectNodes(node2, node3, Path.RAIL);
        connectNodes(node3, node1, Path.RAIL);

        assertEquals(4.0, calculatorR.calculateScore(board));
    }

    @Test
    void testMultiplePathsRail() {
        Node nodeA = new Node();
        Node nodeB = new Node();
        connectNodes(nodeA, nodeB, Path.RAIL);

        Node nodeX = new Node();
        Node nodeY = new Node();
        Node nodeZ = new Node();
        connectNodes(nodeX, nodeY, Path.RAIL);
        connectNodes(nodeY, nodeZ, Path.RAIL);

        assertEquals(2.0, calculatorR.calculateScore(board));
    }

    @Test
    void testIgnoreNonRailConnections() {
        Node node1 = new Node();
        Node node2 = new Node();
        connectNodes(node1, node2, Path.ROAD);

        assertEquals(0, calculatorR.calculateScore(board));
    }

    @Test
    void testRoadPathCalculation() {
        Node node1 = new Node();
        Node node2 = new Node();
        Node node3 = new Node();
        connectNodes(node1, node2, Path.ROAD);
        connectNodes(node2, node3, Path.ROAD);

        assertEquals(2.0, calculatorH.calculateScore(board));
    }

    @Test
    void testMixedPathTypes() {
        Node node1 = new Node();
        Node node2 = new Node();
        Node node3 = new Node();
        connectNodes(node1, node2, Path.RAIL);
        connectNodes(node2, node3, Path.ROAD);

        assertEquals(1.0, calculatorR.calculateScore(board));
        assertEquals(1.0, calculatorH.calculateScore(board));
    }

    @Test
    void testGetOperation() {

        assertEquals(3.0, calculatorR.getOperation().apply(1.5, 1.5));
    }

    @Test
    void testGetId() {
        assertEquals("RAIL", calculatorR.getId());
    }
}
