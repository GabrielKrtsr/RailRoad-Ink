package face;

import util.util_for_nodes.Side;
import util.Path;
import util.Type;

/**
 * Represents a specific type of face where all paths are roads.
 * Face HH
 */
public class HighwayHighwayFace extends AbstractFace {

    /**
     * Unique identifier for this type of face.
     * "HH" stands for "Highway Highway".
     */
    private static final String ID = "HH";

    /**
     * Constructs a new HighwayHighwayFace, a specific type of AbstractFace
     * where all cardinal coordinates (NORTH, EAST, WEST, SOUTH) are associated
     * with roads.
     */
    public HighwayHighwayFace(){
        super(Type.SPECIAL,ID);
        connectNodes(Side.TOP,Side.CENTER,Path.ROAD);
        connectNodes(Side.BOTTOM,Side.CENTER,Path.ROAD);
        connectNodes(Side.RIGHT,Side.CENTER,Path.ROAD);
        connectNodes(Side.LEFT,Side.CENTER,Path.ROAD);
    }
}
