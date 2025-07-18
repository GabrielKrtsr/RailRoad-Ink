package util.util_for_nodes;


import util.Path;

/**
 * Represents a connection between entities such as nodes in a graph or board-like structure.
 * A connection is characterized by its path type which indicates whether the path is a road or a railway.
 */
public class Connection {

    /**
     * Represents the type of path associated with the connection.
     * The path can either be of type RAIL or ROAD as defined in the {@link Path} enum.
     */
    private Path pathType;

    /**
     * Constructs a new Connection instance with the specified path type.
     *
     * @param pathType the type of path associated with the connection (e.g., ROAD or RAIL)
     */
    public Connection(Path pathType) {
        this.pathType = pathType;
    }

    /**
     * Retrieves the type of path associated with this connection.
     *
     * @return the path type of this connection, represented as an instance of the {@code Path} enum.
     */
    public Path getPathType() {
        return pathType;
    }

    /**
     * Sets the path type for this connection.
     *
     * @param pathType the path type to set. It can be either RAIL or ROAD.
     */
    public void setPathType(Path pathType) {
        this.pathType = pathType;
    }

    /**
     * Returns a string representation of the Connection object.
     * The string includes the type of path associated with the connection.
     *
     * @return a string representation of the Connection object, including its path type.
     */
    @Override
    public String toString() {
        return "Connection{" +
                "pathType=" + pathType +
                '}';
    }
}
