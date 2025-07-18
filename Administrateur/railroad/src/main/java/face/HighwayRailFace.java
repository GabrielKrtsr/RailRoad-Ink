package face;

import util.util_for_nodes.Side;
import util.Path;
import util.Type;

/**
 * Represents a specific implementation of the AbstractFace class
 * Face HR
 */
public class HighwayRailFace extends AbstractFace {


    /**
     * Unique identifier for this type of face.
     * "HR" stands for "Highway Rail".
     */
    private static final String ID = "HR";

    /**
     * Constructs an instance of HighwayRailFace, a specific implementation of the AbstractFace class
     */
    public HighwayRailFace(){
        super(Type.CLASSIC,ID);
        connectNodes(Side.TOP,Side.BOTTOM,Path.ROAD);
        connectNodes(Side.RIGHT,Side.CENTER,Path.RAIL);
        connectNodes(Side.LEFT,Side.CENTER,Path.RAIL);
    }
}
