package cell;

import board.Board;
import face.*;
import util.*;

import util.util_for_nodes.Connection;
import util.util_for_nodes.Node;
import util.util_for_nodes.Side;
import graph.ConnectivityInspector;
import graph.Edge;
import graph.Graph;

import java.util.*;
import java.util.stream.Collectors;


/**
 * Represents a generic cell within a game board.
 */
public abstract class Cell implements Cloneable{
    
    protected int id;


    /**
     * Retrieves the ID associated with this cell.
     *
     * @return the ID of this cell
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID for this cell.
     *
     * @param id the new ID to assign to this cell
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Checks if this cell is equal to another object based on the ID.
     *
     * @param obj the object to compare with
     * @return true if the object is a Cell and has the same ID, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Cell cell = (Cell) obj;
        return id == cell.id;
    }


    /**
     * Represents the border of a BorderCell
     */
    protected Tuple<Side, Path> border;
    /**
     * Represents the face of the cell
     */
    protected AbstractFace face;
    /**
     * The score or value associated with this cell.
     */
    private final int points;

    /**
     * Indicates whether the cell is an exit.
     * This variable determines if the current cell acts as a termination point
     * within the broader structure or mechanism in which it is being used.
     */
    private boolean isExit;

    /**
     * A mapping between the sides of the cell and their associated nodes.
     * Each side of the cell (represented by the {@link Side} enum) can be
     * linked to a specific {@link Node} that represents a connection point or
     * endpoint on that side of the cell. This mapping is used to manage
     * and facilitate connections between nodes within the cell and across
     * other cells in the board.
     */
    private Map<Side, Node> nodes;//

    /**
     * A graph structure representing the internal connections between nodes within a cell.
     * This graph is used to model the relationships and connections established between
     * nodes defined within the cell, allowing for efficient retrieval and manipulation of
     * connectivity information. The edges in the graph represent the pathways or connections
     * between nodes, and the nodes themselves represent entities within the cell structure.
     */
    private Graph<Node, Edge<Node>> innerConnectionsGraph; //

    /**
     * Represents the internal connectivity structure of a Cell instance.
     * This variable stores a list of sets, where each set contains nodes
     * that are directly or indirectly connected within the cell. Each set
     * corresponds to a group of nodes that form a connected component
     * within the cell's graph structure.
     *
     * The `innerConnections` is updated whenever nodes are connected or
     * disconnected within the cell, reflecting the current state of
     * connectivity. It serves as the primary representation of node
     * relationships for operations such as notifications, graph
     * analysis, and connectivity checks.
     */
    private List<Set<Node>> innerConnections; //

    /**
     * Represents the `Board` associated with this cell.
     * The `Board` instance captures the overall state and structure
     * of the game or system in which this cell is a component.
     * It is used for managing and interacting with the overarching
     * environment in which this cell resides, as well as for
     * facilitating operations such as notifications and updates
     * related to the cell's state or connections.
     */
    private Board board;//

    /**
     * Retrieves the board associated with this cell.
     *
     * @return the Board object currently linked to this cell
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Sets the board associated with this cell.
     *
     * @param board the Board object to be associated with this cell
     */
    public void setBoard(Board board) {
        this.board = board;
    }

    /**
     * Retrieves the inner connections of this cell. The inner connections represent
     * sets of nodes that are internally connected within the cell without considering
     * connections to external components.
     *
     * @return a list of sets, where each set contains nodes that are connected within the cell
     */
    public List<Set<Node>> getInnerConnections() {
        return innerConnections;
    }

    /**
     * Retrieves the inner connections graph of the cell.
     *
     * The graph represents the connectivity structure of nodes and edges
     * within the cell, describing the relationships or connections between
     * the nodes internally.
     *
     * @return a Graph object consisting of Nodes and Edges that represent
     *         the internal connections within the cell.
     */
    public Graph<Node, Edge<Node>> getInnerConnectionsGraph() {
        return innerConnectionsGraph;
    }

    /**
     * Constructs a Cell with the specified number of points.
     *
     * @param points the point value assigned to the cell
     */
    public Cell(int points, boolean isExit){
        this.face = null;
        this.points = points;
        this.isExit = isExit;

        this.nodes = new EnumMap<>(Side.class);//
        this.initializeNodeMap();//

        this.innerConnectionsGraph = new Graph<>();

        this.innerConnections = new ArrayList<>();

    }

