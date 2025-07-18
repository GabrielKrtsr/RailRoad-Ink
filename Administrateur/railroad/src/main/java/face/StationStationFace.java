package face;

import util.Path;
import util.Type;
import util.util_for_nodes.Side;

/**
 * Represents a specific implementation of the AbstractFace class
 * Face SS
 */
public class StationStationFace extends AbstractFace {




    /**
     * Unique identifier for this type of face.
     * "SS" stands for "Station Station".
     */
    private static final String ID = "SS";

    /**
     * Constructs a StationStationFace
     */
    public StationStationFace(){
        super(Type.SPECIAL,ID);
        connectNodes(Side.TOP,Side.CENTER,Path.ROAD);
        connectNodes(Side.BOTTOM,Side.CENTER,Path.RAIL);
        connectNodes(Side.RIGHT,Side.CENTER,Path.ROAD);
        connectNodes(Side.LEFT,Side.CENTER,Path.RAIL);
        this.getNode(Side.CENTER).setStationNode(true);
    }
}
