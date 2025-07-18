package rotationStrategy;

import face.AbstractFace;
import face.HighwayCurveFace;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.Path;
import util.util_for_nodes.Node;
import util.util_for_nodes.Side;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class RightRotationTest {
    private AbstractFace mockFace;
    private RightRotation rightRotation;

    @BeforeEach
    public void setUp() {
        mockFace = new HighwayCurveFace();
        rightRotation = new RightRotation();
    }

    @Test
    void testGetId() {
        assertEquals("R", rightRotation.getId());
    }

    @Test
    public void testRotation() {
        Node initialTop = mockFace.getNode(Side.TOP);
        Node initialBottom = mockFace.getNode(Side.BOTTOM);
        Node initialLeft = mockFace.getNode(Side.LEFT);
        Node initialRight = mockFace.getNode(Side.RIGHT);

        Node expectedTop = mockFace.getNode(Side.TOP.getLeftSide());
        Node expectedBottom = mockFace.getNode(Side.BOTTOM.getLeftSide());
        Node expectedLeft = mockFace.getNode(Side.LEFT.getLeftSide());
        Node expectedRight = mockFace.getNode(Side.RIGHT.getLeftSide());

        rightRotation.rotate(mockFace);

        assertNotEquals(initialTop, mockFace.getNode(Side.TOP));
        assertNotEquals(initialBottom, mockFace.getNode(Side.BOTTOM));
        assertNotEquals(initialLeft, mockFace.getNode(Side.LEFT));
        assertNotEquals(initialRight, mockFace.getNode(Side.RIGHT));

        assertEquals(expectedTop, mockFace.getNode(Side.TOP));
        assertEquals(expectedBottom, mockFace.getNode(Side.BOTTOM));
        assertEquals(expectedLeft, mockFace.getNode(Side.LEFT));
        assertEquals(expectedRight, mockFace.getNode(Side.RIGHT));

        assertEquals(Path.ROAD,mockFace.getNode(Side.RIGHT).getConnection(mockFace.getNode(Side.CENTER)).getPathType());
        assertEquals(Path.ROAD,mockFace.getNode(Side.CENTER).getConnection(mockFace.getNode(Side.BOTTOM)).getPathType());
    }
}
