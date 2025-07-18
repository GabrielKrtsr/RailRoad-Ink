package rules;

import board.Board;
import board.BoardGame;
import cell.ClassicCell;
import face.AbstractFace;
import cell.Cell;
import face.HighwayFace;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertFalse;


public class PlacementRulesGameTest {

    private PlacementRulesGame placementRules;
    private Board board;
    private AbstractFace face;

    @BeforeEach
    public void setUp() {
        placementRules = PlacementRulesGame.getInstance();
        board = new BoardGame();
        face = new HighwayFace();
    }

    @Test
    public void testSingletonInstance() {
        PlacementRulesGame instance1 = PlacementRulesGame.getInstance();
        PlacementRulesGame instance2 = PlacementRulesGame.getInstance();
        assertSame(instance1, instance2);
    }

    @Test
    public void testCanPlaceFace_ValidPlacement() {
        assertTrue(placementRules.canPlaceFace(0, 1, face, board));
    }


    @Test
    public void testCanPlaceFace_InvalidPlacement() {
        assertFalse(placementRules.canPlaceFace(1, 1, face, board));
        assertFalse(placementRules.canPlaceFace(0, 3, face, board));
    }


    @Test
    public void testIsValidIndex() {
        assertFalse(placementRules.isCompatible(-8,5,face,board));
        assertFalse(placementRules.isCompatible(5,-5,face,board));
        assertFalse(placementRules.isCompatible(17112004,5,face,board));
        assertFalse(placementRules.isCompatible(2,666,face,board));
        board.placeFaceInTheCell(0,1,face);
        assertFalse(placementRules.canPlaceFace(0, 1, face, board));
    }
}
