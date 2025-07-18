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
public class LongestRoadCalculatorWithStations extends LongestRoadCalculator{

    /**
     * Finds the longest path of roads in a graph starting from the given node.
     * The method considers only paths marked as roads and accounts for special cases involving central nodes.
     *
     * @param node The current node from which the path exploration begins.
     * @param visited A set of nodes that have already been visited to avoid cycles during path computation.
     * @return The length of the longest path found starting from the given node, measured as a double.
     */
    protected double findLongestPath(Node node, Set<Node> visited) {
        visited.add(node);
        double maxLength = 0;

        for (Tuple<Node, Connection> connection : node.getConnections()) {
            double currentLength;
            if (connection.getType2().getPathType() == Path.ROAD) {
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
