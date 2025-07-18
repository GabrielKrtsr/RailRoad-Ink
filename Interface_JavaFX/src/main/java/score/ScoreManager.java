package score;

import board.Board;
import score.calculation.ScoreCalculator;

import java.util.HashMap;
import java.util.Map;

/**
 * Manages the scoring system for the board game, calculating the final score based on various factors.
 */
public abstract class ScoreManager {

    /**
     * A mapping between unique identifiers and their corresponding score calculation strategies.
     *
     * Each key in this map is a unique identifier (String) associated with a specific implementation
     * of the {@code ScoreCalculator} interface. The value is the instance of the {@code ScoreCalculator}
     * that defines the logic for calculating a specific type of score.
     *
     * This map is used to dynamically apply different score calculation strategies during
     * the final score computation in the board game, based on the associated identifiers.
     */
    protected Map<String, ScoreCalculator> scoreCalculatorMap;


    /**
     * Constructor for the ScoreManager class.
     * Initializes the mapping of scoring calculators by creating a new HashMap
     * and populating it with applicable calculators through the initScoreCalculatorMap method.
     */
    public ScoreManager() {
        this.scoreCalculatorMap = new HashMap<>();
        initScoreCalculatorMap();


    }

    /**
     * Initializes the scoreCalculatorMap with various scoring strategies by associating
     * unique identifiers with their corresponding ScoreCalculator implementations.
     *
     * This method creates and adds the following score calculation strategies:
     * - NetworksConnectedCalculator: Calculates points based on border-connected networks.
     * - LongestRoadCalculatorWithoutStations: Calculates points for the longest road in the game.
     * - LongestRailCalculatorWithoutStations: Calculates points for the longest rail in the game.
     * - FalseRouteCalculatorWithoutBoardNodes: Calculates penalties for false routes in the game.
     *
     * These ScoreCalculator instances are mapped using their unique IDs obtained from
     * the getId method of each ScoreCalculator implementation.
     */
    protected void initScoreCalculatorMap(){

    }

    /**
     * Calculates the final score for the given board by applying various scoring methods
     * contained in the scoreCalculatorMap. Each scoring method contributes to the final
     * score based on its calculation logic and the defined operation.
     *
     * @param board the game board for which the final score is to be calculated
     * @return the calculated final score as a double value
     */
    public double calculateFinalScore(Board board) {
        double res = 0;
        for (String id : scoreCalculatorMap.keySet()){
            ScoreCalculator scoreCalculator = scoreCalculatorMap.get(id);
            double nbPoints = scoreCalculator.calculateScore(board);
            res = scoreCalculator.getOperation().apply(res,nbPoints);

        }

        return res;
    }


    /**
     * Retrieves the scoreCalculatorMap containing the mapping between unique
     * identifiers and their corresponding score calculation strategies.
     *
     * @return the scoreCalculatorMap as a Map<String, ScoreCalculator>
     */
    public Map<String, ScoreCalculator> getScoreCalculatorMap() {
        return this.scoreCalculatorMap;
    }
}
