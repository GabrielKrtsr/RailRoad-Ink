package graph;


import java.util.*;

/**
 * A class that computes the shortest paths in a weighted directed graph
 * using Dijkstra's algorithm. This implementation is generic and can be
 * used with vertices and edges of different types.
 *
 * @param <V> the type of vertices in the graph
 * @param <E> the type of edges in the graph (must extend {@code Edge<V>})
 */
public class DijkstraShortestPath<V, E extends Edge<V>> {

    /**
     * Represents the weighted directed graph utilized by the Dijkstra algorithm to compute shortest paths.
     * This generic variable stores the graph structure, containing vertices of type {@code V} and edges of type {@code E}.
     * The graph defines the relationships between vertices through its adjacency list and provides methods to access
     * vertices, edges, and associated properties such as edge weights.
     *
     * @param <V> the type of vertices in the graph
     * @param <E> the type of edges in the graph, which must extend {@code Edge<V>}
     */
    private final Graph<V, E> graph;

    /**
     * Constructs an instance of the DijkstraShortestPath algorithm for the provided graph.
     *
     * @param graph the graph on which the shortest path algorithms will operate;
     *              must be a weighted, directed graph where edges have defined weights.
     */
    public DijkstraShortestPath(Graph<V, E> graph) {
        this.graph = graph;
    }

    /**
     * Calculates the shortest path distances from the specified source vertex
     * to all other vertices in the graph using Dijkstra's algorithm. The method
     * returns a map where the keys are vertices and the values are the shortest
     * path distances from the source to that vertex.
     *
     * @param source the source vertex from which shortest path distances are calculated
     * @return a map containing vertices as keys and their shortest path distances
     *         from the source vertex as values
     */
    public Map<V, Double> getShortestPath(V source) {
        if(!graph.containsVertex(source)){
            throw new IllegalArgumentException();
        }
        Map<V, Double> distances = new HashMap<>();
        Map<V, V> predecessors = new HashMap<>();
        PriorityQueue<V> priorityQueue = new PriorityQueue<>(Comparator.comparing(distances::get));

        for (V vertex : graph.getVertices()) {
            distances.put(vertex, Double.MAX_VALUE);
        }
        distances.put(source, 0.0);

        priorityQueue.add(source);

        while (!priorityQueue.isEmpty()) {
            V current = priorityQueue.poll();

            for (E edge : graph.getOutgoingEdges(current)) {
                V neighbor = graph.getTarget(edge);
                double weight = graph.getEdgeWeight(edge);

                double newDistance = distances.get(current) + weight;
                if (newDistance < distances.get(neighbor)) {
                    distances.put(neighbor, newDistance);
                    predecessors.put(neighbor, current);

                    priorityQueue.remove(neighbor);
                    priorityQueue.add(neighbor);
                }
            }
        }

        return distances;
    }

    /**
     * Computes and retrieves the shortest path from a source vertex to a target vertex in the graph.
     * The path is determined based on the shortest distance calculated using Dijkstra's algorithm.
     *
     * @param source the vertex from which the path originates
     * @param target the vertex to which the path is determined
     * @return a list of vertices representing the shortest path from the source to the target,
     *         or an empty list if no path exists
     */
    public List<V> getPath(V source, V target) {
        if(!graph.containsVertex(source) || !graph.containsVertex(target) ){
            throw new IllegalArgumentException();
        }
        Map<V, Double> distances = getShortestPath(source);
        Map<V, V> predecessors = new HashMap<>();
        for (V vertex : distances.keySet()) {
            for (E edge : graph.getOutgoingEdges(vertex)) {
                V neighbor = graph.getTarget(edge);
                double weight = graph.getEdgeWeight(edge);

                if (distances.get(neighbor) == distances.get(vertex) + weight) {
                    predecessors.put(neighbor, vertex);
                }
            }
        }

        List<V> path = new LinkedList<>();
        V current = target;

        while (current != null) {
            path.add(0, current);
            current = predecessors.get(current);
        }

        return path.contains(source) ? path : Collections.emptyList();
    }

}