package rotationStrategy;

import util.util_for_nodes.Node;
import util.util_for_nodes.Side;
import face.AbstractFace;


/**
 * Represents a rotation strategy that combines two specific rotation strategies: FlippedRotation
 * and UpsideDownRotation. This strategy flips the LEFT and RIGHT nodes of an AbstractFace (using
 * the FlippedRotation strategy) and then rotates it upside-down, swapping the NORTH and SOUTH
 * nodes while flipping the remaining sides (using the UpsideDownRotation strategy).
 */
public class FlippedUpsideDownRotation implements RotationStrategy {

    /**
     * Unique identifier for this type of rotation.
     * "FU" stands for "Flipped UpsideDown".
     */
    private final String id = "FU";

    /**
     * Retrieves the unique identifier for the "Flipped UpsideDown" rotation strategy.
     *
     * @return the ID of this rotation strategy.
     */
    @Override
    public String getId() {
        return id;
    }


    /**
     * Rotates the given AbstractFace instance by applying a combination of two rotation strategies:
     * FlippedRotation and UpsideDownRotation.
     * @param face The AbstractFace instance to be rotated. This face will have its nodes transformed
     *             based on the applied rotation strategies.
     */
    @Override
    public void rotate(AbstractFace face) {

        new FlippedRotation().rotate(face);
        new UpsideDownRotation().rotate(face);

    }


}
