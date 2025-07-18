package util.util_for_nodes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SideTest {

    @Test
    public void testGetOppositeSideFromTop() {
        Side side = Side.TOP;
        Side expected = Side.BOTTOM;
        Side result = side.getOppositeSide();
        assertEquals(expected, result);
    }

    @Test
    public void testGetOppositeSideFromBottom() {
        Side side = Side.BOTTOM;
        Side expected = Side.TOP;
        Side result = side.getOppositeSide();
        assertEquals(expected, result);
    }

    @Test
    public void testGetOppositeSideFromLeft() {
        Side side = Side.LEFT;
        Side expected = Side.RIGHT;
        Side result = side.getOppositeSide();
        assertEquals(expected, result);
    }

    @Test
    public void testGetOppositeSideFromRight() {
        Side side = Side.RIGHT;
        Side expected = Side.LEFT;
        Side result = side.getOppositeSide();
        assertEquals(expected, result);
    }

    @Test
    public void testGetOppositeSideFromCenter() {
        Side side = Side.CENTER;
        Side expected = Side.CENTER;
        Side result = side.getOppositeSide();
        assertEquals(expected, result);
    }
}