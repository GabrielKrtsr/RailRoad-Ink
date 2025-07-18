package score.calculation;

import board.Board;
import graph.Edge;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.Path;
import util.util_for_nodes.Connection;
import util.util_for_nodes.Node;
import graph.Edge;
import graph.Graph;

import java.util.HashSet;
import java.util.Set;
import java.util.function.BiFunction;

import static org.junit.jupiter.api.Assertions.*;

abstract class LongestRailCalculatorTest {
    protected LongestRailCalculator calculator;
    protected Board board;

    @BeforeEach
    void setUp() {
        calculator = this.createCaculator();
        board = new Board(3) {
            @Override
            protected void initBorderCell() {}
        };
    }

    protected abstract LongestRailCalculator createCaculator();

    protected void connectNodesWithRail(Node node1, Node node2) {
        node1.addConnection(node2, new Connection(Path.RAIL));

        Set<Node> set1 = new HashSet<>(Set.of(node1));
        Set<Node> set2 = new HashSet<>(Set.of(node2));
        board.getGraph().addVertex(set1);
        board.getGraph().addVertex(set2);
        board.getGraph().addEdge(new Edge<>(set1, set2, 1.0));
    }

    @Test
    void testEmptyBoard() {
        assertEquals(0, calculator.calculateScore(board));
    }

    @Test
    void testSingleNode() {
        Node node = new Node();
        Set<Node> nodeSet = new HashSet<>(Set.of(node));
        board.getGraph().addVertex(nodeSet);

        assertEquals(0, calculator.calculateScore(board));
    }

    @Test
    void testTwoConnectedNodes() {
        Node node1 = new Node();
        Node node2 = new Node();
        connectNodesWithRail(node1, node2);

        assertEquals(this.expectedValue2(), calculator.calculateScore(board));
    }

    @Test
    void testThreeConnectedNodes() {
        Node node1 = new Node();
        Node node2 = new Node();
        Node node3 = new Node();

        connectNodesWithRail(node1, node2);
        connectNodesWithRail(node2, node3);

        // 2 connexions = 1.0
        assertEquals(this.expectedValue3(), calculator.calculateScore(board));
    }

    @Test
    void testCircularPath() {
        Node node1 = new Node();
        Node node2 = new Node();
        Node node3 = new Node();

        connectNodesWithRail(node1, node2);
        connectNodesWithRail(node2, node3);
        connectNodesWithRail(node3, node1);

        assertEquals(this.expectedValue1(), calculator.calculateScore(board));
    }

    protected abstract double expectedValue1();
    protected abstract double expectedValue2();
    protected abstract double expectedValue3();
    protected abstract double expectedValue4();
    @Test
    void testMultiplePaths() {

        Node nodeA = new Node();
        Node nodeB = new Node();
        connectNodesWithRail(nodeA, nodeB);

        Node nodeX = new Node();
        Node nodeY = new Node();
        Node nodeZ = new Node();
        connectNodesWithRail(nodeX, nodeY);
        connectNodesWithRail(nodeY, nodeZ);

        assertEquals(this.expectedValue4(), calculator.calculateScore(board));
    }

    @Test
    void testIgnoreNonRailConnections() {
        Node node1 = new Node();
        Node node2 = new Node();

        node1.addConnection(node2, new Connection(Path.ROAD));

        Set<Node> set1 = new HashSet<>(Set.of(node1));
        Set<Node> set2 = new HashSet<>(Set.of(node2));
        board.getGraph().addVertex(set1);
        board.getGraph().addVertex(set2);
        board.getGraph().addEdge(new Edge<>(set1, set2, 1.0));

        assertEquals(0, calculator.calculateScore(board));
    }

    @Test
    void testGetOperation() {
        BiFunction<Double, Double, Double> op = calculator.getOperation();
        assertEquals(3.0, op.apply(1.5, 1.5));
    }

    @Test
    void testGetId() {
        assertEquals("R", calculator.getId());
    }
}