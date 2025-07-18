package rotationStrategy;

import face.AbstractFace;
import face.HighwayCurveFace;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.Path;
import util.util_for_nodes.Node;
import util.util_for_nodes.Side;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class FlippedRotationTest {
    private AbstractFace mockFace;
    private FlippedRotation flippedRotation;

    @BeforeEach
    public void setUp() {
        mockFace = new HighwayCurveFace();
        flippedRotation = new FlippedRotation();
    }

    @Test
    void testGetId() {
        Assertions.assertEquals("F", flippedRotation.getId());
    }

    @Test
    public void testRotation() {
        Node initialLeft = mockFace.getNode(Side.LEFT);
        Node initialRight = mockFace.getNode(Side.RIGHT);

        Node expectedTop = mockFace.getNode(Side.TOP.getFlippedSide());
        Node expectedBottom = mockFace.getNode(Side.BOTTOM.getFlippedSide());
        Node expectedLeft = mockFace.getNode(Side.LEFT.getFlippedSide());
        Node expectedRight = mockFace.getNode(Side.RIGHT.getFlippedSide());

        flippedRotation.rotate(mockFace);


        assertNotEquals(initialLeft, mockFace.getNode(Side.LEFT));
        assertNotEquals(initialRight, mockFace.getNode(Side.RIGHT));

        Assertions.assertEquals(expectedTop, mockFace.getNode(Side.TOP));
        Assertions.assertEquals(expectedBottom, mockFace.getNode(Side.BOTTOM));
        Assertions.assertEquals(expectedLeft, mockFace.getNode(Side.LEFT));
        Assertions.assertEquals(expectedRight, mockFace.getNode(Side.RIGHT));

        Assertions.assertEquals(Path.ROAD,mockFace.getNode(Side.TOP).getConnection(mockFace.getNode(Side.CENTER)).getPathType());
        Assertions.assertEquals(Path.ROAD,mockFace.getNode(Side.CENTER).getConnection(mockFace.getNode(Side.LEFT)).getPathType());
    }
}
