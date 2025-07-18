package dice;

import face.*;

/**
 * RouteOrRailRoadDice Class
 */
public class RouteOrRailRoadDice extends Dice {


    /** The fixed number of throws allowed for this die. */
    private static final int NUMBER_OF_THROWS = 3;

    /**
     * Constructs of RouteOrRailRoadDice
     */
    public RouteOrRailRoadDice(){
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
