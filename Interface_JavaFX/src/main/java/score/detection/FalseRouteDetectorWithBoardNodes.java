package score.detection;

import board.Board;
import cell.Cell;
import graph.Edge;
import graph.Graph;
import util.util_for_nodes.Node;
import util.util_for_nodes.Side;

import java.util.*;


/**
 * The FalseRouteDetectorWithoutBoardNodes class provides methods to analyze a given board structure and identify false routes.
 * False routes are defined as nodes that are not central nodes, not exit nodes, and appear in exactly one vertex
 * of the graph representation of the board. The class offers methods to detect these false routes and map them
 * to their corresponding cells or sides within the board.
 */
public class FalseRouteDetectorWithBoardNodes implements FalseRouteDetector{

    /**
     * Detects and identifies false routes in a board, associating them with their respective cells and nodes.
     * A false route is defined as a node that is not central, not an exit node, and exists in exactly one vertex of the graph.
     *
     * @param board the board containing the graph structure and cells to be analyzed
     * @return a map where each key is a cell containing false routes, and each value is a list of nodes corresponding to those false routes
     */
    public  static Map<Cell, List<Node>> detectFalseRoutesWithNodes(Board board) {
        Map<Cell, List<Node>> falseRoutes = new HashMap<>();
        Graph<Set<Node>, Edge<Set<Node>>> graph = board.getGraph();

        for (Set<Node> nodeSet : graph.getVertices()) {
            for (Node node : nodeSet) {
                if (!node.isCentralNode() && !node.isExitNode() &&
                        graph.getVertices().stream().filter(set -> set.contains(node)).count() == 1) {

                    Node falseNode = node;

                    for (Cell [] cellLine : board.getCells()) {
                        for(Cell cell : cellLine){
                            if (cell.getInnerConnectionsGraph().containsVertex(falseNode)) {
                                falseRoutes.computeIfAbsent(cell, k -> new ArrayList<>()).add(falseNode);
                            }
                        }
                    }
                }
            }
        }
        return falseRoutes;
    }
    /**
     * Detects false routes within a given board and maps them by their corresponding cells and sides.
     * A false route is identified as a node that is not a central or exit node and is unique to a single node set in the graph.
     * The method determines which sides of the cells are associated with the detected false routes.
     *
     * @param board The board containing the graph, cells, and configurations for route detection.
     * @return A map where the keys are cells containing false routes and the values are lists of sides associated with the false routes for those cells.
     */
    public static  Map<Cell, List<Side>> detectFalseRoutesWithSides(Board board) {
        Map<Cell, List<Side>> falseRoutes = new HashMap<>();
        Graph<Set<Node>, Edge<Set<Node>>> graph = board.getGraph();


        for (Set<Node> nodeSet : graph.getVertices()) {
            for (Node node : nodeSet) {
                if (!node.isCentralNode() && !node.isExitNode() &&
                        graph.getVertices().stream().filter(set -> set.contains(node)).count() == 1) {

                    Node falseNode = node;

                    for (Cell [] cellLine : board.getCells()) {
                        for(Cell cell : cellLine){
                            if (cell.getInnerConnectionsGraph().containsVertex(falseNode)) {
                                falseRoutes.computeIfAbsent(cell, k -> new ArrayList<>()).add(cell.getSide(falseNode));
                            }
                        }
                    }
                }
            }
        }
        return falseRoutes;
    }

}
