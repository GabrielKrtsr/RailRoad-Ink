package score.calculation;

import util.Path;
import util.Tuple;
import util.util_for_nodes.Connection;
import util.util_for_nodes.Node;

import java.util.HashSet;
import java.util.Set;
import java.util.function.BiFunction;

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
