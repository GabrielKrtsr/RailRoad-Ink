package util;

import graph.Edge;
import graph.Graph;
import util.util_for_nodes.Node;

import java.util.Set;

/**
 * Represents a simplified board graph that manages node sets and their connections.
 */
public class SimpleBoardGraph {

    private Graph<Set<Node>, Edge<Set<Node>>> graph;

    /**
     * Constructor for SimpleBoardGraph, initializes an empty graph.
     */
    public SimpleBoardGraph() {
        this.graph = new Graph<>();
    }

    /**
     * Adds a set of nodes to the graph as a vertex.
     *
     * @param nodeSet The set of nodes to be added.
     */
    public void addNodeSet(Set<Node> nodeSet) {
        graph.addVertex(nodeSet);
    }

    /**
     * Connects two sets of nodes by adding an edge between them.
     *
     * @param nodeSet1 The first set of nodes.
     * @param nodeSet2 The second set of nodes.
     */
    public void connectNodeSets(Set<Node> nodeSet1, Set<Node> nodeSet2) {
        graph.addEdge(new Edge<>(nodeSet1, nodeSet2, 1));
    }

    /**
     * Retrieves the graph.
     *
     * @return The underlying graph structure.
     */
    public Graph<Set<Node>, Edge<Set<Node>>> getGraph() {
        return graph;
    }

    /**
     * Checks if there is a connection between two sets of nodes.
     *
     * @param nodeSet1 The first set of nodes.
     * @param nodeSet2 The second set of nodes.
     * @return True if there is an edge connecting the two sets, false otherwise.
     */
    public boolean containsConnection(Set<Node> nodeSet1, Set<Node> nodeSet2) {
        return this.graph.containsEdge(new Edge<>(nodeSet1, nodeSet2, 1));
    }

    /**
     * Retrieves all sets of connected nodes in the graph.
     *
     * @return A set containing all node sets in the graph.
     */
    public Set<Set<Node>> getSetOfConnections() {
        return graph.getVertices();
    }


}
