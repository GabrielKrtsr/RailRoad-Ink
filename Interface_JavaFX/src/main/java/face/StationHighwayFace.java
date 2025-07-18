package face;

import util.util_for_nodes.Side;
import util.Path;
import util.Type;

/**
 * Represents a specific type of face configuration
 * Face SH
 */
public class StationHighwayFace extends AbstractFace {



    /**
     * Unique identifier for this type of face.
     * "SH" stands for "Station Highway".
     */
    private static final String ID = "SH";

    /**
     * Constructs a StationHighwayFace object with a specific path configuration.
     */
    public StationHighwayFace(){
        super(Type.SPECIAL,ID);
        connectNodes(Side.TOP,Side.CENTER,Path.ROAD);
        connectNodes(Side.BOTTOM,Side.CENTER,Path.ROAD);
        connectNodes(Side.RIGHT,Side.CENTER,Path.ROAD);
        connectNodes(Side.LEFT,Side.CENTER,Path.RAIL);
        this.getNode(Side.CENTER).setStationNode(true);

    }
}
