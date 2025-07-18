package face;

import util.Path;
import util.Type;
import util.util_for_nodes.Side;

/**
 * Represents a specific type of face configuration .
 * Face SR
 */
public class StationRailFace extends AbstractFace {


    /**
     * Unique identifier for this type of face.
     * "SR" stands for "Station Rail".
     */
    private static final String ID = "SR";

    /**
     * Constructs a StationRailFace instance with a predefined configuration of paths.
     */
    public StationRailFace(){
        super(Type.SPECIAL,ID);
        connectNodes(Side.TOP,Side.CENTER,Path.RAIL);
        connectNodes(Side.BOTTOM,Side.CENTER,Path.RAIL);
        connectNodes(Side.RIGHT,Side.CENTER,Path.RAIL);
        connectNodes(Side.LEFT,Side.CENTER,Path.ROAD);
        this.getNode(Side.CENTER).setStationNode(true);

    }
}
