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

public class FlippedRightRotationTest {
    private AbstractFace mockFace;
    private FlippedRightRotation flippedRightRotation;

    @BeforeEach
    public void setUp() {
        mockFace = new HighwayCurveFace();
        flippedRightRotation = new FlippedRightRotation();
    }

    @Test
    void testGetId() {
        assertEquals("FR", flippedRightRotation.getId());
    }

    @Test
    void testRotate() {
        Node initialTop = mockFace.getNode(Side.TOP);
        Node initialBottom = mockFace.getNode(Side.BOTTOM);
        Node initialLeft = mockFace.getNode(Side.LEFT);
        Node initialRight = mockFace.getNode(Side.RIGHT);


        flippedRightRotation.rotate(mockFace);

        assertNotEquals(initialTop, mockFace.getNode(Side.TOP));
        assertNotEquals(initialBottom, mockFace.getNode(Side.BOTTOM));
        assertNotEquals(initialLeft, mockFace.getNode(Side.LEFT));
        assertNotEquals(initialRight, mockFace.getNode(Side.RIGHT));


        assertEquals(Path.ROAD,mockFace.getNode(Side.RIGHT).getConnection(mockFace.getNode(Side.CENTER)).getPathType());
        assertEquals(Path.ROAD,mockFace.getNode(Side.CENTER).getConnection(mockFace.getNode(Side.TOP)).getPathType());

    }
}
