package score.calculation;

import board.Board;
import cell.Cell;

import java.util.function.BiFunction;


public class CentralCellsCalculator implements ScoreCalculator{

    private final static String ID = "C";
    private BiFunction<Double,Double,Double> operation;



    public CentralCellsCalculator() {

        this.operation = (a, b) -> a + b;
    }


    public double calculateScore(Board board) {
        double count = 0;
        for(Cell [] cellLine : board.getCells()){
            for(Cell cell : cellLine){
                if(cell.isCentralCell() && cell.getFace() != null){
                    count++;
                }
            }
        }
        return count;
    }
    /**
     * Returns the operation function used for calculations.
     *
     * @return A BiFunction representing the operation.
     */
    public BiFunction<Double, Double, Double> getOperation() {
        return this.operation;
    }

    @Override
    public String getId() {
        return ID;
    }
}
