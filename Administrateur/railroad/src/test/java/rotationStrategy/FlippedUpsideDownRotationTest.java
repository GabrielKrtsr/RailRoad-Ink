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

public class FlippedUpsideDownRotationTest {
    private AbstractFace mockFace;
    private FlippedUpsideDownRotation flippedUpsideDownRotation;

    @BeforeEach
    public void setUp() {
        mockFace = new HighwayCurveFace();
        flippedUpsideDownRotation = new FlippedUpsideDownRotation();
    }

    @Test
    void testGetId() {
        assertEquals("FU", flippedUpsideDownRotation.getId());
    }

    @Test
    void testRotate() {
        Node initialTop = mockFace.getNode(Side.TOP);
        Node initialBottom = mockFace.getNode(Side.BOTTOM);



       flippedUpsideDownRotation.rotate(mockFace);

        assertNotEquals(initialTop, mockFace.getNode(Side.TOP));
        assertNotEquals(initialBottom, mockFace.getNode(Side.BOTTOM));


        assertEquals(Path.ROAD,mockFace.getNode(Side.RIGHT).getConnection(mockFace.getNode(Side.CENTER)).getPathType());
        assertEquals(Path.ROAD,mockFace.getNode(Side.CENTER).getConnection(mockFace.getNode(Side.BOTTOM)).getPathType());

    }
}
