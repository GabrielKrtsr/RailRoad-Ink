package cell;

/**
 * Represents a central cell in a game board.
 * A central cell is initialized with a predefined value and is not movable.
 */
public class CentralCell extends Cell {

    /**
     * Constructs a CentralCell with a fixed value and immovable status.
     */
    public CentralCell() {
        super(3, false);
    }

    /**
     * Returns a string representation of the CentralCell.
     *
     * @return A string indicating this is a central cell.
     */
    @Override
    public String toString() {
        return "Central";
    }

    @Override
    public boolean isCentralCell() {
        return true;
    }
    @Override
    public boolean isBorderCell() {
        return false;
    }
}
