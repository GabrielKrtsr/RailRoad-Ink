package face;

import util.util_for_nodes.Side;
import util.Path;
import util.Type;

/**
 * Represents a specific type of face for a highway junction, extending the abstract face structure.
 * Face HJ
 */
public class HighwayJunctionFace extends AbstractFace {

    /**
     * Unique identifier for this type of face.
     * "HJ" stands for "Highway Junction".
     */
    private static final String ID = "Hj";

    /**
     * Creates a new instance of HighwayJunctionFace,
     * representing a specific highway configuration.
     */
    public HighwayJunctionFace(){
        super(Type.CLASSIC,ID);
        connectNodes(Side.TOP,Side.CENTER,Path.ROAD);
        connectNodes(Side.BOTTOM,Side.CENTER,Path.ROAD);
        connectNodes(Side.RIGHT,Side.CENTER,Path.ROAD);
    }
}
