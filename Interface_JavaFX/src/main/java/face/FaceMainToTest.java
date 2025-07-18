package face;

import rotationStrategy.LeftRotation;
import rotationStrategy.RightRotation;
import util.util_for_nodes.Side;

public class FaceMainToTest {
    public static void main(String[] args) throws CloneNotSupportedException {
        AbstractFace face = new RailFace();
        AbstractFace face2 = face.clone();
        face.dysplayFace();
        face2.dysplayFace();
        if (!face.getNode(Side.TOP).getConnections().isEmpty()) {
            System.out.println("ananas");
        }
        face.rotate(new LeftRotation());
        if (!face2.getNode(Side.TOP).getConnections().isEmpty()) {
            System.out.println("ananas");
        }
        face.dysplayFace();
        face2.dysplayFace();
    }
}
