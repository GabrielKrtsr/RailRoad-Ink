package rotationStrategy;

import util.util_for_nodes.Node;
import util.util_for_nodes.Side;
import face.AbstractFace;


/**
 * A class that implements the RotationStrategy to perform an upside-down
 * rotation on an instance of AbstractFace. This rotation swaps the paths
 * between the NORTH and SOUTH coordinates.
 */
public class UpsideDownRotation implements RotationStrategy {


    /**
     * Unique identifier for this type of rotation.
     * "Fl" stands for "UpsideDown".
     */
    private final  String id = "U";

    /**
     * Retrieves the unique identifier associated with the rotation strategy.
     *
     * @return the unique ID of the rotation strategy.
     */
    @Override
    public String getId() {
        return id;
    }

    /**
     * Rotates the given AbstractFace instance by reassigning its nodes to new positions
     * based on the rotation logic. The top node is replaced with the bottom node,
     * the bottom node with the top node, the left node with the right node,
     * and the right node with the left node.
     *
     * @param face The AbstractFace instance whose nodes are to be rotated.
     */
    @Override
    public void rotate(AbstractFace face) {
        Node newTop = face.getNode(Side.TOP.getOppositeSide());
        Node newBottom = face.getNode(Side.BOTTOM.getOppositeSide());
        Node newLeft = face.getNode(Side.LEFT.getOppositeSide());
        Node newRight = face.getNode(Side.RIGHT.getOppositeSide());

        face.setNode(Side.TOP, newTop);
        face.setNode(Side.BOTTOM, newBottom);
        face.setNode(Side.LEFT, newLeft);
        face.setNode(Side.RIGHT, newRight);
    }


}
