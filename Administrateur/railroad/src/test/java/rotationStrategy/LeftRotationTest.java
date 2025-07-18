package rotationStrategy;

import face.AbstractFace;
import face.HighwayCurveFace;
import face.HighwayFace;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.Path;
import util.util_for_nodes.Node;
import util.util_for_nodes.Side;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class LeftRotationTest {
    private AbstractFace mockFace;
    private LeftRotation leftRotation;

    @BeforeEach
    public void setUp() {
        mockFace = new HighwayFace();
        leftRotation = new LeftRotation();
    }

    @Test
    void testGetId() {
        Assertions.assertEquals("L", leftRotation.getId());
    }

    @Test
    public void testRotation() {
        Node initialTop = mockFace.getNode(Side.TOP);
        Node initialBottom = mockFace.getNode(Side.BOTTOM);
        Node initialLeft = mockFace.getNode(Side.LEFT);
        Node initialRight = mockFace.getNode(Side.RIGHT);

        Node expectedTop = mockFace.getNode(Side.TOP.getRightSide());
        Node expectedBottom = mockFace.getNode(Side.BOTTOM.getRightSide());
        Node expectedLeft = mockFace.getNode(Side.LEFT.getRightSide());
        Node expectedRight = mockFace.getNode(Side.RIGHT.getRightSide());

        leftRotation.rotate(mockFace);

        assertNotEquals(initialTop, mockFace.getNode(Side.TOP));
        assertNotEquals(initialBottom, mockFace.getNode(Side.BOTTOM));
        assertNotEquals(initialLeft, mockFace.getNode(Side.LEFT));
        assertNotEquals(initialRight, mockFace.getNode(Side.RIGHT));

        Assertions.assertEquals(expectedTop, mockFace.getNode(Side.TOP));
        Assertions.assertEquals(expectedBottom, mockFace.getNode(Side.BOTTOM));
        Assertions.assertEquals(expectedLeft, mockFace.getNode(Side.LEFT));
        Assertions.assertEquals(expectedRight, mockFace.getNode(Side.RIGHT));

        Assertions.assertEquals(Path.ROAD,mockFace.getNode(Side.LEFT).getConnection(mockFace.getNode(Side.CENTER)).getPathType());
        Assertions.assertEquals(Path.ROAD,mockFace.getNode(Side.CENTER).getConnection(mockFace.getNode(Side.RIGHT)).getPathType());
    }
}
