package score.calculation;

import util.Path;
import util.Tuple;
import util.util_for_nodes.Connection;
import util.util_for_nodes.Node;

import java.util.HashSet;
import java.util.Set;

/**
 * Calculates the longest connected rail path in a given graph.
 */
public class LongestRailCalculatorWithStations extends LongestRailCalculator {
    /**
     * Recursively calculates the longest rail path starting from the given node
     * while considering connections and central nodes between them.
     *
     * @param node The starting node for the path traversal.
     * @param visited A set containing nodes that have already been visited to avoid cycles.
     * @return The length of the longest rail path starting from the provided node.
     */
    protected double findLongestPath(Node node, Set<Node> visited) {
        visited.add(node);
        double maxLength = 0;

        for (Tuple<Node, Connection> connection : node.getConnections()) {
            double currentLength;
            if (connection.getType2().getPathType() == Path.RAIL) {
                Node adjacentNode = connection.getType1();
                if (!visited.contains(adjacentNode)) {

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
