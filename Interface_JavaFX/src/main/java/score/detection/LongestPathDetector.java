package score.detection;

import board.Board;
import cell.Cell;
import util.Path;
import util.util_for_nodes.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public interface LongestPathDetector {

    static List<Cell> detectLongestPath(Board board, Path path){
        return new ArrayList<>();
    }

    private static double findLongestPathWithCells(Node node, Set<Node> visited, Path path, List<Cell> currentPathCells, Board board) {
        return 0;
    }
}
