package face;

import util.util_for_nodes.Side;
import util.Path;
import util.Type;

/**
 * Represents a specific type of face in a board game where all paths
 * are associated with rail.
 * Face RR
 */
public class RailRailFace extends AbstractFace {


    /**
     * Unique identifier for this type of face.
     * "RR" stands for "Rail Rail".
     */
    private static final String ID = "RR";

    /**
     * Constructs a RailRailFace object where all paths are set to rail.
     */
    public RailRailFace(){
        super(Type.SPECIAL,ID);
        connectNodes(Side.TOP,Side.CENTER,Path.RAIL);
        connectNodes(Side.BOTTOM,Side.CENTER,Path.RAIL);
        connectNodes(Side.RIGHT,Side.CENTER,Path.RAIL);
        connectNodes(Side.LEFT,Side.CENTER,Path.RAIL);

    }
}
