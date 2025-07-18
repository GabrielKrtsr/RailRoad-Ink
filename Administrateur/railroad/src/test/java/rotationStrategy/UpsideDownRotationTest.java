package rotationStrategy;

import face.AbstractFace;
import face.RailCurveFace;
import face.StationFace;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.Path;
import util.util_for_nodes.Node;
import util.util_for_nodes.Side;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class UpsideDownRotationTest {
    private AbstractFace mockFace;
    private UpsideDownRotation upsideDownRotation;

    @BeforeEach
    public void setUp() {
        mockFace = new RailCurveFace();
        upsideDownRotation = new UpsideDownRotation();
    }

    @Test
    void testGetId() {
        assertEquals("U", upsideDownRotation.getId());
    }

    @Test
    public void testRotation() {
        Node initialTop = mockFace.getNode(Side.TOP);
        Node initialBottom = mockFace.getNode(Side.BOTTOM);
        Node initialLeft = mockFace.getNode(Side.LEFT);
        Node initialRight = mockFace.getNode(Side.RIGHT);

        Node expectedTop = mockFace.getNode(Side.TOP.getOppositeSide());
        Node expectedBottom = mockFace.getNode(Side.BOTTOM.getOppositeSide());
        Node expectedLeft = mockFace.getNode(Side.LEFT.getOppositeSide());
        Node expectedRight = mockFace.getNode(Side.RIGHT.getOppositeSide());

        upsideDownRotation.rotate(mockFace);

        assertNotEquals(initialTop, mockFace.getNode(Side.TOP));
        assertNotEquals(initialBottom, mockFace.getNode(Side.BOTTOM));
        assertNotEquals(initialLeft, mockFace.getNode(Side.LEFT));
        assertNotEquals(initialRight, mockFace.getNode(Side.RIGHT));

        assertEquals(expectedTop, mockFace.getNode(Side.TOP));
        assertEquals(expectedBottom, mockFace.getNode(Side.BOTTOM));
        assertEquals(expectedLeft, mockFace.getNode(Side.LEFT));
        assertEquals(expectedRight, mockFace.getNode(Side.RIGHT));

        assertEquals(Path.RAIL,mockFace.getNode(Side.BOTTOM).getConnection(mockFace.getNode(Side.CENTER)).getPathType());
        assertEquals(Path.RAIL,mockFace.getNode(Side.CENTER).getConnection(mockFace.getNode(Side.LEFT)).getPathType());
    }
}
