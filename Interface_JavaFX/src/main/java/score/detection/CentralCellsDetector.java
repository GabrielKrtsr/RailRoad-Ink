package score.detection;

import board.Board;
import cell.Cell;

import java.util.ArrayList;
import java.util.List;

public class CentralCellsDetector {

    public static List<Cell> detectCentralCellsWithFaces(Board board){
        List<Cell> cells = new ArrayList<>();
        for(Cell [] cellLine : board.getCells()){
            for(Cell cell : cellLine){
                if(cell.isCentralCell() && cell.getFace() != null){
                    cells.add(cell);
                }
            }
        }

        return cells;
    }
}
