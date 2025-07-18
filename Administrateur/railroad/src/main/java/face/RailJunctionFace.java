package face;

import util.util_for_nodes.Side;
import util.Path;
import util.Type;

/**
 * Represents a specific implementation of AbstractFace where all cardinal coordinates
 * Face RJ
 */
public class RailJunctionFace extends AbstractFace {


    /**
     * Unique identifier for this type of face.
     * "RJ" stands for "Rail Junction".
     */
    private static final String ID = "Rj";

    /**
     * Constructs a RailJunctionFace.
     */
    public RailJunctionFace(){
        super(Type.CLASSIC,ID);
        connectNodes(Side.TOP,Side.CENTER,Path.RAIL);
        connectNodes(Side.BOTTOM,Side.CENTER,Path.RAIL);
        connectNodes(Side.RIGHT,Side.CENTER,Path.RAIL);
    }

}
