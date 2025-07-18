package rotationStrategy;

import util.util_for_nodes.Node;
import util.util_for_nodes.Side;
import face.AbstractFace;


/**
 * A concrete implementation of the interface that performs
 * a left rotation of an instance.
 *
 */
public class LeftRotation implements RotationStrategy {

    /**
     * Unique identifier for this type of rotation.
     * "L" stands for "Left".
     */
    private final  String id = "L";

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
     * Performs a left rotation of the nodes on the given face.
     * Updates the nodes on the specified sides of the face to new positions
     * corresponding to a left rotation.
     *
     * @param face The face to be rotated. The face's nodes are adjusted based
     *             on the given rotation logic.
     */
    @Override
    public void rotate(AbstractFace face)  {
        Node newTop = face.getNode(Side.TOP.getRightSide());
        Node newBottom = face.getNode(Side.BOTTOM.getRightSide());
        Node newLeft = face.getNode(Side.LEFT.getRightSide());
        Node newRight = face.getNode(Side.RIGHT.getRightSide());

        face.setNode(Side.TOP, newTop);
        face.setNode(Side.BOTTOM, newBottom);
        face.setNode(Side.LEFT, newLeft);
        face.setNode(Side.RIGHT, newRight);
    }





}
