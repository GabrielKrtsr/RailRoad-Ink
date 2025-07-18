package dice;

import face.*;
/**
 * ConnectionsDice Class
 */
public class ConnectionsDice extends Dice {


    /** The fixed number of throws allowed for this die. */
    private static final int NUMBER_OF_THROWS = 1;


    /**
     * Constructs a {@code ConnectionsDice} with a predefined number of throws.
     * Calls the superclass constructor to initialize the die.
     */
    public ConnectionsDice(){
        super(NUMBER_OF_THROWS);
    }

    @Override
    protected void initializeFaces() {
        faces.add(new StationFace());
        faces.add(new StationFace());
        faces.add(new StationCurveFace());
        faces.add(new StationCurveFace());
        faces.add(new HighwayRailFace());
        faces.add(new HighwayRailFace());
    }
}

