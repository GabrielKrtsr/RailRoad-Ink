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
public abstract class Cell {


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
        //this.addInnerConnections();
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



    /**
     * Returns the points associated with the cell.
     *
     * @return the points value of this cell
     */
    public int getPoints() {
        return points;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cell cell = (Cell) o;
        return points == cell.points && Objects.equals(face, cell.face);
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
    /**
     * Determines whether the cell is considered a central cell.
     *
     * @return true if the cell is classified as central, false otherwise.
     */
    public abstract boolean isCentralCell();

    /**
     * Determines whether the current cell is a border cell.
     * @return true if the cell is a border cell, false otherwise
     */
    public abstract boolean isBorderCell();


}
