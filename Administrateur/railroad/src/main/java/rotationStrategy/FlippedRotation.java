package rotationStrategy;

import util.util_for_nodes.Node;
import util.util_for_nodes.Side;
import face.AbstractFace;


/**
 * Implements a rotation strategy for flipping the EAST and WEST paths of an AbstractFace instance.
 */
public class FlippedRotation implements RotationStrategy {

    /**
     * Unique identifier for this type of rotation.
     * "F" stands for "Flipped".
     */
    private final String id = "F";

    /**
     * Retrieves the unique identifier for the rotation strategy.
     *
     * @return the ID of the rotation strategy.
     */
    @Override
    public String getId() {
        return id;
    }


    /**
     * Rotates the given AbstractFace instance by flipping its LEFT and RIGHT nodes
     * to the respective flipped sides.
     *
     * @param face The AbstractFace instance to be rotated. This face will have its
     *             nodes swapped for the LEFT and RIGHT sides based on their flipped counterparts.
     */
    @Override
    public void rotate(AbstractFace face) {

        Node newLeft = face.getNode(Side.LEFT.getFlippedSide());
        Node newRight = face.getNode(Side.RIGHT.getFlippedSide());


        face.setNode(Side.LEFT, newLeft);
        face.setNode(Side.RIGHT, newRight);
    }


}
