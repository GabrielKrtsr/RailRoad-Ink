package score.calculation;

import board.Board;
import cell.Cell;

import java.util.function.BiFunction;


/**
 * CentralCellsCalculator is a specific implementation of the ScoreCalculator
 * interface. It focuses on calculating the score based on the central cells
 * of a board. The score is determined by counting the number of central cells
 * that have a non-null face. Additionally, it provides a customizable operation
 * for combining score components.
 */
public class CentralCellsCalculator implements ScoreCalculator{

    /**
     * A unique identifier representing the score calculation strategy for
     * the CentralCellsCalculator class. This identifier is used to differentiate
     * this specific strategy among other implementations of the ScoreCalculator interface.
     */
    private final static String ID = "C";
    /**
     * Represents an operation used to combine two double values into a single result.
     * This operation is implemented using a BiFunction that takes two Double parameters
     * as input and produces a Double output. It is utilized to define custom logic
     * for score combination or calculation strategies.
     */
    private BiFunction<Double,Double,Double> operation;



    /**
     * Initializes an instance of CentralCellsCalculator with a default operation
     * for combining score components. This class implements the ScoreCalculator
     * interface and is responsible for calculating scores based on central cells
     * on a board.
     */
    public CentralCellsCalculator() {

        this.operation = (a, b) -> a + b;
    }


    /**
     * Calculates the score based on the number of central cells in the board that have a non-null face.
     *
     * @param board the board containing cells to evaluate
     * @return the score as a double, representing the count of central cells with a non-null face
     */
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
     * Retrieves the operation used for combining two score components.
     *
     * @return a BiFunction that takes two Double inputs and produces a Double,
     *         representing the logic for combining score values.
     */
    public BiFunction<Double, Double, Double> getOperation() {
        return this.operation;
    }

    /**
     * Retrieves the unique identifier of the score calculation strategy.
     *
     * @return a string representing the unique identifier of this calculation strategy.
     */
    @Override
    public String getId() {
        return ID;
    }
}
