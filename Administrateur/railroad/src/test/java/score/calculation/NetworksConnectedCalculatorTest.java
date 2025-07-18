package score.calculation;

import board.Board;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.SimpleBoardGraph;
import util.util_for_nodes.Node;
import graph.Edge;
import graph.Graph;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class NetworksConnectedCalculatorTest {
    private NetworksConnectedCalculator calculator;
    private Board board;

    @BeforeEach
    void setUp() {
        calculator = new NetworksConnectedCalculator();
        board = new Board(3) {
            @Override
            protected void initBorderCell() {
                // Implémentation vide pour les tests
            }
        };
    }

    @Test
    void testCalculateScore_EmptyBoard() {
        double score = calculator.calculateScore(board);
        assertEquals(0, score);
    }

    @Test
    void testCalculateScore_SingleNetworkOneExit() {
        Node exitNode = new Node();
        exitNode.setExitNode(true);

        Set<Node> nodeSet = new HashSet<>();
        nodeSet.add(exitNode);

        Graph<Set<Node>, Edge<Set<Node>>> graph = new Graph<>();
        graph.addVertex(nodeSet);

        board.addGraph(new SimpleBoardGraph() {
            @Override
            public Graph<Set<Node>, Edge<Set<Node>>> getGraph() {
                return graph;
            }
        });

        double score = calculator.calculateScore(board);
        assertEquals(0, score);
    }

    @Test
    void testCalculateScore_SingleNetworkTwoExits() {
        // réseau avec 2 sorties
        Node exitNode1 = new Node();
        exitNode1.setExitNode(true);

        Node exitNode2 = new Node();
        exitNode2.setExitNode(true);

        Set<Node> nodeSet = new HashSet<>();
        nodeSet.add(exitNode1);
        nodeSet.add(exitNode2);

        Graph<Set<Node>, Edge<Set<Node>>> graph = new Graph<>();
        graph.addVertex(nodeSet);

        board.addGraph(new SimpleBoardGraph() {
            @Override
            public Graph<Set<Node>, Edge<Set<Node>>> getGraph() {
                return graph;
            }
        });

        double score = calculator.calculateScore(board);
        assertEquals(4, score);
    }

    @Test
    void testCalculateScore_MultipleNetworks() {
        Node exitNode1 = new Node();
        exitNode1.setExitNode(true);
        Set<Node> network1 = new HashSet<>();
        network1.add(exitNode1);

        Node exitNode2 = new Node();
        exitNode2.setExitNode(true);
        Node exitNode3 = new Node();
        exitNode3.setExitNode(true);
        Node exitNode4 = new Node();
        exitNode4.setExitNode(true);
        Set<Node> network2 = new HashSet<>();
        network2.add(exitNode2);
        network2.add(exitNode3);
        network2.add(exitNode4);

        Graph<Set<Node>, Edge<Set<Node>>> graph = new Graph<>();
        graph.addVertex(network1);
        graph.addVertex(network2);

        board.addGraph(new SimpleBoardGraph() {
            @Override
            public Graph<Set<Node>, Edge<Set<Node>>> getGraph() {
                return graph;
            }
        });

        double score = calculator.calculateScore(board);
        assertEquals(8, score);
    }

    @Test
    void testCalculateScore_NetworkWithNoExits() {
        Node normalNode = new Node();
        Set<Node> network = new HashSet<>();
        network.add(normalNode);

        Graph<Set<Node>, Edge<Set<Node>>> graph = new Graph<>();
        graph.addVertex(network);

        board.addGraph(new SimpleBoardGraph() {
            @Override
            public Graph<Set<Node>, Edge<Set<Node>>> getGraph() {
                return graph;
            }
        });

        double score = calculator.calculateScore(board);
        assertEquals(0, score);
    }

    @Test
    void testCalculateScore_Complex() {
        Node exit1 = new Node();
        exit1.setExitNode(true);
        Node exit2 = new Node();
        exit2.setExitNode(true);
        Set<Node> network1 = new HashSet<>();
        network1.add(exit1);
        network1.add(exit2);

        Node exit3 = new Node();
        exit3.setExitNode(true);
        Node exit4 = new Node();
        exit4.setExitNode(true);
        Node exit5 = new Node();
        exit5.setExitNode(true);
        Node exit6 = new Node();
        exit6.setExitNode(true);
        Set<Node> network2 = new HashSet<>();
        network2.add(exit3);
        network2.add(exit4);
        network2.add(exit5);
        network2.add(exit6);

        Node exit7 = new Node();
        exit7.setExitNode(true);
        Set<Node> network3 = new HashSet<>();
        network3.add(exit7);

        Graph<Set<Node>, Edge<Set<Node>>> graph = new Graph<>();
        graph.addVertex(network1);
        graph.addVertex(network2);
        graph.addVertex(network3);

        board.addGraph(new SimpleBoardGraph() {
            @Override
            public Graph<Set<Node>, Edge<Set<Node>>> getGraph() {
                return graph;
            }
        });

        double score = calculator.calculateScore(board);
        assertEquals(16, score);
    }

    @Test
    void testGetId() {
        assertEquals("N", calculator.getId());
    }
}