    /**
     * Initializes the map of nodes associated with each side of the cell.
     *
     * This method iterates over all possible values of the Side enumeration,
     * and for each side, assigns a null value in the `nodes` map. This operation
     * ensures that the map is properly initialized with keys corresponding to
     * all sides of the cell, but without any associated nodes initially.
     */
    public void initializeNodeMap(){
        for (Side side : Side.values()) {
            this.nodes.put(side, null);
        }
    }

    /**
     * Retrieves the border information of the cell.
     * This method should be overridden by subclasses if applicable.
     *
     * @return null
     */
    public Tuple<Side, Path> getBorder() {
        return null;
    }

    /**
     * Checks whether this cell is an exit.
     *
     * @return true if the cell is an exit, false otherwise.
     */
    public boolean isExit() {
        return this.isExit;
    }

    /**
     * Retrieves the current Face associated with the cell.
     *
     * @return the Face object representing the face of the cell,
     */
    public AbstractFace getFace() {
        return face;
    }

    /**
     * Sets the face of this cell to the specified HighwayFace.
     *
     * @param face the HighwayFace to be associated with this cell
     */
    public void setFace(AbstractFace face) {
        this.face = face;
        this.addNodesConnectionsFromFace(this.face);
        if(face.getNode(Side.CENTER).isStationNode()){
            this.getNode(Side.CENTER).setStationNode(true);
        }
    }


    /**
     * Removes the face associated with this cell and clears related connections.
     */
    public void removeFace() {
        if (this.face != null) {
            // Clear connections between nodes set by the current face
            ConnectivityInspector<Node,Edge<Node>> connectivityInspector = new ConnectivityInspector<>(innerConnectionsGraph);
            this.innerConnections = connectivityInspector.connectedSets().stream().collect(Collectors.toList());
            sendNotificationOfRemove();
            for (Side side1 : nodes.keySet()) {
                for (Side side2 : nodes.keySet()) {
                    Node node1 = this.getNode(side1);
                    Node node2 = this.getNode(side2);
                    if (node1 != null && node2 != null && this.areTwoNodesConnected(node1, node2)) {
                        this.innerConnectionsGraph.removeEdge(new Edge<>(node1, node2, 1));
                    }
                    disconnectNodes(node1,node2);

                }
            }

            // Clear all vertices associated with the face
            for (Node node : nodes.values()) {
                if (node != null && this.innerConnectionsGraph.containsVertex(node)) {
                    this.innerConnectionsGraph.removeVertex(node);
                }
            }

            // Reset inner connections
            this.innerConnections.clear();
            
            // Remove the face
            this.face = null;
        }
    }
    

    /**
     * Adds connections between nodes based on the structure and connections of the specified face.
     * This method examines the relationships between sides of the face, establishes connections between
     * corresponding nodes, updates the internal graph of connections, and recalculates the connectivity structure.
     * Additionally, it triggers a notification after updating the connections.
     *
     * @param face the face object containing sides, nodes, and connectivity information used to establish connections
     */
    private void addNodesConnectionsFromFace(AbstractFace face){
        Map<Side,Node> nodes = face.getNodes();
        for(Side side1 : nodes.keySet()){
            for(Side side2: nodes.keySet()){
                if(face.areTwoSidesConnected(side1,side2)){
                    Connection connectionBetweenSides = face.getConnectionBetweenTwoSides(side1,side2);
                    Node node1 = this.getNode(side1);
                    Node node2 = this.getNode(side2);
                    if(node1 != null && node2 != null){
                        if(!this.areTwoNodesConnected(node1,node2)){
                            this.connectNodes(node1,node2,connectionBetweenSides.getPathType());
                            if(!this.innerConnectionsGraph.containsVertex(node1)){
                               this.innerConnectionsGraph.addVertex(node1);
                            }
                            if(!this.innerConnectionsGraph.containsVertex(node2)){
                                this.innerConnectionsGraph.addVertex(node2);
                            }
                            if(!innerConnectionsGraph.containsEdge(new Edge<>(node1,node2,1))){
                                this.innerConnectionsGraph.addEdge(new Edge<>(node1,node2,1));
                            }
                        }
                    }
                }
            }

        }
        ConnectivityInspector<Node,Edge<Node>> connectivityInspector = new ConnectivityInspector<>(innerConnectionsGraph);
        this.innerConnections = connectivityInspector.connectedSets().stream().collect(Collectors.toList());
        this.sendNotification();
    }

    /**
     * Sends a notification to update the board state based on the current inner connections
     * of the cell. This method invokes the `updateVertex` method on the associated `Board`
     * instance, providing the list of connected sets of nodes (`innerConnections`) as its
     * parameter to reflect the latest connectivity changes within the cell's inner graph.
     */
    public void sendNotification(){
        this.board.updateVertex(this.innerConnections);
    }

