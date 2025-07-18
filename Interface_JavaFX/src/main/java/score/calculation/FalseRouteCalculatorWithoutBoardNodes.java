package score.calculation;

import board.Board;
import graph.Edge;
import graph.Graph;
import util.util_for_nodes.Node;

import java.util.Set;

/**
 * Calculates the number of false routes in a given graph.
 */
public class FalseRouteCalculatorWithoutBoardNodes extends FalseRouteCalculator{
    /**
     * Counts the number of false routes in the graph.
     *
     * @return The total count of false routes.
     */
    public double calculateScore(Board board) {
        double count = 0;
        Graph<Set<Node>, Edge<Set<Node>>> graph = board.getGraph();

        for (Set<Node> nodeSet : graph.getVertices()) {
            for (Node node : nodeSet) {
                if (!node.isBoardNode() &&!node.isCentralNode() && !node.isExitNode() &&
                        graph.getVertices().stream().filter(set -> set.contains(node)).count() == 1) {
                    count++;
                }
            }
        }
        return count;
    }
}
