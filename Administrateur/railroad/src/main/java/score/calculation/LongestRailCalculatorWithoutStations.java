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
public class LongestRailCalculatorWithoutStations extends LongestRailCalculator {

    /**
     * Recursively calculates the longest connected path starting from the given node,
     * considering only rail paths while avoiding stations and revisiting nodes.
     *
     * @param node the starting node for the path calculation
     * @param visited a set of nodes that have already been visited to prevent cycles
     * @return the length of the longest valid path from the given node
     */
    protected double findLongestPath(Node node, Set<Node> visited) {
        visited.add(node);
        double maxLength = 0;

        for (Tuple<Node, Connection> connection : node.getConnections()) {
            double currentLength;
            if (connection.getType2().getPathType() == Path.RAIL) {
                Node adjacentNode = connection.getType1();
                if (!visited.contains(adjacentNode) && !node.isStationNode()) {

                    if(!adjacentNode.isCentralNode() && !node.isCentralNode()){
                        currentLength = 1 + findLongestPath(adjacentNode, new HashSet<>(visited));
                    }
                    else{
                        currentLength = 0.5 +  findLongestPath(adjacentNode, new HashSet<>(visited));
                    }
                    maxLength = Math.max(maxLength, currentLength);
                }
            }
        }
        return maxLength;
    }
}
