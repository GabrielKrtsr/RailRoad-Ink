package face;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import rotationStrategy.LeftRotation;
import util.Path;
import util.Type;
import util.util_for_nodes.Side;

import static org.junit.Assert.assertEquals;

public class StationCurveFaceTest extends AbstractFaceTest {

    @BeforeEach
    public void setUp() {
        abstractFace = new StationCurveFace();
    }

    @Override
    protected Type type() {
        return Type.CLASSIC;
    }

    @Override
    protected String id() {
        return "Sc";
    }

    @Test
    public void testRotate() {
        LeftRotation leftRotation = new LeftRotation();
        abstractFace.rotate(leftRotation);
        assertEquals(Path.ROAD,abstractFace.getConnectionBetweenTwoSides(Side.LEFT,Side.CENTER).getPathType());
        assertEquals(Path.RAIL,abstractFace.getConnectionBetweenTwoSides(Side.TOP,Side.CENTER).getPathType());
    }

    @Test
    public void testInitialConfiguration() {
        assertEquals(Path.ROAD,abstractFace.getConnectionBetweenTwoSides(Side.TOP,Side.CENTER).getPathType());
        assertEquals(Path.RAIL,abstractFace.getConnectionBetweenTwoSides(Side.RIGHT,Side.CENTER).getPathType());
    }
}
