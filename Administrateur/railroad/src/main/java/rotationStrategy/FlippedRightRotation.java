package rotationStrategy;

import util.util_for_nodes.Node;
import util.util_for_nodes.Side;
import face.AbstractFace;


/**
 * A rotation strategy that combines a flipped rotation followed by a right rotation
 * for an AbstractFace instance. This strategy effectively flips the face
 * horizontally and then rotates it to the right.
 */
public class FlippedRightRotation implements RotationStrategy {

    /**
     * Unique identifier for this type of rotation.
     * "FR" stands for "Flipped Right".
     */
    private final String id = "FR";

    /**
     * Retrieves the unique identifier of the rotation strategy.
     *
     * @return the identifier for the FlippedRightRotation strategy, specifically "FR".
     */
    @Override
    public String getId() {
        return id;
    }


    /**
     * Rotates the given AbstractFace instance by applying a series of rotation strategies.
     * This method performs a flipped rotation followed by a right rotation.
     *
     * @param face The AbstractFace instance to be rotated.
     */
    @Override
    public void rotate(AbstractFace face) {

        new FlippedRotation().rotate(face);
        new RightRotation().rotate(face);

    }


}
