package score.calculation;

import util.Path;
import util.Tuple;
import util.util_for_nodes.Connection;
import util.util_for_nodes.Node;

import java.util.HashSet;
import java.util.Set;
import java.util.function.BiFunction;

/**
 * A specialized implementation of the LongestPathCalculator, designed to calculate
 * the longest path in a graph with additional logic involving stations or central nodes.
 * The class considers specific conditions for central nodes while evaluating paths.
 */
public class LongestPathCalculatorWithStations extends LongestPathCalculator{

    /**
     * Constructor for the LongestRailCalculatorWithoutStations class.
     * Initializes the operation used for calculating the score by combining results.
     * The default operation is set to addition of two values.
     *
     * @param operation
     * @param id
     * @param path
     */
    public LongestPathCalculatorWithStations(BiFunction<Double, Double, Double> operation, String id, Path path) {
        super(operation, id, path);
    }

    /**
     * Finds the longest path starting from the given node under certain conditions.
     * It evaluates valid paths based on specific criteria, including node types and
     * connection types, and calculates the length of the path recursively.
     *
     * @param node The starting node for calculating the longest path.
     * @param visited A set of nodes that have already been visited to prevent cycles.
     * @return The length of the longest path starting from the given node, as a double.
     */
    protected double findLongestPath(Node node, Set<Node> visited) {
        visited.add(node);
        double maxLength = 0;

        for (Tuple<Node, Connection> connection : node.getConnections()) {
            double currentLength;
            if (connection.getType2().getPathType() == path) { // Only count ROADS
                Node adjacentNode = connection.getType1();
                if (!visited.contains(adjacentNode)) {

                    if(!adjacentNode.isCentralNode() && !node.isCentralNode()){
                        currentLength = 1 + findLongestPath(adjacentNode, new HashSet<>(visited));
                    }
                    else{
                        currentLength = 0.5 + findLongestPath(adjacentNode, new HashSet<>(visited));
                    }
                    maxLength = Math.max(maxLength, currentLength);
                }
            }
        }
        return maxLength;
    }
}
