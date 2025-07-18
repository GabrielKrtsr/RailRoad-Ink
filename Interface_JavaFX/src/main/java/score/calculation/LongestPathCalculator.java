package score.calculation;

import board.Board;
import graph.Edge;
import graph.Graph;
import util.Path;
import util.util_for_nodes.Node;

import java.util.HashSet;
import java.util.Set;
import java.util.function.BiFunction;

public abstract class LongestPathCalculator implements ScoreCalculator{

    /**
     * Specifies the type of path being calculated in the context of evaluating the longest path
     * within a game board graph. This variable holds information about the type of connection
     * (either RAIL or ROAD or extension) that is considered while performing path calculations.
     */
    protected Path path;


    /**
     * The unique identifier for the LongestPathCalculatorWithoutStations. This ID is used
     * to distinguish between different instances or configurations of the
     * calculator. It is typically used to retrieve or reference a specific
     * implementation of the score calculator based on its identifier.
     */
    private String id;

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
    public LongestPathCalculator(BiFunction<Double,Double,Double> operation, String id, Path path) {
        this.operation = operation;
        this.id = id;
        this.path = path;
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
    protected double findLongestPath(Node node, Set<Node> visited) {
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
        return id;
    }




}