    public void sendNotificationOfRemove(){
        this.board.removeVertex(this.innerConnections);
    }



    /**
     * Returns the points associated with the cell.
     *
     * @return the points value of this cell
     */
    public int getPoints() {
        return points;
    }


    /**
     * Generates a hash code value for the Cell instance. The hash code is derived
     * based on the `face` and `points` properties of the Cell, ensuring that
     * equal Cell instances produce the same hash code.
     *
     * @return an integer representing the hash code of the Cell
     */
    @Override
    public int hashCode() {
        return Objects.hash(face, points);
    }
    /**
     * Associates a node with a specific side of the cell.
     *
     * @param side the side of the cell where the node will be placed
     * @param node the node to associate with the specified side
     */
    public void putNode(Side side, Node node){
        this.nodes.put(side,node);
    }

    /**
     * Retrieves the Node associated with the specified side of the cell.
     *
     * @param side the side of the cell for which the Node is to be retrieved
     * @return the Node associated with the specified side, or null if no Node is associated with that side
     */
    public Node getNode(Side side){
        return this.nodes.getOrDefault(side,null);
    }
    /**
     * Establishes a bidirectional connection between two nodes using the specified path.
     *
     * @param node1 the first Node to establish the connection
     * @param node2 the second Node to establish the connection
     * @param path the Path object defining the link between the two nodes
     */
    public void connectNodes(Node node1, Node node2, Path path){
        node1.addConnection(node2, new Connection(path));
        node2.addConnection(node1, new Connection(path));
    }

    public void disconnectNodes(Node node1, Node node2){
        node1.removeConnection(node2);
        node2.removeConnection(node1);
    }


    /**
     * Determines whether two nodes are connected.
     *
     * @param node1 the first node to check for a connection
     * @param node2 the second node to check for a connection
     * @return true if the two nodes are connected, false otherwise
     */
    public boolean areTwoNodesConnected(Node node1, Node node2) {
        return node1.isConnected(node2);
    }




    /**
     * Retrieves a map of nodes associated with their corresponding sides in the cell.
     *
     * @return a map where the keys are of type Side, representing the sides of the cell,
     *         and the values are of type Node, representing the nodes linked to those sides.
     */
    public Map<Side, Node> getNodes() {
        return nodes;
    }
    

    @Override
    public Cell clone() {
        try {
            Cell cloned = (Cell) super.clone();

            // Deep clone mutable fields, ensuring that the cloned object has its own distinct state

            if (this.innerConnectionsGraph != null) {
                cloned.innerConnectionsGraph = this.innerConnectionsGraph.clone();
            }

            if (this.innerConnections != null) {
                cloned.innerConnections = new ArrayList<>();
                for (Set<Node> innerConnectionSet : this.innerConnections) {
                    cloned.innerConnections.add(new HashSet<>(innerConnectionSet));
                }
            }

            
            if (this.nodes != null) {
                cloned.nodes = new EnumMap<>(Side.class);
                for (Map.Entry<Side, Node> entry : this.nodes.entrySet()) {
                    cloned.nodes.put(entry.getKey(), entry.getValue().clone()); // Assuming Node has a proper clone() method
                }
            }
            
            cloned.face = (this.face != null) ? this.face.clone() : null; // Assuming AbstractFace has a clone() method

            return cloned;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError("Clone is not supported", e);
        }
    }



    
    /**
     * Checks whether the given node exists in the cell.
     *
     * @param node the node to check for existence
     * @return true if the node exists in the cell, false otherwise
     */
    public boolean containsNode(Node node) {
        return this.nodes.containsValue(node);
    }

    /**
     * Checks whether the specified node exists in the inner connections of the cell.
     * The inner connections represent sets of nodes that are internally connected within the cell.
     *
     * @param node the Node object to check for existence within the inner connections
     * @return true if the node exists in any of the inner connections, false otherwise
     */
    public boolean containsNodeInInnerConnections(Node node) {
        for (Set<Node> nodeSet : innerConnections) {
            if (nodeSet.contains(node)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Retrieves the side associated with the specified node in the cell.
     *
     * @param node the node whose associated side is to be retrieved
     * @return the Side associated with the node, or null if the node is not found
     */
    public Side getSide(Node node) {
        for (Map.Entry<Side, Node> entry : nodes.entrySet()) {
            if (entry.getValue().equals(node)) {
                return entry.getKey();
            }
        }
        return null;
    }

    public abstract boolean isCentralCell();

    public abstract boolean isBorderCell();


}
