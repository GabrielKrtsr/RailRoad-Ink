package face;

import util.util_for_nodes.Side;
import util.Path;
import util.Type;


/**
 * Represents a specialized implementation of AbstractFace, where all paths are of type ROAD.
 * Face H
 */
public class HighwayFace extends AbstractFace {


    /**
     * Unique identifier for this type of face.
     * "H" stands for "Highway".
     */
    private static final String ID = "H";

    /**
     * Creates a new instance of HighwayFace,
     * representing a specific highway configuration.
     */
    public HighwayFace(){
        super(Type.CLASSIC,ID);
        connectNodes(Side.TOP,Side.CENTER,Path.ROAD);
        connectNodes(Side.CENTER,Side.BOTTOM,Path.ROAD);
    }
}
