package graph;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.*;

class DijkstraShortestPathTest {

    @Test
    void testGetShortestPath() {
        Graph<String, Edge<String>> graph = new Graph<>();
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
        graph.addEdge(new Edge<>("A", "B", 10));
        graph.addEdge(new Edge<>("B", "C", 10));
        graph.addEdge(new Edge<>("A", "C", 20));

        DijkstraShortestPath<String, Edge<String>> dijkstraShortestPath = new DijkstraShortestPath<>(graph);

        Map<String, Double> expectedShortestPaths = new HashMap<>();
        expectedShortestPaths.put("A", 0.0);
        expectedShortestPaths.put("B", 10.0);
        expectedShortestPaths.put("C", 20.0);

        Map<String, Double> actualShortestPaths = dijkstraShortestPath.getShortestPath("A");

        Assertions.assertEquals(expectedShortestPaths, actualShortestPaths);
    }

    @Test
    void testGetPath() {
        Graph<String, Edge<String>> graph = new Graph<>();
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
        graph.addEdge(new Edge<>("A", "B", 10));
        graph.addEdge(new Edge<>("B", "C", 10));
        graph.addEdge(new Edge<>("A", "C", 20));

        DijkstraShortestPath<String, Edge<String>> dijkstraShortestPath = new DijkstraShortestPath<>(graph);

        List<String> expectedPath = Arrays.asList("A", "B", "C");

        List<String> actualPath = dijkstraShortestPath.getPath("A", "C");

        Assertions.assertEquals(expectedPath, actualPath);
    }




    @Test
    void testMutationForNonExistentSourceVertex() {
        Graph<String, Edge<String>> graph = new Graph<>();
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addEdge(new Edge<>("A", "B", 10));

        DijkstraShortestPath<String, Edge<String>> dijkstraShortestPath = new DijkstraShortestPath<>(graph);

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            dijkstraShortestPath.getShortestPath("Z");
        });
    }

    @Test
    void testMutationForDisconnectedGraph() {
        Graph<String, Edge<String>> graph = new Graph<>();
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C"); // Disconnected vertex
        graph.addEdge(new Edge<>("A", "B", 10));

        DijkstraShortestPath<String, Edge<String>> dijkstraShortestPath = new DijkstraShortestPath<>(graph);

        Map<String, Double> shortestPaths = dijkstraShortestPath.getShortestPath("A");

        Assertions.assertEquals(Double.MAX_VALUE, shortestPaths.get("C"), "Disconnected vertex should have MAX_VALUE as distance");
    }

    @Test
    void testMutationForCyclicGraph() {
        Graph<String, Edge<String>> graph = new Graph<>();
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addEdge(new Edge<>("A", "B", 10));
        graph.addEdge(new Edge<>("B", "A", 5));

        DijkstraShortestPath<String, Edge<String>> dijkstraShortestPath = new DijkstraShortestPath<>(graph);

        Map<String, Double> shortestPaths = dijkstraShortestPath.getShortestPath("A");

        Assertions.assertEquals(0.0, shortestPaths.get("A"), "Distance to itself should be 0");
        Assertions.assertEquals(10.0, shortestPaths.get("B"), "Distance from A to B should be correct");
    }

    @Test
    void testMutationForPathBacktracking() {
        Graph<String, Edge<String>> graph = new Graph<>();
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
        graph.addEdge(new Edge<>("A", "B", 5));
        graph.addEdge(new Edge<>("B", "C", 15));
        graph.addEdge(new Edge<>("A", "C", 50));

        DijkstraShortestPath<String, Edge<String>> dijkstraShortestPath = new DijkstraShortestPath<>(graph);

        List<String> expectedPath = Arrays.asList("A", "B", "C");
        List<String> actualPath = dijkstraShortestPath.getPath("A", "C");

        Assertions.assertEquals(expectedPath, actualPath, "Path should backtrack through optimal route");
    }

    @Test
    void testMutationForEmptyGraph() {
        Graph<String, Edge<String>> graph = new Graph<>();
        DijkstraShortestPath<String, Edge<String>> dijkstraShortestPath = new DijkstraShortestPath<>(graph);

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            dijkstraShortestPath.getShortestPath("Z");
        });
    }

    @Test
    public void testMutationForGraphWithSelfLoop() {
        Graph<String, Edge<String>> graph = new Graph<>();
        graph.addVertex("A");
        graph.addEdge(new Edge<>("A", "A", 5));

        DijkstraShortestPath<String, Edge<String>> dijkstraShortestPath = new DijkstraShortestPath<>(graph);

        Map<String, Double> shortestPaths = dijkstraShortestPath.getShortestPath("A");

        Assertions.assertEquals(0.0, shortestPaths.get("A"), "Distance to itself should be 0 despite self-loop");
    }
}