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
public class LongestRailCalculatorWithBranches extends LongestRailCalculator {
    /**
     * Calculates the total length of the longest connected path of rail-type connections
     * starting from the given node, considering all possible branches and paths.
     *
     * @param node the starting node to calculate the path from
     * @param visited the set of nodes that have already been visited to prevent cycles
     * @return the total length of the longest connected path of rail-type connections
     */
    protected double findLongestPath(Node node, Set<Node> visited) {
        visited.add(node);
        double totalBranchLength = 0;

        for (Tuple<Node, Connection> connection : node.getConnections()) {
            if (connection.getType2().getPathType() == Path.RAIL) {
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
