package util.util_for_nodes;

import board.Board;
import util.Path;
import util.Tuple;


import java.util.HashSet;
import java.util.Set;

/**
 * Represents a network node that can be connected to other nodes via specific connection types.
 * Each node has a unique identifier and may have attributes such as being an exit node
 * or a central node. The class also provides methods to manage connections and retrieve information
 * about connectivity and associated connection details.
 */
public class Node implements Cloneable {
    /**
     * Represents the set of connections associated with a node.
     * Each connection is defined as a tuple consisting of a target node
     * and the connection details (e.g., the path type between the nodes).
     */
    private Set<Tuple<Node, Connection>> connections;
    /**
     * Represents the unique identifier for a Node instance.
     * Used to distinguish and reference individual nodes within a structure or system.
     */
    private int id;
    
    /**
     * Indicates whether this node is designated as an exit node in the network.
     * An exit node is typically a terminal point or endpoint in a graph structure
     * where certain operations or paths terminate.
     */
    private boolean isExitNode;

    /**
     * Indicates whether this node is designated as a central node.
     * A central node may have specific significance in the context
     * of the containing graph or network structure, such as being
     * a hub or a critical connection point.
     */
    private boolean isCentralNode;



    private boolean isStationNode;



    private boolean isBoardNode;

    public boolean isBoardNode() {
        return isBoardNode;
    }

    public void setBoardNode(boolean boardNode) {
        isBoardNode = boardNode;
    }

    /**
     * Determines if the current node is designated as a central node.
     *
     * @return true if the node is a central node, false otherwise.
     */
    public boolean isCentralNode() {
        return isCentralNode;
    }

    /**
     * Sets whether the node is designated as a central node.
     *
     * @param isCentralNode a boolean value indicating if the node should be identified
     *                      as a central node. Pass true to mark the node as central,
     *                      or false otherwise.
     */
    public void setCentralNode(boolean isCentralNode) {
        this.isCentralNode = isCentralNode;
    }


    /**
     * Determines whether the current node is marked as an exit node.
     *
     * @return true if this node is an exit node; false otherwise.
     */
    // Getter method for isExitNode
    public boolean isExitNode() {
        return isExitNode;
    }

    /**
     * Sets whether this node is an exit node or not.
     *
     * @param isExitNode a boolean value indicating whether the node should be marked as an exit node.
     */
    public void setExitNode(boolean isExitNode) {
        this.isExitNode = isExitNode;
    }

    public boolean isStationNode() {
        return isStationNode;
    }

    public void setStationNode(boolean stationNode) {
        isStationNode = stationNode;
    }


    /**
     * Creates a new instance of the Node class.
     *
     * The new Node instance is initialized with the following default configurations:
     * - The `connections` field is initialized as an empty HashSet, representing the connections to other nodes.
     * - The `isExitNode` field is set to `false`, indicating that the node is not an exit node by default.
     * - The `isCentralNode` field is set to `false`, indicating that the node is not a central node by default.
     */
    public Node() {
        this.connections = new HashSet<>();
        this.isExitNode = false;
        this.isCentralNode = false;
        this.isStationNode = false;
        this.isBoardNode = false;
    }

    /**
     * Retrieves the set of connections associated with this node. Each connection
     * is represented as a tuple containing a connected node and its respective
     * connection details.
     *
     * @return a set of tuples where each tuple contains a connected node and its associated connection.
     */
    public Set<Tuple<Node, Connection>> getConnections() {
        return connections;
    }

    /**
     * Adds a connection between the current node and the specified node with the given connection details.
     *
     * @param node       the node to which the connection should be added
     * @param connection the connection object that defines the relationship between the nodes
     */
    public void addConnection(Node node, Connection connection) {
        if (connections != null) {
            connections.add(new Tuple<>(node, connection));
        }
    }

    /**
     * Removes a connection to the specified node from the current node's connections.
     *
     * @param node the node whose connection is to be removed.
     * @return {@code true} if the connection was successfully removed;
     *         {@code false} if no connection exists or the operation could not be performed.
     */
    public boolean removeConnection(Node node) {
        if (connections != null) {
            return connections.removeIf(tuple -> tuple.getType1().equals(node));
        }
        return false;
    }

    /**
     * Determines if the current node is connected to the specified node.
     *
     * @param node the node to check for a connection.
     * @return true if the current node is connected to the specified node, false otherwise.
     */
    public boolean isConnected(Node node) {
        if (connections != null) {
            for (Tuple<Node, Connection> tuple : connections) {
                if (tuple.getType1().equals(node)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Creates and returns a copy of this Node instance. The cloned instance includes
     * a deep copy of its connections field, ensuring that the cloned Node has an independent
     * set of connections.
     *
     * @return a clone of this Node instance.
     * @throws CloneNotSupportedException if the Node's class does not support the Cloneable interface.
     */
    @Override
    public Node clone() throws CloneNotSupportedException {
        Node clonedNode = (Node) super.clone();
        clonedNode.connections = new HashSet<>();
        return clonedNode;
    }

    /**
     * Retrieves the connection associated with the specified node by searching
     * through the existing connections.
     *
     * @param node the Node object whose associated Connection is to be retrieved.
     * @return the Connection associated with the specified node if found,
     *         otherwise returns null.
     */
    public Connection getConnection(Node node) {
        if (connections != null) {
            for (Tuple<Node, Connection> tuple : connections) {
                if (tuple.getType1().equals(node)) {
                    return tuple.getType2();
                }
            }
        }
        return null;
    }
    /**
     * Retrieves the unique identifier of the Node.
     *
     * @return the identifier of the Node as an integer.
     */
    public int getId () {
        return id;
    }

    /**
     * Sets the identifier for this node.
     *
     * @param id the identifier to set for this node
     */
    public void setId(int id) {
        this.id = id;
    }


    /**
     * Returns a string representation of the Node object.
     * The string primarily includes the node's identifier.
     *
     * @return a string representation of the Node, including its ID.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Node id=").append(id);
//                .append(", connections=[");
//        if (connections != null && !connections.isEmpty()) {
//            for (Tuple<Node, Connection> tuple : connections) {
//                Node connectedNode = tuple.getType1();
//                Connection connection = tuple.getType2();
//                sb.append("{connectedNodeId=")
//                        .append(connectedNode.getId())
//                        .append(", connectionType=")
//                        .append(connection.getPathType())
//                        .append("}, ");
//            }
//            sb.setLength(sb.length() - 2);
//        }
//        sb.append("]}");
        return sb.toString();
    }

    /**
     * Checks if the node has a connection with the specified path type.
     *
     * @param pathType the type of path to check for among the node's connections.
     * @return true if a connection with the specified path type exists, false otherwise.
     */
    public boolean hasPathType(Path pathType) {
        for (Tuple<Node, Connection> connection : this.getConnections()) {
            if (connection.getType2().getPathType() == pathType) {
                return true;
            }
        }
        return false;
    }

/*
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Node other = (Node) obj;
        return this.id == other.id;
    }

 */

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }

}
