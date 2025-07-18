package score.calculation;

import util.Path;
import util.Tuple;
import util.util_for_nodes.Connection;
import util.util_for_nodes.Node;

import java.util.HashSet;
import java.util.Set;
import java.util.function.BiFunction;

/**
 * This class calculates the longest path in a graph without including nodes that are designated as stations.
 * It extends the {@code LongestPathCalculator} class and overrides specific path-finding logic to exclude station nodes.
 * The class is specifically designed for handling path constraints related to rail or road paths in graph-based games.
 */
public class LongestPathCalculatorWithoutStations extends LongestPathCalculator{

    /**
     * Constructor for the LongestRailCalculatorWithoutStations class.
     * Initializes the operation used for calculating the score by combining results.
     * The default operation is set to addition of two values.
     *
     * @param operation
     * @param id
     * @param path
     */
    public LongestPathCalculatorWithoutStations(BiFunction<Double, Double, Double> operation, String id, Path path) {
        super(operation, id, path);
    }

    /**
     * Recursively finds the longest path from the given node following specific path constraints.
     * The path length is determined based on connections and whether a node is a central node.
     * Each node in the path is considered only once to avoid cyclic traversals.
     *
     * @param node The current node from which the longest path is to be calculated.
     * @param visited A set of nodes that have already been visited to ensure no node is revisited in the current path calculation.
     * @return The length of the longest path that can be traversed starting from the given node.
     */
    protected double findLongestPath(Node node, Set<Node> visited) {
        visited.add(node);
        double maxLength = 0;

        for (Tuple<Node, Connection> connection : node.getConnections()) {
            double currentLength;
            if (connection.getType2().getPathType() == path) {
                Node adjacentNode = connection.getType1();
                if (!visited.contains(adjacentNode) && !node.isStationNode()) {

                    if(!adjacentNode.isCentralNode() && !node.isCentralNode()){
                        currentLength = 1 + findLongestPath(adjacentNode, new HashSet<>(visited));
                    }
                    else{
                        currentLength =0.5 + findLongestPath(adjacentNode, new HashSet<>(visited));
                    }
                    maxLength = Math.max(maxLength, currentLength);
                }
            }
        }
        return maxLength;
    }
}
