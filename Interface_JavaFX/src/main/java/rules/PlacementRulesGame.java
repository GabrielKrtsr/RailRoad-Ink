package rules;

import board.Board;
import face.AbstractFace;
import rotationStrategy.*;
import cell.Cell;
import util.Path;
import util.Tuple;
import util.util_for_nodes.Connection;
import util.util_for_nodes.Node;
import util.util_for_nodes.Side;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * PlacementRulesGame Class
 */
public class PlacementRulesGame implements PlacementRules{

    /** Array of rotation strategies used to adjust face orientation for compatibility. */
    private final RotationStrategy[] rotationStrategies;


    /** Singleton instance of the placement rules game. */
    private static PlacementRulesGame instance = null;


    /**
     * Private constructor to enforce the singleton pattern.
     * Initializes the rotation strategies available in the game.
     */
    private PlacementRulesGame(){
        this.rotationStrategies = new RotationStrategy[]{
                new UpsideDownRotation(),
                new RightRotation(),
                new LeftRotation(),
                new FlippedRotation(),
                new FlippedLeftRotation(),
                new FlippedRightRotation(),
                new FlippedUpsideDownRotation()
        };
    }

    /**
     * Retrieves the singleton instance of PlacementRulesGame.
     * If the instance does not exist, it is created.
     *
     * @return the singleton instance of PlacementRulesGame
     */
    public static PlacementRulesGame getInstance() {
        if(instance == null) {
            instance = new PlacementRulesGame();
        }
        return instance;
    }

    @Override
    public boolean canPlaceFace(int x, int y, AbstractFace face, Board board) {
        if (!isValidIndex(x, y, board)) return false;

        Cell cell = board.getCell(x, y);
        if (cell.getFace() != null) return false;
        
        //adjustOrientation(x, y, face, board);
        return isCompatible(x, y, face, board);
    }

    @Override
    public void adjustOrientation(int x, int y, AbstractFace face, Board board) {
        for (RotationStrategy strategy : rotationStrategies) {
            strategy.rotate(face);
            if (isCompatible(x, y, face, board)) {
                return;
            }
        }
    }

    @Override
    public boolean isCompatible(int x, int y, AbstractFace face, Board board) {
        if (!isValidIndex(x, y, board)) {
            return false;
        }
        Cell cell = board.getCell(x,y);
        Map<Side,Cell> neighbours = new EnumMap<>(Side.class);
        neighbours.put(Side.TOP, board.getNeighbour(x,y,Side.TOP));
        neighbours.put(Side.BOTTOM, board.getNeighbour(x,y,Side.BOTTOM));
        neighbours.put(Side.LEFT, board.getNeighbour(x,y,Side.LEFT));
        neighbours.put(Side.RIGHT, board.getNeighbour(x,y,Side.RIGHT));
        int count = 0;
        for(Side s : neighbours.keySet()){
            if(neighbours.get(s)!= null){
                //verif neighbours
                Node node = neighbours.get(s).getNode(s.getOppositeSide());
                if(node != null){
                    List<Tuple<Node ,Connection>> connections = node.getConnections().stream().collect(Collectors.toList());
                    List<Tuple<Node ,Connection>> connections2 = face.getNode(s).getConnections().stream().collect(Collectors.toList());
                    if (!connections.isEmpty() && !connections2.isEmpty() && connections2.get(0)!=null && connections.get(0) != null && connections.get(0).getType2().getPathType() == connections2.get(0).getType2().getPathType()){
                        count +=1;
                    }
                    else if(!connections.isEmpty() && !connections2.isEmpty() && connections2.get(0)!=null && connections.get(0) != null && connections.get(0).getType2().getPathType() != connections2.get(0).getType2().getPathType()){
                        return false;
                    }
                }

            }

            else {
                // verif border
                if (cell.isExit() && face.getNode(cell.getBorder().getType1()) != null){
                    List<Tuple<Node ,Connection>> connections = face.getNode(cell.getBorder().getType1()).getConnections().stream().collect(Collectors.toList());
                    if (!connections.isEmpty() && connections.get(0) != null && connections.get(0).getType2().getPathType() != cell.getBorder().getType2()){
                        return false;
                    }
                    else if (!connections.isEmpty() && connections.get(0) != null && connections.get(0).getType2().getPathType() == cell.getBorder().getType2()){
                        count +=1;
                    }
                }
            }
        }

        return count != 0;
    }




    /**
     * Checks if the given indexes are valid
     * @param x the coordinate x
     * @param y the coordinate y
     * @param board the game board
     * @return true if the indexes are valid and false if not
     * */
    private static boolean isValidIndex(int x, int y, Board board) {
        //System.out.println("\n\n(x,y) " + x + "," + y + " -> " + (x >= 0 && x < board.getCells().length && y >= 0 && y < board.getCells()[0].length) +  "\n\n");
        return x >= 0 && x < board.getCells().length && y >= 0 && y < board.getCells()[0].length && board.getCell(x,y).getFace() == null;
    }
}
