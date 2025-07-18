package face;

import util.util_for_nodes.Side;
import util.Path;
import util.Type;



/**
 * Represents a specific type of face where all cardinal paths are associated with RAIL.
 * Face RC
 */
public class RailCurveFace extends AbstractFace {


    /**
     * Unique identifier for this type of face.
     * "RC" stands for "Rail Curve".
     */
    private static final String ID = "Rc";

    /**
     * Constructs a RailCurveFace instance
     */
    public RailCurveFace(){
        super(Type.CLASSIC,ID);
        connectNodes(Side.TOP,Side.CENTER,Path.RAIL);
        connectNodes(Side.CENTER,Side.RIGHT,Path.RAIL);
    }
}
