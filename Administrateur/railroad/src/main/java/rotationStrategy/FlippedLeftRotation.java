package rotationStrategy;
import face.AbstractFace;


/**
 * Implements a composite rotation strategy by combining a flipped rotation
 * and a left rotation on an instance of AbstractFace.
 * This strategy first flips the left and right paths of the face
 * and then performs a leftward rotation.
 */
public class FlippedLeftRotation implements RotationStrategy {

    /**
     * Unique identifier for this type of rotation.
     * "FL" stands for "Flipped Left".
     */
    private final String id = "FL";

    /**
     * Retrieves the unique identifier of the FlippedLeftRotation strategy.
     *
     * @return a string representing the unique identifier of this rotation strategy.
     */
    @Override
    public String getId() {
        return id;
    }

    /**
     * Applies a sequence of rotations to an {@link AbstractFace} instance.
     * First, it performs a flipped rotation using {@link FlippedRotation},
     * followed by a left rotation using {@link LeftRotation}.
     *
     * @param face The face to which the rotation operations will be applied.
     */
    @Override
    public void rotate(AbstractFace face) {

    new FlippedRotation().rotate(face);
    new LeftRotation().rotate(face);

    }


}
