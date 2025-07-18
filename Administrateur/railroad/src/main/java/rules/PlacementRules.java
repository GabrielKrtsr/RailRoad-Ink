package rules;

import face.AbstractFace;
import board.Board;


/**
 * PlacementRules interface
 */
public interface PlacementRules {

    /**
     * Determines whether a face can be placed at the given position on the board.
     * This method checks if the position is valid and if the face respects the game's placement constraints.
     *
     * @param x the x-coordinate on the board
     * @param y the y-coordinate on the board
     * @param face the face to be placed
     * @param board the game board
     * @return true if the face can be placed, false otherwise
     */
    boolean canPlaceFace(int x, int y, AbstractFace face, Board board);




    /**
     * Checks whether the given face is compatible with its surroundings at the specified position on the board.
     * Compatibility depends on adjacent faces, border constraints, and the game's connection rules.
     *
     * @param x the x-coordinate on the board
     * @param y the y-coordinate on the board
     * @param face the face to check for compatibility
     * @param board the game board
     * @return true if the face is compatible, false otherwise
     */
    boolean isCompatible(int x, int y, AbstractFace face, Board board);
}
