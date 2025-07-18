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

    @Override
    public boolean isCentralCell() {
        return false;
    }
    @Override
    public boolean isBorderCell() {
        return false;
    }
}
