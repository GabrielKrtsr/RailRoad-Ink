package face;

import util.util_for_nodes.Side;
import util.Path;
import util.Type;


/**
 * A concrete implementation of the AbstractFace class representing a highway configuration.
 * Face HC
 */
public class HighwayCurveFace extends AbstractFace {


    /**
     * Unique identifier for this type of face.
     * "HC" stands for "Highway Curve".
     */
    private static final String ID = "Hc";

    /**
     * Creates a new instance of HighwayCurveFace,
     * representing a specific highway configuration.
     */
    public HighwayCurveFace(){
        super(Type.CLASSIC,ID);
        connectNodes(Side.TOP,Side.CENTER,Path.ROAD);
        connectNodes(Side.CENTER,Side.RIGHT,Path.ROAD);
    }
}
