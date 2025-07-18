package util;

import board.Board;
import cell.Cell;
import face.AbstractFace;
import interpreter.RotationFactory;
import rotationStrategy.RotationStrategy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PossibilitiesForAllFaces {

    public static Map<Tuple<AbstractFace, RotationStrategy>, List<Cell>> getAllPossibilities(List<AbstractFace> faces, Board board) {
        Map<Tuple<AbstractFace, RotationStrategy>, List<Cell>> map = new HashMap<>();

        for (AbstractFace face : faces) {
            for (RotationStrategy rotation : RotationFactory.getStrategies().values()) {
                AbstractFace rotatedFace = face;
                rotatedFace.rotate(rotation);

                List<Cell> cells = board.getAvailableCells(rotatedFace);
                rotatedFace.rotate(rotation.getOppositeRotation());
                if (!cells.isEmpty()) {
                    map.put(new Tuple<>(rotatedFace, rotation), cells);
                }
            }
        }
        return map;
    }
}
