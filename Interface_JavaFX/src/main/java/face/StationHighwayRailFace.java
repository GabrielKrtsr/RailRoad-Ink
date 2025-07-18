package face;

import util.util_for_nodes.Side;
import util.Path;
import util.Type;

/**
 * Represents a specific configuration of an AbstractFace
 * Face SHR
 */
public class StationHighwayRailFace extends AbstractFace {


    /**
     * Unique identifier for this type of face.
     * "SHR" stands for "Station Highway Rail".
     */
    private static final String ID = "SHR";

    /**
     * Constructs a StationHighwayRailFace with specific path configurations.
     */
    public StationHighwayRailFace(){
        super(Type.SPECIAL,ID);
        connectNodes(Side.TOP,Side.CENTER,Path.ROAD);
        connectNodes(Side.BOTTOM,Side.CENTER,Path.ROAD);
        connectNodes(Side.RIGHT,Side.CENTER,Path.RAIL);
        connectNodes(Side.LEFT,Side.CENTER,Path.RAIL);
        this.getNode(Side.CENTER).setStationNode(true);

    }

}
