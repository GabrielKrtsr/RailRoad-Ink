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
public class LongestRoadCalculatorWithBranches extends LongestRailCalculator {
    /**
     * Recursively calculates the longest path in a graph starting from a given node,
     * restricted to paths of a specified type and avoiding previously visited nodes.
     *
     * @param node the current node from which to calculate the longest path
     * @param visited a set of nodes that have already been visited to avoid cycles
     * @return the total length of the longest path from the given node
     */
    protected double findLongestPath(Node node, Set<Node> visited) {
        visited.add(node);
        double totalBranchLength = 0;

        for (Tuple<Node, Connection> connection : node.getConnections()) {
            if (connection.getType2().getPathType() == Path.ROAD) {
                Node adjacentNode = connection.getType1();

                if (!visited.contains(adjacentNode)) {
                    double branchLength;

                    if (node.isCentralNode()) {
                        branchLength = findLongestPath(adjacentNode, new HashSet<>(visited));
                    } else {
                        branchLength = 1 + findLongestPath(adjacentNode, new HashSet<>(visited));
                    }
                    totalBranchLength += branchLength;
                }
            }
        }
        return totalBranchLength;
    }
}
