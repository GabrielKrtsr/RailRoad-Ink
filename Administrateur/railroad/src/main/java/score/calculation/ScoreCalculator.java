package score.calculation;

import board.Board;

import java.util.function.BiFunction;

/**
 * Represents a blueprint for score calculation strategies in board games. This interface
 * defines the methods necessary for calculating scores based on specific criteria
 * and provides an operation for combining score components.
 */
public interface ScoreCalculator {

    /**
     * Calculates the score for the given board based on specific
     * scoring criteria defined by the implementing class.
     *
     * @param board the board for which the score is to be calculated
     * @return the calculated score as a double value
     */
    public double calculateScore(Board board);
    /**
     * Retrieves the operation for combining two score components.
     *
     * @return A BiFunction that takes two Double inputs and produces a Double, representing
     *         the logic for combining score values.
     */
    public BiFunction<Double,Double,Double> getOperation();
    /**
     * Retrieves the unique identifier of the score calculation strategy.
     *
     * @return A string representing the unique ID associated with the score calculation strategy.
     */
    public String getId();
}
