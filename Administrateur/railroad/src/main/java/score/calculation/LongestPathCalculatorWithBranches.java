package score.calculation;

import util.Path;
import util.Tuple;
import util.util_for_nodes.Connection;
import util.util_for_nodes.Node;

import java.util.HashSet;
import java.util.Set;
import java.util.function.BiFunction;

/**
 * The LongestPathCalculatorWithBranches class extends the LongestPathCalculator class
 * and provides a specific implementation for calculating the longest path that includes
 * branching logic. This calculator considers all branches emanating from central nodes
 * and calculates the total path length by recursively analyzing paths in the graph.
 */
public class LongestPathCalculatorWithBranches extends LongestPathCalculator{

    /**
     * Constructor for the LongestRailCalculatorWithoutStations class.
     * Initializes the operation used for calculating the score by combining results.
     * The default operation is set to addition of two values.
     *
     * @param operation
     * @param id
     * @param path
     */
    public LongestPathCalculatorWithBranches(BiFunction<Double, Double, Double> operation, String id, Path path) {
        super(operation, id, path);
    }

    /**
     * Recursively calculates the total length of the longest path in a graph from the given node,
     * accounting for branching logic. Nodes are visited only once to prevent revisiting, and
     * branching logic is applied specially for central nodes in the graph.
     *
     * @param node the current node from where the longest path calculation begins
     * @param visited a set of nodes that have already been visited to prevent cycles
     * @return the total length of the longest path from the given node, including branches
     */
    protected double findLongestPath(Node node, Set<Node> visited) {
        visited.add(node);
        double totalBranchLength = 0;

        for (Tuple<Node, Connection> connection : node.getConnections()) {
            if (connection.getType2().getPathType() == path) {
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
