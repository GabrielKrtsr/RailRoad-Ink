package score.calculation;

import board.Board;

import java.util.function.BiFunction;

/**
 * FalseRouteCalculator is a score calculation strategy that identifies and calculates
 * the number of false routes in a given board's graph. A false route is defined
 * based on specific criteria implemented in subclasses of this calculator.
 * This class provides a base implementation and operation for modifying derived scoring calculations.
 */
public class FalseRouteCalculator implements ScoreCalculator{
    /**
     * A constant identifier for the FalseRouteCalculatorWithoutBoardNodes class.
     * Used to uniquely identify the type of this score calculator.
     */
    private final static String ID = "X";

    /**
     * Represents a mathematical operation that takes two Double inputs and produces a Double output.
     * This operation is used for calculations within the class.
     * The specific implementation of this operation may vary but is defined during class construction.
     */
    private BiFunction<Double,Double,Double> operation;





    /**
     * Constructs a FalseRouteCalculatorWithoutBoardNodes with the given graph.
     */
    public FalseRouteCalculator() {

        this.operation = (a, b) -> a - b;
    }

    /**
     * Counts the number of false routes in the graph.
     *
     * @return The total count of false routes.
     */
    public double calculateScore(Board board) {
        return 0;
    }
    /**
     * Returns the operation function used for calculations.
     *
     * @return A BiFunction representing the operation.
     */
    public BiFunction<Double, Double, Double> getOperation() {
        return this.operation;
    }

    /**
     * Retrieves the unique identifier of the score calculation strategy.
     *
     * @return A string representing the unique ID of this score calculator.
     */
    @Override
    public String getId() {
        return ID;
    }
}
