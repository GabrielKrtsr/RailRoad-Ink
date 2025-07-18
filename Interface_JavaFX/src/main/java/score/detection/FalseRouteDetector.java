package score.detection;

import board.Board;
import cell.Cell;
import util.util_for_nodes.Node;
import util.util_for_nodes.Side;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface FalseRouteDetector {

     static Map<Cell, List<Node>> detectFalseRoutesWithNodes(Board board){
         return new HashMap<>();
     }

     private static Map<Cell, List<Side>> detectFalseRoutesWithSides(Board board){
         return new HashMap<>();
     }
}
