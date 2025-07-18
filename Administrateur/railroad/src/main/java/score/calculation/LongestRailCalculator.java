package score.calculation;

import board.Board;
import graph.Edge;
import graph.Graph;
import util.util_for_nodes.Node;

import java.util.HashSet;
import java.util.Set;
import java.util.function.BiFunction;

/**
 * Abstract class representing the logic for calculating the longest connected rail path
 * on a game board. Implements the ScoreCalculator interface and provides a default
 * operation for combining score components. Derived classes must implement the logic
 * for finding the longest path.
 */
public abstract class LongestRailCalculator implements ScoreCalculator {
    /**
     * A constant identifier used to uniquely represent the LongestRailCalculatorWithoutStations.
     * This ID is utilized as the unique string representation in contexts where
     * the calculator needs to be distinguished among other ScoreCalculator implementations.
     */
    private final static String ID = "R";

    /**
     * Represents an operation that accepts two input Double values and returns a result Double.
     * This BiFunction is used to perform a specific computation related to calculating scores
     * in the context of the rail-based game logic.
     */
    private BiFunction<Double,Double,Double> operation;

    /**
     * Constructor for the LongestRailCalculatorWithoutStations class.
     * Initializes the operation used for calculating the score by combining results.
     * The default operation is set to addition of two values.
     */
    public LongestRailCalculator() {
        this.operation = (a, b) -> a + b;
    }

    /**
     * Calculates the longest connected rail path in the graph associated with the given board.
     * This method evaluates the graph structure and determines the maximum possible
     * continuous rail path.
     *
     * @param board the board containing the graph representation of nodes and connections
     * @return the length of the longest rail path as a double
     */
    public double calculateScore(Board board) {
        double longest = 0;
        Graph<Set<Node>, Edge<Set<Node>>> graph = board.getGraph();

        if (graph != null) {
            for (Set<Node> nodeSet : graph.getVertices()) {
                for (Node node : nodeSet) {
                    double currentLongest = findLongestPath(node, new HashSet<>());
                    longest = Math.max(longest, currentLongest);
                }
            }
        }
        longest = Math.ceil(longest);
        return longest;
    }

    /**
     * Recursively calculates the longest path of RAIL connections in a graph
     * starting from a given node.
     *
     * @param node the current node to traverse from
     * @param visited the set of nodes that have already been visited during the traversal
     * @return the length of the longest path consisting only of RAIL connections, starting from the given node
     */
    protected double findLongestPath(Node node, Set<Node> visited){
        return 0;
    }
    /**
     * Returns the operation used to compute scores by combining two double values.
     *
     * @return a BiFunction that takes two Double arguments and computes a Double result.
     */
    public BiFunction<Double, Double, Double> getOperation() {
        return this.operation;
    }
    /**
     * Returns the unique identifier for this calculator.
     *
     * @return a string representing the unique ID of this calculator
     */
    @Override
    public String getId() {
        return ID;
    }

}
