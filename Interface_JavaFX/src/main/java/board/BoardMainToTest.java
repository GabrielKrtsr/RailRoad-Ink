package board;

import cell.Cell;
import face.*;

import rotationStrategy.LeftRotation;
import rotationStrategy.RightRotation;
import rotationStrategy.RotationStrategy;
import util.PossibilitiesForAllFaces;
import util.Tuple;
import util.util_for_nodes.Side;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BoardMainToTest {

    public static void main(String[] args) throws CloneNotSupportedException {
        Board board = new BoardGame();
        board.displayConsole();
        AbstractFace f = new HighwayFace();

        List<AbstractFace> faces = new ArrayList<>();
        faces.add(f);

        Map<Tuple<AbstractFace, RotationStrategy>, List<Cell>> possibilities = PossibilitiesForAllFaces.getAllPossibilities(faces, board);

        for (Map.Entry<Tuple<AbstractFace, RotationStrategy>, List<Cell>> entry : possibilities.entrySet()) {
            Tuple<AbstractFace, RotationStrategy> key = entry.getKey();
            AbstractFace face = key.getType1();
            RotationStrategy rotationStrategy = key.getType2();

            System.out.println("Face class: " + face.getClass());
            System.out.println("Rotation strategy class: " + rotationStrategy.getClass());
            for(Cell cell: possibilities.get(key)){
                int i = board.getPointOfCell(cell).getType1();
                int j = board.getPointOfCell(cell).getType2();

                System.out.println("Cell coordinates: i = " + i + ", j = " + j);
            }
        }

        System.out.println(board.getGraph().getVertices());


    }
}
