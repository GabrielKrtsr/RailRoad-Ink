package graph;


import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * The ConnectivityInspector class provides utility methods to analyze the
 * connectivity of a graph. It is a generic class that works with any type of
 * vertices and edges, where edges extend the Edge class.
 *
 * @param <V> the type of the vertices
 * @param <E> the type of the edges, which must extend the Edge class
 */
public class ConnectivityInspector<V, E extends Edge<V>> {

    /**
     * The graph instance on which connectivity operations are performed.
     * It represents a directed or undirected graph containing vertices of type V
     * and edges of type E. The edges must extend the Edge class.
     *
     * Used internally by the ConnectivityInspector class to analyze graph
     * properties such as connectivity of vertices and components.
     */
    private final Graph<V, E> graph;

    /**
     * Constructs a ConnectivityInspector for the given graph.
     *
     * @param graph the graph on which connectivity inspections will be performed
     */
    public ConnectivityInspector(Graph<V, E> graph) {
        this.graph = graph;
    }

    /**
     * Determines if the graph is connected. A graph is considered connected if there
     * is a path between every pair of vertices. If the graph has no vertices, it is
     * considered trivially connected.
     *
     * @return true if the graph is connected, false otherwise
     */
    public boolean isConnected() {
        if (graph.getVertices().isEmpty()) return true;

        Set<V> visited = new HashSet<>();
        Iterator<V> iterator = graph.getVertices().iterator();
        if (!iterator.hasNext()) return false;

        V start = iterator.next();
        traverse(start, visited);

        return visited.size() == graph.getVertices().size();
    }

    /**
     * Determines whether two vertices in a graph are connected.
     * Two vertices are considered connected if there is a path between them.
     *
     * @param vertex1 the first vertex to check for connectivity
     * @param vertex2 the second vertex to check for connectivity
     * @return true if there exists a path between vertex1 and vertex2, false otherwise
     */
    public boolean areConnected(V vertex1, V vertex2) {
        if (!graph.containsVertex(vertex1) || !graph.containsVertex(vertex2)) return false;

        Set<V> visited = new HashSet<>();
        traverse(vertex1, visited);

        return visited.contains(vertex2);
    }

    /**
     * Identifies and retrieves all connected components in the graph as sets of vertices.
     * A connected component is defined as a subset of the graph's vertices where
     * any two vertices are connected by paths, and which is connected to no additional vertices
     * in the graph.
     *
     * @return a set of sets, where each inner set represents a connected component in the graph.
     *         Each inner set contains the vertices that belong to that specific connected component.
     */
    public Set<Set<V>> connectedSets() {
        Set<Set<V>> connectedComponents = new HashSet<>();
        Set<V> visitedNodes = new HashSet<>();

        for (V vertex : graph.getVertices()) {
            if (!visitedNodes.contains(vertex)) {
                Set<V> connectedComponent = new HashSet<>();
                traverse(vertex, connectedComponent);
                connectedComponents.add(connectedComponent);
                visitedNodes.addAll(connectedComponent);
            }
        }

        return connectedComponents;
    }

    /**
     * Traverses the graph starting from the specified vertex and adds each visited vertex to the provided set.
     * It performs a depth-first traversal of the graph, following outgoing edges to their targets
     * and all edges to their sources to ensure comprehensive exploration.
     *
     * @param vertex the starting vertex for the traversal
     * @param visited the set of visited vertices, which will be populated during the traversal
     */
    private void traverse(V vertex, Set<V> visited) {
        if (visited.contains(vertex)) return;
        visited.add(vertex);

        for (E edge : graph.getOutgoingEdges(vertex)) {
            V target = edge.getTarget();
            if (!visited.contains(target)) {
                traverse(target, visited);
            }
        }

        for (E edge : graph.getEdges(vertex)) {
            V source = edge.getSource();
            if (!visited.contains(source)) {
                traverse(source, visited);
            }
        }
    }
}