package graph;


/**
 * Represents an edge in a graph with a source vertex, a target vertex,
 * and an associated weight. This class is generic and can work with
 * various types of vertices.
 *
 * @param <V> the type of the vertices (source and target) in the edge
 */
public class Edge<V> {
    /**
     * The source vertex of the edge. Represents the starting point
     * of the edge in a graph.
     */
    private final V source;
    /**
     * The target vertex of this edge. This defines the destination or endpoint
     * of the directed connection represented by the edge. The type of the vertex
     * is defined by the generic parameter V.
     */
    private final V target;
    /**
     * Represents the weight associated with the edge. The weight signifies
     * the cost, distance, or any other metric used to define the relation
     * between the source and target vertices.
     *
     * This value is immutable and is assigned during the construction of
     * the edge instance.
     */
    private final double weight;

    /**
     * Constructs an Edge representing a connection between two vertices with a specified weight.
     *
     * @param source the starting vertex of the edge
     * @param target the ending vertex of the edge
     * @param weight the weight or cost associated with this edge
     */
    public Edge(V source, V target, double weight) {
        this.source = source;
        this.target = target;
        this.weight = weight;
    }

    /**
     * Retrieves the source vertex of this edge.
     *
     * @return the source vertex of type V
     */
    public V getSource() {
        return source;
    }

    /**
     * Retrieves the target vertex of this edge.
     *
     * @return the target vertex of this edge
     */
    public V getTarget() {
        return target;
    }

    /**
     * Retrieves the weight associated with this edge.
     *
     * @return the weight of this edge
     */
    public double getWeight() {
        return weight;
    }

    /**
     * Returns a string representation of the edge, including its source vertex,
     * target vertex, and weight. The format is "Edge[source=sourceVertex, target=targetVertex, weight=weightValue]".
     *
     * @return a string describing the edge with its source, target, and weight
     */
    @Override
    public String toString() {
        return String.format("Edge[source=%s, target=%s, weight=%f]", source, target, weight);
    }

    /**
     * Compares this object to the specified object for equality.
     * The method checks if the specified object is of the same class and has the same attributes.
     *
     * @param obj the object to be compared for equality
     * @return true if the specified object is equal to this object, otherwise false
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Edge<?> edge = (Edge<?>) obj;

        if (Double.compare(edge.weight, weight) != 0) return false;
        if (!source.equals(edge.source)) return false;
        return target.equals(edge.target);
    }

    /**
     * Computes the hash code for this edge instance based on its source,
     * target, and weight properties.
     *
     * @return an integer hash code representing this edge
     */
    @Override
    public int hashCode() {
        int result;
        long temp;
        result = source.hashCode();
        result = 31 * result + target.hashCode();
        temp = Double.doubleToLongBits(weight);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
