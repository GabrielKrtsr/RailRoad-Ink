package util.util_for_nodes;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents a collection of interconnected nodes. This class maintains
 * a set of nodes, ensuring that only connected nodes can be added to the
 * collection.
 */
public class InnerConnectedNodes {
    
    /**
     * A set containing nodes that are interconnected. Ensures that only nodes with valid
     * connections are included. Nodes in this set must have an established relationship
     * with at least one other node already present in the set.
     */
    private Set<Node> connectedNodes;



    /**
     * Initializes an empty collection of interconnected nodes.
     * The connectedNodes set is instantiated as a new HashSet.
     */
    public InnerConnectedNodes() {
        this.connectedNodes = new HashSet<>();
    }


    /**
     * Retrieves the set of nodes that are currently connected.
     *
     * @return a Set containing the nodes that are part of the connected nodes collection.
     */
    public Set<Node> getConnectedNodes() {
        return connectedNodes;
    }


    /**
     * Returns the size of the collection of connected nodes.
     * If there are no nodes, returns 0.
     *
     * @return the number of nodes in the collection, or 0 if the collection is null or empty.
     */
    public int getSize() {
        return connectedNodes != null ? connectedNodes.size() : 0;
    }

    /**
     * Checks whether the specified node is present in the set of connected nodes.
     *
     * @param node the Node to be checked for presence in the connected nodes
     * @return true if the specified node exists within the connected nodes set, false otherwise
     */
    public boolean containsNode(Node node) {
        if (connectedNodes != null) {
            return connectedNodes.contains(node);
        }
        return false;
    }

    /**
     * Checks whether the specified node has a connection to any of the nodes
     * in the current set of connected nodes.
     *
     * @param node the node to check for a connection with the connected nodes
     * @return true if the specified node is connected to any of the nodes in the set,
     *         false otherwise
     */
    public boolean containsConnection(Node node) {
        if (connectedNodes != null) {
            for (Node connectedNode : connectedNodes) {
                if (connectedNode.isConnected(node) || node.isConnected(connectedNode)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Adds a node to the set of connected nodes if it meets the necessary conditions.
     * A node can be added if the set is empty or if it is connected to at least one
     * existing node in the set.
     *
     * @param node the node to be added
     * @return true if the node was successfully added, false otherwise
     */
    public boolean addNode(Node node) {
        if(this.getSize() == 0 || containsConnection(node)){
            return connectedNodes.add(node);
        }
        return false;
    }


    /**
     * Returns a string representation of the InnerConnectedNodes object.
     * The string includes all connected nodes in the collection.
     *
     * @return a string representation of this object and its connected nodes.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("InnerConnectedNodes{connectedNodes=[");
        if (connectedNodes != null && !connectedNodes.isEmpty()) {
            for (Node node : connectedNodes) {
                sb.append(node.toString()).append(", ");
            }
            sb.setLength(sb.length() - 2); // Remove trailing comma and space
        }
        sb.append("]}");
        return sb.toString();
    }
}
