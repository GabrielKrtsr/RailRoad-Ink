package face;

import util.Path;
import util.Type;
import util.util_for_nodes.Side;

/**
 * Represents a specific implementation of the AbstractFace class
 * Face SC
 */
public class StationCurveFace extends AbstractFace {

    /**
     * Unique identifier for this type of face.
     * "SC" stands for "Station Curve".
     */
    private static final String ID = "Sc";

    /**
     * Constructs a StationCurveFace, a specific implementation of the AbstractFace class.
     */
    public StationCurveFace(){
        super(Type.CLASSIC,ID);
        connectNodes(Side.TOP,Side.CENTER,Path.ROAD);
        connectNodes(Side.CENTER,Side.RIGHT,Path.RAIL);
        this.getNode(Side.CENTER).setStationNode(true);
    }
}
