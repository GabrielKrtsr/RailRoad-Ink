package score.calculation;

import board.Board;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.Path;
import util.util_for_nodes.Connection;
import util.util_for_nodes.Node;
import graph.Edge;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

abstract class LongestRoadCalculatorTest {
    private LongestRoadCalculator calculator;
    private Board board;

    @BeforeEach
    void setUp() {
        calculator = this.createCalculator();
        board = new Board(3) {
            @Override
            protected void initBorderCell() {}
        };
    }

    protected abstract LongestRoadCalculator createCalculator();

    protected void connectNodes(Node node1, Node node2) {
        // Connexion unidirectionnelle seulement
        node1.addConnection(node2, new Connection(Path.ROAD));

        // Ajout au graphe
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
        connectNodes(node1, node2);

        // 1 connexion unidirectionnelle = 0.5
        assertEquals(1.0, calculator.calculateScore(board));
    }

    @Test
    void testThreeConnectedNodes() {
        Node node1 = new Node();
        Node node2 = new Node();
        Node node3 = new Node();

        connectNodes(node1, node2);
        connectNodes(node2, node3);

        // 2 connexions unidirectionnelles = 1.0
        assertEquals(2.0, calculator.calculateScore(board));
    }

    @Test
    void testCircularPath() {
        Node node1 = new Node();
        Node node2 = new Node();
        Node node3 = new Node();

        connectNodes(node1, node2);
        connectNodes(node2, node3);
        connectNodes(node3, node1);

        // Le plus long chemin simple (2 connexions) = 1.0
        assertEquals(2.0, calculator.calculateScore(board));
    }

    @Test
    void testMultiplePaths() {
        Node nodeA = new Node();
        Node nodeB = new Node();
        connectNodes(nodeA, nodeB);

        Node nodeX = new Node();
        Node nodeY = new Node();
        Node nodeZ = new Node();
        connectNodes(nodeX, nodeY);
        connectNodes(nodeY, nodeZ);

        assertEquals(2.0, calculator.calculateScore(board));
    }

    @Test
    void testIgnoreNonRoadConnections() {
        Node node1 = new Node();
        Node node2 = new Node();

        node1.addConnection(node2, new Connection(Path.RAIL));

        Set<Node> set1 = new HashSet<>(Set.of(node1));
        Set<Node> set2 = new HashSet<>(Set.of(node2));
        board.getGraph().addVertex(set1);
        board.getGraph().addVertex(set2);
        board.getGraph().addEdge(new Edge<>(set1, set2, 1.0));

        assertEquals(0, calculator.calculateScore(board));
    }
}