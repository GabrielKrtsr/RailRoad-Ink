package score.calculation;

import board.Board;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.Path;
import util.Tuple;
import util.util_for_nodes.Connection;
import util.util_for_nodes.Node;
import graph.Edge;
import graph.Graph;

import java.util.HashSet;
import java.util.Set;
import java.util.function.BiFunction;

import static org.junit.jupiter.api.Assertions.*;

public abstract class  LongestPathCalculatorTest {
    protected Board board;
    protected BiFunction<Double, Double, Double> sumOperation = Double::sum;
    protected LongestPathCalculator calculatorR;
    protected LongestPathCalculator calculatorH;
    @BeforeEach
    void setUp() {
        board = new Board(3) {
            @Override
            protected void initBorderCell() {}
        };
        calculatorR = this.createCaulator(Path.RAIL);
        calculatorH = this.createCaulator(Path.ROAD);

    }

    protected abstract LongestPathCalculator createCaulator(Path path);

    protected void connectNodes(Node node1, Node node2, Path pathType) {
        // Crée une seule instance de Connection à partager
        Connection sharedConnection = new Connection(pathType);

        // Utilise la méthode addConnection de chaque node
        node1.addConnection(node2, sharedConnection);
        node2.addConnection(node1, sharedConnection);

        // Ajout au graphe (reste inchangé)
        Set<Node> set1 = new HashSet<>(Set.of(node1));
        Set<Node> set2 = new HashSet<>(Set.of(node2));
        board.getGraph().addVertex(set1);
        board.getGraph().addVertex(set2);
        board.getGraph().addEdge(new Edge<>(set1, set2, 1.0));
    }

}