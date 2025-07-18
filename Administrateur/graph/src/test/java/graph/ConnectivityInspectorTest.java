package graph;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ConnectivityInspectorTest {

    @Test
    void isConnected_EmptyGraph_ReturnsTrue() {
        Graph<String, Edge<String>> graph = new Graph<>();
        ConnectivityInspector<String, Edge<String>> inspector = new ConnectivityInspector<>(graph);

        assertTrue(inspector.isConnected());
    }

    @Test
    void isConnected_DisconnectedGraph_ReturnsFalse() {
        Graph<String, Edge<String>> graph = new Graph<>();
        graph.addVertex("Vertex 1");
        graph.addVertex("Vertex 2");
        ConnectivityInspector<String, Edge<String>> inspector = new ConnectivityInspector<>(graph);

        assertFalse(inspector.isConnected());
    }

    @Test
    void isConnected_FullyConnectedGraph_ReturnsTrue() {
        Graph<String, Edge<String>> graph = new Graph<>();
        graph.addVertex("Vertex 1");
        graph.addVertex("Vertex 2");
        graph.addEdge(new Edge<>("Vertex 1", "Vertex 2",1));
        ConnectivityInspector<String, Edge<String>> inspector = new ConnectivityInspector<>(graph);

        assertTrue(inspector.isConnected());
    }

    @Test
    void isConnected_EmptyGraph_IsTriviallyConnected() {
        ConnectivityInspector<String, Edge<String>> inspector = new ConnectivityInspector<>(new Graph<>());

        assertTrue(inspector.isConnected());
    }

    @Test
    void isConnected_SingleVertexGraph_ReturnsTrue() {
        Graph<String, Edge<String>> graph = new Graph<>();
        graph.addVertex("A");
        ConnectivityInspector<String, Edge<String>> inspector = new ConnectivityInspector<>(graph);

        assertTrue(inspector.isConnected());
    }

    @Test
    void isConnected_GraphWithDisconnectedVertices_ReturnsFalse() {
        Graph<String, Edge<String>> graph = new Graph<>();
        graph.addVertex("A");
        graph.addVertex("B");
        ConnectivityInspector<String, Edge<String>> inspector = new ConnectivityInspector<>(graph);

        assertFalse(inspector.isConnected());
    }

    @Test
    void areConnected_ExistingPath_ReturnsTrue() {
        Graph<String, Edge<String>> graph = new Graph<>();
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addEdge(new Edge<>("A", "B", 1));
        ConnectivityInspector<String, Edge<String>> inspector = new ConnectivityInspector<>(graph);

        assertTrue(inspector.areConnected("A", "B"));
    }

    @Test
    void areConnected_NoPath_ReturnsFalse() {
        Graph<String, Edge<String>> graph = new Graph<>();
        graph.addVertex("A");
        graph.addVertex("B");
        ConnectivityInspector<String, Edge<String>> inspector = new ConnectivityInspector<>(graph);

        assertFalse(inspector.areConnected("A", "B"));
    }

    @Test
    void areConnected_NonExistentVertices_ReturnsFalse() {
        Graph<String, Edge<String>> graph = new Graph<>();
        ConnectivityInspector<String, Edge<String>> inspector = new ConnectivityInspector<>(graph);

        assertFalse(inspector.areConnected("A", "B"));
    }

    @Test
    void connectedSets_MultipleDisconnectedVertices_ReturnsSeparateComponents() {
        Graph<String, Edge<String>> graph = new Graph<>();
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
        ConnectivityInspector<String, Edge<String>> inspector = new ConnectivityInspector<>(graph);

        Set<Set<String>> connectedSets = inspector.connectedSets();

        assertEquals(3, connectedSets.size());
        assertTrue(connectedSets.contains(Set.of("A")));
        assertTrue(connectedSets.contains(Set.of("B")));
        assertTrue(connectedSets.contains(Set.of("C")));
    }

    @Test
    void connectedSets_SingleConnectedComponent_ReturnsOneComponent() {
        Graph<String, Edge<String>> graph = new Graph<>();
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
        graph.addEdge(new Edge<>("A", "B", 1));
        graph.addEdge(new Edge<>("B", "C", 1));
        ConnectivityInspector<String, Edge<String>> inspector = new ConnectivityInspector<>(graph);

        Set<Set<String>> connectedSets = inspector.connectedSets();

        assertEquals(1, connectedSets.size());
        assertTrue(connectedSets.contains(Set.of("A", "B", "C")));
    }

    @Test
    void connectedSets_MixedComponents_ReturnsCorrectComponents() {
        Graph<String, Edge<String>> graph = new Graph<>();
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
        graph.addVertex("D");
        graph.addEdge(new Edge<>("A", "B", 1));
        graph.addEdge(new Edge<>("C", "D", 1));
        ConnectivityInspector<String, Edge<String>> inspector = new ConnectivityInspector<>(graph);

        Set<Set<String>> connectedSets = inspector.connectedSets();

        assertEquals(2, connectedSets.size());
        assertTrue(connectedSets.contains(Set.of("A", "B")));
        assertTrue(connectedSets.contains(Set.of("C", "D")));
    }
    
}