package cell;

/**
 * Represents a classic cell in a game board.
 * A ClassicCell is a standard type of cell with predefined properties.
 */
public class ClassicCell extends Cell {

    /**
     * Constructs a ClassicCell with default properties.
     * It has a fixed value and is not an exit.
     */
    public ClassicCell() {
        super(1, false);
    }

    /**
     * Returns a string representation of the ClassicCell.
     *
     * @return A string indicating this is a classic cell.
     */
    @Override
    public String toString() {
        return "Classic";
    }
    /**
     * Determines if this cell is the central cell of the game board.
     *
     * @return false, as a ClassicCell is not a central cell in the game board.
     */
    @Override
    public boolean isCentralCell() {
        return false;
    }

    /**
     * Determines if the cell is located on the border of a game board.
     *
     * @return false, indicating this cell is not a border cell.
     */
    @Override
    public boolean isBorderCell() {
        return false;
    }
}
