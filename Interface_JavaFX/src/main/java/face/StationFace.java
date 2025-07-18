package face;

import util.util_for_nodes.Side;
import util.Path;
import util.Type;

/**
 * Represents a specific configuration of an AbstractFace
 * Face S
 */
public class StationFace extends AbstractFace {


    /**
     * Unique identifier for this type of face.
     * "S" stands for "Station ".
     */
    private static final String ID = "S";

    /**
     * Constructs a StationFace instance with predefined path associations
     */
    public StationFace(){
        super(Type.CLASSIC,ID);
        connectNodes(Side.TOP,Side.CENTER,Path.ROAD);
        connectNodes(Side.CENTER,Side.BOTTOM,Path.RAIL);
        this.getNode(Side.CENTER).setStationNode(true);

    }


}
