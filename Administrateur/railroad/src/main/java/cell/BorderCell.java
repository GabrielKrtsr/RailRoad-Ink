package cell;

import util.Path;
import util.Tuple;
import util.util_for_nodes.Side;

/**
 * Represents a specialized cell on a game board that includes a border.
 * A BorderCell is associated with a specific side and path type.
 */
public class BorderCell extends Cell {

    private Tuple<Side, Path> border;

    /**
     * Constructs a BorderCell with a specified coordinate, path type, and exit status.
     *
     * @param side   the side associated with the border cell
     * @param p      the path type associated with the border cell
     * @param isExit whether this border cell acts as an exit
     */
    public BorderCell(Side side, Path p, boolean isExit) {
        super(1, isExit);
        this.border = new Tuple<>(side, p);
    }

    /**
     * Retrieves the border information of this cell.
     *
     * @return the Tuple representing the border of the cell
     */
    public Tuple<Side, Path> getBorder() {
        return this.border;
    }

    /**
     * Returns a string representation of the BorderCell.
     *
     * @return A string indicating this is a border cell.
     */
    @Override
    public String toString() {
        return "Border";
    }
    /**
     * Determines if this cell is a central cell.
     *
     * @return false, as this cell is not a central cell.
     */
    @Override
    public boolean isCentralCell() {
        return false;
    }

    /**
     * Determines if the current cell is a border cell.
     *
     * @return true if the cell is a border cell, false otherwise
     */
    @Override
    public boolean isBorderCell() {
        return true;
    }
}