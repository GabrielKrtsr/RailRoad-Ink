package rotationStrategy;

import face.AbstractFace;

public class ClassicRotation implements RotationStrategy {
    @Override
    public String getId() {
        return "C";
    }

    @Override
    public void rotate(AbstractFace f) {

    }

    @Override
    public RotationStrategy getOppositeRotation() {
        return this;
    }
}
