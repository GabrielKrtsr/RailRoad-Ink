package face;

import util.util_for_nodes.Connection;
import util.util_for_nodes.Node;
import util.util_for_nodes.Side;
import rotationStrategy.RotationStrategy;
import util.Path;
import util.Tuple;
import util.Type;

import java.util.EnumMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Represents an abstract face, associating paths with cardinal coordinates.
 * This class manages the nodes and their connections on a face of the board.
 */
public abstract class AbstractFace implements Cloneable {

    /**
     * Map of nodes associated with each side of the face.
     */
    private Map<Side, Node> nodes;

    /**
     * The type of the face.
     */
    private Type type;

    /**
     * The unique identifier of the face.
     */
    private String id;

    /**
     * Creates an instance of AbstractFace, initializing nodes for each side.
     *
     * @param type The type of the face.
     * @param id The unique identifier of the face.
     */
    public AbstractFace(Type type, String id){
        this.nodes = new EnumMap<>(Side.class);
        this.type = type;
        this.id = id;

        for (Side side : Side.values()) {
            this.nodes.put(side, new Node());
        }
    }

    /**
     * Retrieves the node associated with the specified side.
     *
     * @param side The side whose node is to be retrieved.
     * @return The node associated with the given side.
     */
    public Node getNode(Side side) {
        return this.nodes.get(side);
    }

    /**
     * Sets the node associated with a specific side.
     *
     * @param side The side to associate the node with.
     * @param node The node to be assigned to the given side.
     */
    public void setNode(Side side, Node node){
        this.nodes.put(side,node);
    }

    /**
     * Connects two nodes located on the specified sides with a given path.
     *
     * @param side1 The first side.
     * @param side2 The second side.
     * @param path The path connecting the nodes of the two sides.
     */
    public void connectNodes(Side side1, Side side2, Path path){
        Node node1 = this.getNode(side1);
        Node node2 = this.getNode(side2);
        node1.addConnection(node2, new Connection(path));
        node2.addConnection(node1, new Connection(path));
    }


    /**
     * Retrieves all nodes of the current AbstractFace instance.
     *
     * @return A map of sides to their associated nodes.
     */
    public Map<Side, Node> getNodes() {
        return this.nodes;
    }

    /**
     * Checks if two sides of the face are connected.
     *
     * @param side1 The first side to check.
     * @param side2 The second side to check.
     * @return True if the nodes of the two sides are connected, false otherwise.
     */
    public boolean areTwoSidesConnected(Side side1, Side side2) {
        Node node1 = this.getNode(side1);
        Node node2 = this.getNode(side2);
        return node1.isConnected(node2);
    }

    /**
     * Retrieves the connection details between the nodes of two specified sides.
     *
     * @param side1 The first side.
     * @param side2 The second side.
     * @return The Connection object if a connection exists, otherwise null.
     */
    public Connection getConnectionBetweenTwoSides(Side side1, Side side2) {
        Node node1 = this.getNode(side1);
        Node node2 = this.getNode(side2);
        return node1.getConnection(node2);
    }

    /**
     * Creates a deep copy of this AbstractFace instance.
     *
     * @return A cloned AbstractFace instance.
     * @throws CloneNotSupportedException If cloning is not supported.
     */
    @Override
    public AbstractFace clone() throws CloneNotSupportedException {
        AbstractFace a = (AbstractFace) super.clone();
        a.nodes = new EnumMap<>(Side.class);
        for (Side side : Side.values()) {
            Node clonedNode = this.nodes.get(side).clone();
            a.nodes.put(side, clonedNode);
        }
        for (Side side1 : Side.values()) {
            for (Side side2 : Side.values()) {
                if (this.areTwoSidesConnected(side1, side2)) {
                    Connection originalConnection = this.getConnectionBetweenTwoSides(side1, side2);
                    Connection newConnection = new Connection(originalConnection.getPathType());
                    Node clonedNode1 = a.getNode(side1);
                    Node clonedNode2 = a.getNode(side2);
                    clonedNode1.addConnection(clonedNode2, newConnection);
                }
            }
        }
        return a;
    }

    /**
     * Rotates the current AbstractFace instance using the given rotation strategy.
     *
     * @param r The rotation strategy to be applied to this AbstractFace instance.
     */
    public void rotate(RotationStrategy r){
        r.rotate(this);
    }

    /**
     * Retrieves the type of this face.
     *
     * @return The type of the face.
     */
    public Type getType() {
        return this.type;
    }

    /**
     * Retrieves the unique identifier of this face.
     *
     * @return The ID of the face.
     */
    public String getId() {
        return this.id;
    }

}
