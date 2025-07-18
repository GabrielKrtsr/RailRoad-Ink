package graph;


import java.util.*;

/**
 * A generic Graph class that represents a directed graph structure using an adjacency list.
 *
 * @param <V> the type of the vertices in the graph
 * @param <E> the type of the edges in the graph; extends the Edge class constrained by the vertex type V
 */
public class Graph<V, E extends Edge<V>> {

    /**
     * Represents the adjacency list of the graph where each vertex is mapped to a list of its outgoing edges.
     * The key in the map is a vertex, and the value is a list of edges originating from that vertex.
     * This structure is utilized to efficiently store and retrieve graph relationships.
     */
    private final Map<V, List<E>> adjacencyList = new HashMap<>();


    /**
     * Adds a vertex to the graph. If the vertex is not already present,
     * it initializes an empty adjacency list for it.
     *
     * @param vertex the vertex to be added to the graph
     */
    public void addVertex(V vertex) {
        adjacencyList.putIfAbsent(vertex, new ArrayList<>());
    }
    
    /**
     * Adds an edge to the graph by connecting its source and target vertices.
     * If the source or target vertex does not already exist in the graph, it is added automatically.
     *
     * @param edge the edge to be added to the graph; the edge must have a defined source and target vertex.
     */
    public void addEdge(E edge) {
        V source = edge.getSource();
        V target = edge.getTarget();
        addVertex(source);
        addVertex(target);
        adjacencyList.get(source).add(edge);
    }
    
    /**
     * Retrieves the edges associated with a specific vertex in the graph.
     * If the vertex has no edges, an empty list is returned.
     *
     * @param vertex the vertex whose edges are to be retrieved
     * @return a list of edges connected to the specified vertex, or an empty list if no edges exist
     */
    public List<E> getEdges(V vertex) {
        return adjacencyList.getOrDefault(vertex, Collections.emptyList());
    }


    /**
     * Retrieves all vertices in the graph as a set.
     *
     * @return a set containing all vertices in the graph.
     */
    public Set<V> getVertices() {
        return adjacencyList.keySet();
    }
    
    /**
     * Checks if the specified vertex exists in the graph.
     *
     * @param vertex the vertex to check for existence in the graph
     * @return true if the vertex exists in the graph, false otherwise
     */
    public boolean containsVertex(V vertex) {
        return adjacencyList.containsKey(vertex);
    }
    
    /**
     * Checks if the specified edge exists in the graph.
     *
     * @param edge the edge to check for existence in the graph
     * @return true if the edge exists in the graph, otherwise false
     */
    public boolean containsEdge(E edge) {
        List<E> edges = adjacencyList.get(edge.getSource());
        return edges != null && edges.contains(edge);
    }

    
    /**
     * Retrieves the list of outgoing edges from the specified vertex in the graph.
     * If the vertex has no outgoing edges or does not exist in the graph, an empty list is returned.
     *
     * @param vertex the vertex for which the outgoing edges are to be retrieved
     * @return a list of edges that originate from the specified vertex, or an empty list if none are found
     */
    public List<E> getOutgoingEdges(V vertex) {
        return adjacencyList.getOrDefault(vertex, Collections.emptyList());
    }
    
    /**
     * Retrieves the target vertex of the specified edge.
     *
     * @param edge the edge from which the target vertex is to be retrieved
     * @return the target vertex of the specified edge
     */
    public V getTarget(E edge) {
        return edge.getTarget();
    }
    
    /**
     * Retrieves the weight of a given edge in the graph.
     *
     * @param edge the edge for which the weight is to be retrieved
     * @return the weight of the specified edge
     */
    public double getEdgeWeight(E edge) {
        return edge.getWeight();
    }


    /**
     * Get the number of vertices in the graph.
     *
     * @return the count of vertices.
     */
    public int getVertexCount() {
        return adjacencyList.size();
    }

    /**
     * Get the number of edges in the graph.
     *
     * @return the count of edges.
     */
    public int getEdgeCount() {
        return adjacencyList.values().stream()
                .mapToInt(List::size)
                .sum();
    }

    /**
     * Get all edges in the graph.
     *
     * @return a list of all edges.
     */
    public List<E> getAllEdges() {
        List<E> edges = new ArrayList<>();
        for (List<E> edgeList : adjacencyList.values()) {
            edges.addAll(edgeList);
        }
        return edges;
    }

    /**
     * Check if the graph is empty (contains no vertices).
     *
     * @return true if the graph is empty, otherwise false.
     */
    public boolean isEmpty() {
        return adjacencyList.isEmpty();
    }

    /**
     * Removes a vertex and all its associated edges from the graph.
     *
     * @param vertex the vertex to be removed.
     */
    public void removeVertex(V vertex) {
        adjacencyList.remove(vertex);
        for (List<E> edges : adjacencyList.values()) {
            edges.removeIf(edge -> edge.getTarget().equals(vertex));
        }
    }

    /**
     * Removes an edge from the graph.
     *
     * @param edge the edge to be removed.
     */
    public void removeEdge(E edge) {
        List<E> edges = adjacencyList.get(edge.getSource());
        if (edges != null) {
            edges.remove(edge);
        }
    }

    /**
     * Checks if the graph is directed.
     * Assumes the graph is directed if all edges are considered one-way.
     *
     * @return true if the graph is directed, otherwise false.
     */
    public boolean isDirected() {
        // Assuming all edges in the graph are directed as there is no specific data for undirected edges.
        return true;
    }

    /**
     * Get the in-degree of a vertex (number of incoming edges).
     *
     * @param vertex the vertex whose in-degree is calculated.
     * @return the in-degree of the vertex.
     */
    public int getInDegree(V vertex) {
        int inDegree = 0;
        for (List<E> edges : adjacencyList.values()) {
            for (E edge : edges) {
                if (edge.getTarget().equals(vertex)) {
                    inDegree++;
                }
            }
        }
        return inDegree;
    }

    /**
     * Get the out-degree of a vertex (number of outgoing edges).
     *
     * @param vertex the vertex whose out-degree is calculated.
     * @return the out-degree of the vertex.
     */
    public int getOutDegree(V vertex) {
        return adjacencyList.getOrDefault(vertex, Collections.emptyList()).size();
    }


    /**
     * Removes all vertices and edges from the graph, effectively clearing it.
     */
    public void clear() {
        adjacencyList.clear();
    }
}



