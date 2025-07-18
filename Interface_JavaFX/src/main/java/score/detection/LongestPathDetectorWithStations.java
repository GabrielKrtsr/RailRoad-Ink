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
public class LongestPathDetectorWithStations implements LongestPathDetector{

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

        return cellsWithLongestPath;

    }
    private static double findLongestPathWithCells (Node node, Set < Node > visited, Path path, List < Cell > currentPathCells, Board board){
        visited.add(node);
        double maxLength = 0;
        List<Cell> longestPathCells = new ArrayList<>();

        for (Tuple<Node, Connection> connection : node.getConnections()) { //

            if (connection.getType2().getPathType() == path) {
                Node adjacentNode = connection.getType1();
                if (!visited.contains(adjacentNode)) {
                    double currentLength;
                    List<Cell> pathCells = new ArrayList<>(currentPathCells);
                    pathCells.add(board.getCellByNodeWithInnerConnections(node));
                    if(!adjacentNode.isCentralNode() && !node.isCentralNode()){
                        currentLength = 1 + findLongestPathWithCells(adjacentNode, new HashSet<>(visited), path, pathCells,board);
                    }
                    else{
                        currentLength = 0.5 + findLongestPathWithCells(adjacentNode, new HashSet<>(visited), path, pathCells,board);
                    }
                    if (currentLength > maxLength) {
                        maxLength = currentLength;
                        longestPathCells = new ArrayList<>(pathCells);
                    }
                }
            }
        }

        if (!currentPathCells.contains(board.getCellByNodeWithInnerConnections(node))) {
            longestPathCells.add(board.getCellByNodeWithInnerConnections(node));
        }

        currentPathCells.clear();
        currentPathCells.addAll(longestPathCells);

        return maxLength;
    }


}
