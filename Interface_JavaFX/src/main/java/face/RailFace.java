package face;

import util.util_for_nodes.Side;
import util.Path;
import util.Type;

/**
 * Represents a specific configuration of the AbstractFace where all associated paths
 * are of type RAIL.
 * Face R
 */
public class RailFace extends AbstractFace {

    /**
     * Unique identifier for this type of face.
     * "R" stands for "Rail".
     */
    private static final String ID = "R";

    /**
     * Constructs a RailFace instance
     **/
    public RailFace(){
        super(Type.CLASSIC,ID);
        connectNodes(Side.TOP,Side.CENTER,Path.RAIL);
        connectNodes(Side.CENTER,Side.BOTTOM,Path.RAIL);

    }
}
