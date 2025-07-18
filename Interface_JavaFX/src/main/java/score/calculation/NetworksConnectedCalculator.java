package score.calculation;

import board.Board;
import graph.ConnectivityInspector;
import graph.Edge;
import graph.Graph;
import util.util_for_nodes.Node;

import java.util.*;
import java.util.function.BiFunction;

/**
 * Calculates points based on the number of border-connected networks in a graph.
 * It uses a connectivity inspector to find connected components and assigns points based on the number of exits.
 */
public class NetworksConnectedCalculator implements ScoreCalculator{
    /**
     * A unique identifier for the `NetworksConnectedCalculator` class.
     * This ID is used to distinguish this score calculation implementation
     * from others within the `ScoreCalculator` interface.
     */
    private final static String ID = "N";

    /**
     * A mapping that associates the number of exits in a network with corresponding point values.
     * Keys represent the count of exits in a connected network, while values indicate the points awarded for that count.
     * This map is initialized with predefined exit-to-point mappings used to calculate scores
     * in network-based graph evaluations.
     */
    private final Map<Integer, Integer> numberOfPoints;
    /**
     * Represents an operation used to compute a result based on two {@code Double}
     * values. The operation is defined as a {@link BiFunction} and allows for flexible
     * customization of the calculation logic.
     *
     * The operation's purpose is to process inputs and return a resultant value,
     * which can be used for scoring or other numerical computations.
     */
    private BiFunction<Double,Double,Double> operation;

    /**
     * Constructor for NetworksConnectedCalculator.
     * Initializes the mapping of the number of exit nodes to the corresponding score
     * and sets the default operation to a summation of two values.
     */
    public NetworksConnectedCalculator() {
        this.numberOfPoints = new HashMap<>();
        this.initNumberOfPoints();
        this.operation = (a, b) -> a + b;
    }

    /**
     * Initializes the mapping of the number of exits to their corresponding scores.
     * This method populates a predefined mapping where the key represents the
     * number of exits (connections to the border of a graph) and the value represents
     * the score associated with that number of exits.
     *
     * The scores are pre-defined as follows:
     * - 1 exit: 2 points
     * - 2 exits: 4 points
     * - 3 exits: 8 points
     * - 4 exits: 12 points
     * - 5 exits: 16 points
     * - 6 exits: 20 points
     * - 7 exits: 24 points
     *
     * This mapping is used within the class to calculate the total score based on the
     * connectivity of a graph's components and the number of exits in each component.
     */
    private void initNumberOfPoints(){
        numberOfPoints.put(0, 0);
        numberOfPoints.put(1, 0);
        numberOfPoints.put(2, 4);
        numberOfPoints.put(3, 8);
        numberOfPoints.put(4, 12);
        numberOfPoints.put(5, 16);
        numberOfPoints.put(6, 20);
        numberOfPoints.put(7, 24);
    }

    /**
     * Calculates the score based on the number of exit nodes in connected components of the graph
     * representing the board. Points are assigned according to a predefined mapping of exit counts
     * to scores.
     *
     * @param board The board object containing the graph representation of nodes and edges.
     * @return The total score calculated based on exit node connections.
     */
    public double calculateScore(Board board) {
        int totalPoints = 0;
        Graph<Set<Node>, Edge<Set<Node>>> graph = board.getGraph();
        ConnectivityInspector<Set<Node>, Edge<Set<Node>>> inspector = new ConnectivityInspector<>(graph);
        Set<Set<Set<Node>>> networks = inspector.connectedSets();


        for (Set<Set<Node>> network : networks) {
            int exitCount = 0;
            List<Node> visited = new ArrayList<>();
            for(Set<Node> nodes: network){
                for(Node node: nodes){
                    if(node.isExitNode() && !visited.contains(node)){
                        exitCount += 1;
                        visited.add(node);
                    }

                }
            }
//            int exitCount = (int) network.stream()
//                    .flatMap(Set::stream) // Flattening the sets to access individual Nodes
//                    .filter(node -> {node.isExitNode();}) // Filtering Nodes that are exit nodes
//                    .count();

            if(this.numberOfPoints.containsKey(exitCount)){
                totalPoints += this.numberOfPoints.get(exitCount);
            }
        }
        return totalPoints;
    }

    /**
     * Retrieves the operation function used to perform calculations.
     *
     * @return a BiFunction that takes two Double arguments and returns a Double result
     */
    public BiFunction<Double, Double, Double> getOperation() {
        return this.operation;
    }

    /**
     * Retrieves the unique identifier for the calculator.
     *
     * @return a string representing the unique identifier for this calculator.
     */
    @Override
    public String getId() {
        return ID;
    }
}
