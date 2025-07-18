package score.calculation;

import board.Board;
import graph.Edge;
import graph.Graph;
import util.util_for_nodes.Node;

import java.util.HashSet;
import java.util.Set;
import java.util.function.BiFunction;

public abstract class LongestRoadCalculator implements ScoreCalculator{

    /**
     * Identifier for the LongestRoadCalculatorWithoutStations.
     * Used to uniquely represent this ScoreCalculator implementation.
     */
    private final static String ID = "H";

    /**
     * Represents a mathematical operation to be performed on two Double values
     * and returns a Double result. This function is utilized within the
     * LongestRoadCalculatorWithoutStations to combine or evaluate values related to the
     * calculation of the longest road in a graph.
     */
    private BiFunction<Double,Double,Double> operation;

    /**
     * Initializes an instance of the LongestRoadCalculatorWithoutStations class.
     * This calculator is responsible for determining the length of the longest continuous road
     * in a given board graph. It leverages an operation function to accumulate scores.
     *
     * The operation used within this calculator is a BiFunction that performs addition on
     * two given double values. This function can be used to combine scores as required.
     */
    public LongestRoadCalculator() {
        this.operation = (a, b) -> a + b;
    }

    /**
     * Calculates the longest road in the graph of the given board.
     *
     * @param board the board containing the graph to be analyzed
     * @return the length of the longest road found in the graph
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
     * Recursively finds the length of the longest path composed of ROAD connections
     * starting from the given node while keeping track of visited nodes to avoid revisiting.
     *
     * @param node the starting node for calculating the longest path
     * @param visited the set of nodes that have already been visited during the traversal
     * @return the length of the longest ROAD path starting from the given node
     */
    protected double findLongestPath(Node node, Set<Node> visited) {
        return 0;
    }
    /**
     * Retrieves the operation used for combining two double values in the
     * longest road score calculation.
     *
     * @return a BiFunction representing the operation, which takes two Double arguments
     *         and returns a Double result.
     */
    public BiFunction<Double, Double, Double> getOperation() {
        return this.operation;
    }
    /**
     * Returns the unique identifier for this score calculator.
     *
     * @return the unique identifier as a string
     */
    @Override
    public String getId() {
        return ID;
    }


}
