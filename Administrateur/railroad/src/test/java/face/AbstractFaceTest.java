package face;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.*;
import util.util_for_nodes.Connection;
import util.util_for_nodes.Node;
import util.util_for_nodes.Side;

import static org.junit.jupiter.api.Assertions.*;


public abstract class AbstractFaceTest {

    protected AbstractFace abstractFace;

    @BeforeEach
    public abstract void setUp();


    @Test
    public void testConnectNodes() {

        Side side1 = Side.TOP;
        Side side2 = Side.BOTTOM;
        Path path = Path.ROAD;

        Node expectedNode1 = abstractFace.getNode(side1);
        Node expectedNode2 = abstractFace.getNode(side2);


        abstractFace.connectNodes(side1, side2, path);

        assertTrue(expectedNode1.isConnected(expectedNode2),
                "Node1 should be connected to Node2");
        assertTrue(expectedNode2.isConnected(expectedNode1),
                "Node2 should be connected to Node1");

        Tuple<Node, Connection> connectionFromNode1 =
                expectedNode1.getConnections().stream()
                        .filter(tuple -> tuple.getType1().equals(expectedNode2))
                        .findFirst()
                        .orElse(null);

        Tuple<Node, Connection> connectionFromNode2 =
                expectedNode2.getConnections().stream()
                        .filter(tuple -> tuple.getType1().equals(expectedNode1))
                        .findFirst()
                        .orElse(null);

        assertNotNull(connectionFromNode1, "Connection from Node1 to Node2 should exist");
        assertNotNull(connectionFromNode2, "Connection from Node2 to Node1 should exist");

        assertEquals(path, connectionFromNode1.getType2().getPathType(),
                "Path type from Node1 to Node2 should match the provided path");
        assertEquals(path, connectionFromNode2.getType2().getPathType(),
                "Path type from Node2 to Node1 should match the provided path");
    }

    @Test
    public void testGetType(){
        assertEquals(type(),abstractFace.getType());
    }

    @Test
    public void testGetId(){
        assertEquals(id(),abstractFace.getId());
    }

    protected abstract Type type();

    protected abstract String id();

    @Test
    public abstract  void testRotate();
}