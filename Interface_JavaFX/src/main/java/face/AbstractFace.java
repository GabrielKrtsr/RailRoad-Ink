package face;



import javafx.scene.image.Image;
import util.util_for_nodes.Connection;
import util.util_for_nodes.Node;
import util.util_for_nodes.Side;
import rotationStrategy.RotationStrategy;
import util.Path;
import util.Tuple;
import util.Type;


import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.EnumMap;
import java.util.Map;
import java.util.stream.Collectors;



/**
 * Represents an abstract face, associating paths with cardinal coordinates.
 * This class manages the nodes and their connections on a face of the board.
 */
public abstract class AbstractFace implements Cloneable {


    private String imageSvg;

    private String rotate;

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
        this.rotate="";

        for (Side side : Side.values()) {
            this.nodes.put(side, new Node());
        }
        
        this.setImageSvg();
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
     * Displays all connections of nodes in the current AbstractFace instance, along with their associated paths.
     */
    public void displayConnections() {
        for (Map.Entry<Side, Node> entry : nodes.entrySet()) {
            Side side = entry.getKey();
            Node node = entry.getValue();
            System.out.println("Connections for side: " + side);

            for (Tuple<Node, Connection> connection : node.getConnections()) {
                System.out.println("  Connected to Node: " + connection.getType1() +
                        ", Path Type: " + connection.getType2().getPathType());
            }
        }
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
        a.rotate = "";
        a.nodes = new EnumMap<>(Side.class);
        for (Side side : Side.values()) {
            Node clonedNode = this.nodes.get(side).clone();
            a.nodes.put(side, clonedNode);
        }
        a.imageSvg = this.imageSvg;
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

    public String getRotate() {
        return rotate;
    }

    public void setRotate(String rotate) {
        this.rotate = rotate;
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

    /**
     * Displays the face connections in a human-readable format.
     */
    public void dysplayFace() {
        for (Side s : Side.values()){
            if(s != Side.CENTER && !this.getNode(s).getConnections().isEmpty()){
                System.out.println(s +" : "+ (this.getNode(s).getConnections().stream().collect(Collectors.toList()).get(0).getType2()));
            }
        }
    }

    public String getImageSvg(){
        return this.imageSvg;
    }

    public void setImageSvg() {
        String path = "src/main/resources/images/faces/" + this.getId() + ".svg";
        this.imageSvg = loadFaceSvg(path);
    }

    public void changeImageSvg(String imageSvg){
        this.imageSvg = imageSvg;
    }

    private String loadFaceSvg(String filePath) {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                //textArea.setText("Face SVG file not found: " + filePath);
                System.out.println("Not found");
                return null;
            }

            String faceSvg = new String(Files.readAllBytes(Paths.get(filePath)), StandardCharsets.UTF_8);
            int startIndex = faceSvg.indexOf("<svg");
            int endIndex = faceSvg.lastIndexOf("</svg>");

            if (startIndex != -1 && endIndex != -1) {
                int contentStart = faceSvg.indexOf('>', startIndex) + 1;
                if (contentStart > 0 && contentStart < endIndex) {
                    return faceSvg.substring(contentStart, endIndex);
                    //return faceSvg;
                }
            }

            return null;
        } catch (IOException e) {
            //textArea.setText("Error loading face SVG: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }


}
