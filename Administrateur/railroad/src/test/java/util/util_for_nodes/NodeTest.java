package util.util_for_nodes;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import util.Path;

public class NodeTest {

    @Test
    public void testIsCentralNodeWhenSetFalse() {
        Node node = new Node();
        node.setCentralNode(false);
        Assertions.assertFalse(node.isCentralNode(), "The node should not be a central node");
    }

    @Test
    public void testIsCentralNodeWhenSetTrue() {
        Node node = new Node();
        node.setCentralNode(true);
        Assertions.assertTrue(node.isCentralNode(), "The node should be a central node");
    }

    @Test
    public void testIsExitNodeWhenSetFalse() {
        Node node = new Node();
        node.setExitNode(false);
        Assertions.assertFalse(node.isExitNode(), "The node should not be an exit node");
    }

    @Test
    public void testIsExitNodeWhenSetTrue() {
        Node node = new Node();
        node.setExitNode(true);
        Assertions.assertTrue(node.isExitNode(), "The node should be an exit node");
    }

    @Test
    public void testAddConnection() {
        Node node1 = new Node();
        Node node2 = new Node();
        Connection connection = new Connection(Path.ROAD);
        node1.addConnection(node2, connection);
        Assertions.assertTrue(node1.isConnected(node2), "Node1 should be connected with Node2");
    }

    @Test
    public void testRemoveConnection() {
        Node node1 = new Node();
        Node node2 = new Node();
        Connection connection = new Connection(Path.ROAD);
        node1.addConnection(node2, connection);
        node1.removeConnection(node2);
        Assertions.assertFalse(node1.isConnected(node2), "Node1 should not be connected with Node2");
    }
}