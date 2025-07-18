package rotationStrategy;

import face.RailCurveFace;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import util.Path;
import util.Type;
import util.util_for_nodes.Node;
import util.util_for_nodes.Side;
import face.AbstractFace;

class FlippedLeftRotationTest {

    private FlippedLeftRotation rotationStrategy;
    private AbstractFace mockFace;

    @BeforeEach
    void setUp() {
        rotationStrategy = new FlippedLeftRotation();
        mockFace = new RailCurveFace();
    }


    @Test
    void testGetId() {
        assertEquals("FL", rotationStrategy.getId());
    }

    @Test
    void testRotate() {
        Node initialTop = mockFace.getNode(Side.TOP);
        Node initialBottom = mockFace.getNode(Side.BOTTOM);
        Node initialLeft = mockFace.getNode(Side.LEFT);
        Node initialRight = mockFace.getNode(Side.RIGHT);


        rotationStrategy.rotate(mockFace);

        assertNotEquals(initialTop, mockFace.getNode(Side.TOP));
        assertNotEquals(initialBottom, mockFace.getNode(Side.BOTTOM));
        assertNotEquals(initialLeft, mockFace.getNode(Side.LEFT));
        assertNotEquals(initialRight, mockFace.getNode(Side.RIGHT));


        assertEquals(Path.RAIL,mockFace.getNode(Side.LEFT).getConnection(mockFace.getNode(Side.CENTER)).getPathType());
        assertEquals(Path.RAIL,mockFace.getNode(Side.CENTER).getConnection(mockFace.getNode(Side.BOTTOM)).getPathType());

    }


}
