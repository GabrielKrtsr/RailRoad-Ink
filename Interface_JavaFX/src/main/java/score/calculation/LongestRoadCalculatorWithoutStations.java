package score.calculation;

import util.Path;
import util.Tuple;
import util.util_for_nodes.Connection;
import util.util_for_nodes.Node;

import java.util.HashSet;
import java.util.Set;

/**
 * This class calculates the longest road in a given graph.
 */
public class LongestRoadCalculatorWithoutStations extends LongestRoadCalculator{

    /**
     * Recursively finds the length of the longest path composed of ROAD connections
     * starting from the given node while keeping track of visited nodes to avoid revisiting.
     *
     * @param node the starting node for calculating the longest path
     * @param visited the set of nodes that have already been visited during the traversal
     * @return the length of the longest ROAD path starting from the given node
     */
    protected double findLongestPath(Node node, Set<Node> visited) {
        visited.add(node);
        double maxLength = 0;

        for (Tuple<Node, Connection> connection : node.getConnections()) {
            double currentLength;
            if (connection.getType2().getPathType() == Path.ROAD) { // Only count ROADS
                Node adjacentNode = connection.getType1();
                if (!visited.contains(adjacentNode) && !node.isStationNode()) {

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
