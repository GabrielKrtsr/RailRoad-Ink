package graph;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class GraphTest {

    @Test
    void testGetVertexCountEmptyGraphMutation() {
        Graph<String, Edge<String>> graph = new Graph<>();
        assertNotEquals(1, graph.getVertexCount());
    }

    @Test
    void testGetVertexCountSingleVertexMutation() {
        Graph<String, Edge<String>> graph = new Graph<>();
        graph.addVertex("A");
        assertNotEquals(0, graph.getVertexCount());
    }

    @Test
    void testGetVertexCountTwoVerticesMutation() {
        Graph<String, Edge<String>> graph = new Graph<>();
        graph.addVertex("A");
        graph.addVertex("B");
        assertNotEquals(1, graph.getVertexCount());
    }

    @Test
    void testGetVertexCountTwoVerticesAndEdgeMutation() {
        Graph<String, Edge<String>> graph = new Graph<>();
        Edge<String> edge = new Edge<>("A", "B", 1.0);
        graph.addEdge(edge);
        assertNotEquals(1, graph.getVertexCount());
    }

    @Test
    void testGetVertexCountSingleVertexAndEdgeMutation() {
        Graph<String, Edge<String>> graph = new Graph<>();
        Edge<String> edge = new Edge<>("A", "A", 1.0);
        graph.addEdge(edge);
        assertNotEquals(2, graph.getVertexCount());
    }

    @Test
    void testGetVertexCountAfterVertexRemovalMutation() {
        Graph<String, Edge<String>> graph = new Graph<>();
        graph.addVertex("A");
        graph.addVertex("B");
        graph.removeVertex("A");
        assertNotEquals(2, graph.getVertexCount());
    }

    @Test
    void testAddVertexMutation() {
        Graph<String, Edge<String>> graph = new Graph<>();
        graph.addVertex("A");
        assertNotEquals(0, graph.getVertexCount());
        graph.addVertex("B");
        assertNotEquals(1, graph.getVertexCount());
    }

    @Test
    void testAddEdgeMutation() {
        Graph<String, Edge<String>> graph = new Graph<>();
        graph.addVertex("A");
        graph.addVertex("B");
        Edge<String> edge = new Edge<>("A", "B", 1.0);
        graph.addEdge(edge);
        assertNotEquals(0, graph.getEdgeCount());
    }

    @Test
    void testRemoveEdgeMutation() {
        Graph<String, Edge<String>> graph = new Graph<>();
        graph.addVertex("A");
        graph.addVertex("B");
        Edge<String> edge = new Edge<>("A", "B", 1.0);
        graph.addEdge(edge);
        graph.removeEdge(edge);
        assertNotEquals(1, graph.getEdgeCount());
    }

    @Test
    void testRemoveVertexWithEdgesMutation() {
        Graph<String, Edge<String>> graph = new Graph<>();
        graph.addVertex("A");
        graph.addVertex("B");
        Edge<String> edge = new Edge<>("A", "B", 1.0);
        graph.addEdge(edge);
        graph.removeVertex("A");
        assertNotEquals(1, graph.getEdgeCount());
        assertNotEquals(2, graph.getVertexCount());
    }

    @Test
    void testClearGraphMutation() {
        Graph<String, Edge<String>> graph = new Graph<>();
        graph.addVertex("A");
        graph.addVertex("B");
        Edge<String> edge = new Edge<>("A", "B", 1.0);
        graph.addEdge(edge);
        graph.clear();
        assertNotEquals(1, graph.getVertexCount());
        assertNotEquals(1, graph.getEdgeCount());
    }

    @Test
    void testAddVertex() {
        Graph<String, Edge<String>> graph = new Graph<>();
        graph.addVertex("A");
        assertTrue(graph.containsVertex("A"));
    }

    @Test
    void testAddEdge() {
        Graph<String, Edge<String>> graph = new Graph<>();
        Edge<String> edge = new Edge<>("A", "B", 2.0);
        graph.addEdge(edge);
        assertTrue(graph.containsEdge(edge));
        assertTrue(graph.containsVertex("A"));
        assertTrue(graph.containsVertex("B"));
    }

    @Test
    void testGetEdges() {
        Graph<String, Edge<String>> graph = new Graph<>();
        Edge<String> edge = new Edge<>("A", "B", 2.0);
        graph.addEdge(edge);
        List<Edge<String>> edges = graph.getEdges("A");
        assertEquals(1, edges.size());
        assertTrue(edges.contains(edge));
    }

    @Test
    void testGetVertices() {
        Graph<String, Edge<String>> graph = new Graph<>();
        graph.addVertex("A");
        graph.addVertex("B");
        Set<String> vertices = graph.getVertices();
        assertEquals(2, vertices.size());
        assertTrue(vertices.contains("A"));
        assertTrue(vertices.contains("B"));
    }

    @Test
    void testContainsVertex() {
        Graph<String, Edge<String>> graph = new Graph<>();
        graph.addVertex("A");
        assertTrue(graph.containsVertex("A"));
        assertFalse(graph.containsVertex("B"));
    }

    @Test
    void testContainsEdge() {
        Graph<String, Edge<String>> graph = new Graph<>();
        Edge<String> edge = new Edge<>("A", "B", 2.0);
        graph.addEdge(edge);
        assertTrue(graph.containsEdge(edge));
    }

    @Test
    void testGetOutgoingEdges() {
        Graph<String, Edge<String>> graph = new Graph<>();
        Edge<String> edge = new Edge<>("A", "B", 2.0);
        graph.addEdge(edge);
        List<Edge<String>> edges = graph.getOutgoingEdges("A");
        assertEquals(1, edges.size());
        assertEquals(edge, edges.get(0));
    }

    @Test
    void testGetTarget() {
        Graph<String, Edge<String>> graph = new Graph<>();
        Edge<String> edge = new Edge<>("A", "B", 2.0);
        assertEquals("B", graph.getTarget(edge));
    }

    @Test
    void testGetEdgeWeight() {
        Graph<String, Edge<String>> graph = new Graph<>();
        Edge<String> edge = new Edge<>("A", "B", 2.5);
        assertEquals(2.5, graph.getEdgeWeight(edge));
    }

    @Test
    void testGetVertexCount() {
        Graph<String, Edge<String>> graph = new Graph<>();
        graph.addVertex("A");
        graph.addVertex("B");
        assertEquals(2, graph.getVertexCount());
    }

    @Test
    void testGetEdgeCount() {
        Graph<String, Edge<String>> graph = new Graph<>();
        Edge<String> edge1 = new Edge<>("A", "B", 2.0);
        Edge<String> edge2 = new Edge<>("B", "C", 3.0);
        graph.addEdge(edge1);
        graph.addEdge(edge2);
        assertEquals(2, graph.getEdgeCount());
    }

    @Test
    void testGetAllEdges() {
        Graph<String, Edge<String>> graph = new Graph<>();
        Edge<String> edge1 = new Edge<>("A", "B", 2.0);
        Edge<String> edge2 = new Edge<>("B", "C", 3.0);
        graph.addEdge(edge1);
        graph.addEdge(edge2);
        List<Edge<String>> edges = graph.getAllEdges();
        assertEquals(2, edges.size());
        assertTrue(edges.contains(edge1));
        assertTrue(edges.contains(edge2));
    }

    @Test
    void testIsEmpty() {
        Graph<String, Edge<String>> graph = new Graph<>();
        assertTrue(graph.isEmpty());
        graph.addVertex("A");
        assertFalse(graph.isEmpty());
    }

    @Test
    void testRemoveVertex() {
        Graph<String, Edge<String>> graph = new Graph<>();
        graph.addVertex("A");
        graph.removeVertex("A");
        assertFalse(graph.containsVertex("A"));
    }

    @Test
    void testRemoveEdge() {
        Graph<String, Edge<String>> graph = new Graph<>();
        Edge<String> edge = new Edge<>("A", "B", 1.0);
        graph.addEdge(edge);
        graph.removeEdge(edge);
        assertFalse(graph.containsEdge(edge));
    }

    @Test
    void testGetInDegree() {
        Graph<String, Edge<String>> graph = new Graph<>();
        Edge<String> edge1 = new Edge<>("A", "B", 1.0);
        Edge<String> edge2 = new Edge<>("C", "B", 1.0);
        graph.addEdge(edge1);
        graph.addEdge(edge2);
        assertEquals(2, graph.getInDegree("B"));
    }

    @Test
    void testGetOutDegree() {
        Graph<String, Edge<String>> graph = new Graph<>();
        Edge<String> edge1 = new Edge<>("A", "B", 1.0);
        Edge<String> edge2 = new Edge<>("A", "C", 1.0);
        graph.addEdge(edge1);
        graph.addEdge(edge2);
        assertEquals(2, graph.getOutDegree("A"));
    }

    @Test
    void testClear() {
        Graph<String, Edge<String>> graph = new Graph<>();
        Edge<String> edge = new Edge<>("A", "B", 1.0);
        graph.addEdge(edge);
        graph.clear();
        assertTrue(graph.isEmpty());
    }
}