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
import java.util.function.BiFunction;

import static org.junit.jupiter.api.Assertions.*;

class FalseRouteCalculatorTest {
    private FalseRouteCalculator calculator;
    private Board board;
    private Graph<Set<Node>, Edge<Set<Node>>> graph;

    @BeforeEach
    void setUp() {
        calculator = this.createCalculator();
        board = new Board(3) {
            @Override
            protected void initBorderCell() {
            }
        };
        graph = new Graph<>();
    }

    protected FalseRouteCalculator createCalculator() {
        return new FalseRouteCalculator();
    }

    @Test
    void testCalculateScore_EmptyBoard() {
        double result = calculator.calculateScore(board);
        assertEquals(0, result);
    }

    @Test
    void testCalculateScore_SingleFalseRoute() {
        Node falseNode = new Node();
        falseNode.setId(1);

        Set<Node> nodeSet = new HashSet<>();
        nodeSet.add(falseNode);
        graph.addVertex(nodeSet);

        board.addGraph(new SimpleBoardGraph() {
            @Override
            public Graph<Set<Node>, Edge<Set<Node>>> getGraph() {
                return graph;
            }
        });

        double result = calculator.calculateScore(board);
        assertEquals(this.expectedValue2(), result);
    }

    protected double expectedValue2() {
        return 0.0;
    }
    @Test
    void testCalculateScore_IgnoreCentralNode() {
        Node centralNode = new Node();
        centralNode.setId(1);
        centralNode.setCentralNode(true);

        Set<Node> nodeSet = new HashSet<>();
        nodeSet.add(centralNode);
        graph.addVertex(nodeSet);

        board.addGraph(new SimpleBoardGraph() {
            @Override
            public Graph<Set<Node>, Edge<Set<Node>>> getGraph() {
                return graph;
            }
        });

        double result = calculator.calculateScore(board);
        assertEquals(0, result);
    }

    @Test
    void testCalculateScore_IgnoreExitNode() {
        Node exitNode = new Node();
        exitNode.setId(1);
        exitNode.setExitNode(true);

        Set<Node> nodeSet = new HashSet<>();
        nodeSet.add(exitNode);
        graph.addVertex(nodeSet);

        board.addGraph(new SimpleBoardGraph() {
            @Override
            public Graph<Set<Node>, Edge<Set<Node>>> getGraph() {
                return graph;
            }
        });

        double result = calculator.calculateScore(board);
        assertEquals(0, result);
    }

    @Test
    void testCalculateScore_MultipleFalseRoutes() {
        Node falseNode1 = new Node();
        falseNode1.setId(1);
        Node falseNode2 = new Node();
        falseNode2.setId(2);

        Set<Node> nodeSet1 = new HashSet<>();
        nodeSet1.add(falseNode1);
        Set<Node> nodeSet2 = new HashSet<>();
        nodeSet2.add(falseNode2);
        graph.addVertex(nodeSet1);
        graph.addVertex(nodeSet2);

        board.addGraph(new SimpleBoardGraph() {
            @Override
            public Graph<Set<Node>, Edge<Set<Node>>> getGraph() {
                return graph;
            }
        });

        double result = calculator.calculateScore(board);
        assertEquals(this.expectedValue1(), result);
    }
    protected double expectedValue1() {
        return 0.0;
    }

    @Test
    void testGetOperation() {
        BiFunction<Double, Double, Double> op = calculator.getOperation();
        assertEquals(2.0, op.apply(5.0, 3.0));
    }

    @Test
    void testGetId() {
        assertEquals("X", calculator.getId());
    }
}