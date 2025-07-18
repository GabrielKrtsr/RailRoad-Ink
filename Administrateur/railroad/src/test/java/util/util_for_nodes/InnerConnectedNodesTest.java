package util.util_for_nodes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import util.Path;
import util.util_for_nodes.InnerConnectedNodes;
import util.util_for_nodes.Node;

public class InnerConnectedNodesTest {

    @Test
    void testAddNodeWhenSetIsEmpty() {
        InnerConnectedNodes innerConnectedNodes = new InnerConnectedNodes();
        Node node = new Node();
        assertTrue(innerConnectedNodes.addNode(node), "Should return true as set is empty");
        assertEquals(1, innerConnectedNodes.getSize(), "Size of set should be 1 as we added one node");
    }

    @Test
    void testAddNodeWhenNodeIsConnected() {
        InnerConnectedNodes innerConnectedNodes = new InnerConnectedNodes();
        Node node1 = new Node();
        Node node2 = new Node();
        node1.addConnection(node2, new Connection(Path.ROAD));
        innerConnectedNodes.addNode(node1);

        assertTrue(innerConnectedNodes.addNode(node2), "Should return true as node2 is connected to node in set");
        assertEquals(2, innerConnectedNodes.getSize(), "Size of set should be 2 as we added two nodes");
    }

    @Test
    void testAddNodeWhenNodeIsNotConnected() {
        InnerConnectedNodes innerConnectedNodes = new InnerConnectedNodes();
        Node node1 = new Node();
        Node node2 = new Node();
        innerConnectedNodes.addNode(node1);

        assertFalse(innerConnectedNodes.addNode(node2), "Should return false as node2 is not connected to any node in set");
        assertEquals(1, innerConnectedNodes.getSize(), "Size of set should remain 1 as we could not add node2");
    }

}