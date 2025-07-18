package score.detection;

import board.Board;
import cell.Cell;
import graph.ConnectivityInspector;
import graph.Edge;
import graph.Graph;
import util.util_for_nodes.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * The NetworksDetector class provides functionality to analyze a graph structure
 * within a board and detect groups of connected nodes, also referred to as networks.
 */
public class NetworksDetector {

    /**
     * Detects and identifies the networks of connected nodes within a given board's graph.
     *
     * @param board the input board containing the graph and related data structure
     * @return a list of networks, where each network is represented as a list of cells
     */
    public static List<List<Cell>> detectNetworks(Board board) {

        Graph<Set<Node>, Edge<Set<Node>>> graph = board.getGraph();
        ConnectivityInspector<Set<Node>, Edge<Set<Node>>> inspector = new ConnectivityInspector<>(graph);
        Set<Set<Set<Node>>> networks = inspector.connectedSets();

        List<List<Cell>> networksCells = new ArrayList<>();

        for(Set<Set<Node>> network : networks){
            List<Cell> cells = new ArrayList<>();
            for(Set<Node> nodeSet : network){
                for(Node node :  nodeSet){
                    Cell cell = board.getCellByNodeWithInnerConnections(node);
                    if(!cells.contains(cell)){
                        cells.add(cell);
                    }
                }
            }
            networksCells.add(cells);
        }
        return networksCells;
    }


}
