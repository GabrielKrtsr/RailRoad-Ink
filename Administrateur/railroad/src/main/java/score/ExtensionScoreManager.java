package score;

import score.calculation.*;

/**
 * ExtensionScoreManager is an extension of the ScoreManager class that manages an
 * advanced mapping of scoring strategies for a board game. It provides specific
 * implementations for score calculation strategies, assigning their unique identifiers
 * and calculation logics.
 *
 * This class defines an extended set of scoring strategies compared to its parent class, ScoreManager,
 * and initializes additional calculators tailored for more complex gameplay scenarios.
 */
public class ExtensionScoreManager extends ScoreManager{

    /**
     * Initializes and populates the `scoreCalculatorMap` with specific score calculation strategies.
     * Each strategy implements the `ScoreCalculator` interface and is identified by a unique ID.
     *
     * The method creates instances of multiple score calculation strategies such as:
     * - `NetworksConnectedCalculator`: Calculates points based on the number of border-connected networks.
     * - `LongestRoadCalculatorWithoutStations`: Calculates the length of the longest road path excluding stations.
     * - `LongestRailCalculatorWithoutStations`: Calculates the length of the longest rail path excluding stations.
     * - `FalseRouteCalculatorWithBoardNodes`: Calculates penalties or scores based on incorrect routes.
     * - `CentralCellsCalculator`: Calculates scores for central cell nodes within the game board.
     *
     * After instantiation, each calculator is added to the `scoreCalculatorMap` using its unique ID
     * as the key. This allows for dynamic retrieval and utilization of specific scoring logic
     * based on gameplay requirements.
     */
    protected void initScoreCalculatorMap(){
        ScoreCalculator borderConnectedPointsCalculator = new NetworksConnectedCalculator();
        ScoreCalculator roadCalculator = new LongestRoadCalculatorWithoutStations();
        ScoreCalculator railCalculator = new LongestRailCalculatorWithoutStations();
        ScoreCalculator falseRouteCalculator = new FalseRouteCalculatorWithBoardNodes();
        ScoreCalculator centralCellsCalculator = new CentralCellsCalculator();

        scoreCalculatorMap.put(borderConnectedPointsCalculator.getId(),borderConnectedPointsCalculator);
        scoreCalculatorMap.put(roadCalculator.getId(), roadCalculator);
        scoreCalculatorMap.put(railCalculator.getId(), railCalculator);
        scoreCalculatorMap.put(falseRouteCalculator.getId(), falseRouteCalculator);
        scoreCalculatorMap.put(centralCellsCalculator.getId(), centralCellsCalculator);
    }
}
