package rotationStrategy;


import face.AbstractFace;

/**
 * Represents a strategy for rotating an implementation of the AbstractFace class.
 */
public interface RotationStrategy {

    /**
     * Retrieves the unique identifier of the rotation strategy.
     *
     * @return the ID of the strategy.
     */
    String getId();

    /**
     * Rotates the given AbstractFace instance according to a specific rotation strategy.
     *
     * @param f the AbstractFace instance to be rotated
     */
    void rotate(AbstractFace f) ;
}
