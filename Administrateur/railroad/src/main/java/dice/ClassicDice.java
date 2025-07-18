package dice;

import face.*;

/**
 * ClassicDice Class
 */
public class ClassicDice extends Dice {


    /** The fixed number of throws allowed for this die. */
    private static final int NUMBER_OF_THROWS = 3;

    /**
     * Constructs of ClassicDice
     */
    public ClassicDice(){
        super(NUMBER_OF_THROWS);
    }
    @Override
    protected void initializeFaces() {
        faces.add(new HighwayFace());
        faces.add(new HighwayJunctionFace());
        faces.add(new HighwayCurveFace());
        faces.add(new RailJunctionFace());
        faces.add(new RailFace());
        faces.add(new RailCurveFace());
    }
}
