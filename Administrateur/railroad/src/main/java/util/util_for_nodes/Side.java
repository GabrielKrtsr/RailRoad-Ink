package util.util_for_nodes;

/**
 * Represents the cardinal sides (TOP, BOTTOM, RIGHT, LEFT) and CENTER as an enum.
 * Provides utility methods to get different relations like the opposite, left,
 * right, or flipped side relative to a given side.
 */
public enum Side {
    TOP,BOTTOM,RIGHT,LEFT,CENTER;

    /**
     * Gets the opposite side from the given
     * @return the opposite side
     */
    public Side getOppositeSide(){
        if(this.equals(Side.TOP)) return Side.BOTTOM;
        else if (this.equals(Side.BOTTOM)) return Side.TOP;
        else if (this.equals(Side.LEFT)) return Side.RIGHT;
        else if (this.equals(Side.RIGHT)) return Side.LEFT;
        else return Side.CENTER;
    }

    /**
     * Gets the left side from the current side
     *
     * @return the left side
     */
    public Side getLeftSide() {
        if (this.equals(Side.TOP)) return Side.LEFT;
        else if (this.equals(Side.BOTTOM)) return Side.RIGHT;
        else if (this.equals(Side.RIGHT)) return Side.TOP;
        else if (this.equals(Side.LEFT)) return Side.BOTTOM;
        else return Side.CENTER;
    }


    /**
     * Gets the right side from the current side
     *
     * @return the right side
     */
    public Side getRightSide() {
        if (this.equals(Side.TOP)) return Side.RIGHT;
        else if (this.equals(Side.BOTTOM)) return Side.LEFT;
        else if (this.equals(Side.RIGHT)) return Side.BOTTOM;
        else if (this.equals(Side.LEFT)) return Side.TOP;
        else return Side.CENTER;
    }


    /**
     * Gets the flipped side from the current side
     *
     * @return the flipped side
     */
    public Side getFlippedSide() {
        if (this.equals(Side.TOP)) return Side.TOP;
        else if (this.equals(Side.BOTTOM)) return Side.BOTTOM;
        else if (this.equals(Side.LEFT)) return Side.RIGHT;
        else if (this.equals(Side.RIGHT)) return Side.LEFT;
        else return Side.CENTER;
    }

}
