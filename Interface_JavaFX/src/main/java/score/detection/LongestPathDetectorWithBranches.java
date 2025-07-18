package score.detection;

import board.Board;
import cell.Cell;
import graph.Edge;
import graph.Graph;
import util.Path;
import util.Tuple;
import util.util_for_nodes.Connection;
import util.util_for_nodes.Node;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The LongestPathDetectorWithoutStations class provides functionality to detect the longest path
 * in a graph structure represented within a board. The detection is based on a specified
 * path type, and the class identifies the sequence of cells that constitute this longest path.
 */
public class LongestPathDetectorWithBranches implements LongestPathDetector{

    /**
     * Detects the longest path on a board matching the specified path type and returns
     * the list of cells that constitute this path.
     *
     * @param board The board containing the graph and its associated nodes and connections.
     * @param path The path type to match during the detection process.
     * @return A list of cells representing the longest path found on the board.
     *         Returns an empty list if no matching path is found.
     */
    public static List<Cell> detectLongestPath(Board board,Path path) {
        double longest = 0;

        List<Cell> cellsWithLongestPath = new ArrayList<>();
        Graph<Set<Node>, Edge<Set<Node>>> graph = board.getGraph();


        if (graph != null) {
            for (Set<Node> nodeSet : graph.getVertices()) {
                for (Node node : nodeSet) {
                    List<Cell> currentPathCells = new ArrayList<>();
                    double currentLongest = findLongestPathWithCells(node, new HashSet<>(), path, currentPathCells, board);
                    if (currentLongest > longest) {
                        longest = currentLongest;
                        cellsWithLongestPath = new ArrayList<>(currentPathCells);
                    }
                }
            }
        }
        System.out.println(cellsWithLongestPath.size());
        return cellsWithLongestPath;

    }
    /**
     * Recursively finds the longest rail path starting from the given node and collects the cells along the path.
     *
     * @param node The starting node.
     * @param visited A set of visited nodes to avoid cycles.
     * @param path The path type to match.
     * @param currentPathCells A list to store the cells along the current path.
     * @return The length of the longest path starting from the given node.
     */
    private static double findLongestPathWithCells(Node node, Set<Node> visited, Path path, List<Cell> currentPathCells, Board board) {
        visited.add(node);

        double totalBranchLength = 0;
        List<Cell> branchPathCells = new ArrayList<>();

        for (Tuple<Node, Connection> connection : node.getConnections()) {
            if (connection.getType2().getPathType() == path) {
                Node adjacentNode = connection.getType1();

                if (!visited.contains(adjacentNode)) {
                    List<Cell> subBranchCells = new ArrayList<>(currentPathCells);

                    subBranchCells.add(board.getCellByNodeWithInnerConnections(node));

                    double branchLength;

                    if(!adjacentNode.isCentralNode() && !node.isCentralNode()){
                        branchLength = 1 + findLongestPathWithCells(adjacentNode, new HashSet<>(visited), path, subBranchCells,board);
                    }
                    else{
                        branchLength = 0.5 + findLongestPathWithCells(adjacentNode, new HashSet<>(visited), path, subBranchCells,board);
                    }

                    totalBranchLength += branchLength;

                    branchPathCells.addAll(subBranchCells);
                }
            }
        }

        if (!currentPathCells.contains(board.getCellByNodeWithInnerConnections(node))) {
            branchPathCells.add(board.getCellByNodeWithInnerConnections(node));
        }
        currentPathCells.clear();
        currentPathCells.addAll(branchPathCells);

        return totalBranchLength;
    }


}
