package rotationStrategy;

import util.util_for_nodes.Node;
import util.util_for_nodes.Side;
import face.AbstractFace;


/**
 * Represents a rotation strategy that rotates the paths of an AbstractFace instance.
 */
public class RightRotation implements RotationStrategy {


    /**
     * Unique identifier for this type of rotation.
     * "R" stands for "Right".
     */
    private final  String id = "R";

    /**
     * Retrieves the unique identifier for this rotation strategy.
     *
     * @return the ID of the rotation strategy.
     */
    @Override
    public String getId() {
        return id;
    }

    /**
     * Rotates the given face by modifying its associated nodes according to the rotation strategy.
     *
     * @param face The AbstractFace instance whose nodes are to be rotated.
     *             This face should have predefined associations between its sides and nodes.
     */
    @Override
    public void rotate(AbstractFace face) {
        Node newTop = face.getNode(Side.TOP.getLeftSide());
        Node newBottom = face.getNode(Side.BOTTOM.getLeftSide());
        Node newLeft = face.getNode(Side.LEFT.getLeftSide());
        Node newRight = face.getNode(Side.RIGHT.getLeftSide());

        face.setNode(Side.TOP, newTop);
        face.setNode(Side.BOTTOM, newBottom);
        face.setNode(Side.LEFT, newLeft);
        face.setNode(Side.RIGHT, newRight);
    }


}
