package score;

import score.calculation.*;

/**
 * ClassicScoreManager is a concrete implementation of the ScoreManager class.
 * It initializes and manages a mapping of specific score calculation strategies
 * that are used to calculate scores in a board game. The strategies include
 * calculations for connected networks, longest roads, longest rails, false routes,
 * and central cells.
 */
public class ClassicScoreManager extends ScoreManager {

    /**
     * Initializes the `scoreCalculatorMap` with specific implementations of the `ScoreCalculator` interface.
     * Each implementation corresponds to a different scoring criterion used in the game. The method
     * creates instances of calculators for connected networks, longest road, longest rail, false routes,
     * and central cells, and maps their unique identifiers to the instances within the `scoreCalculatorMap`.
     *
     * The method includes the following calculator instances:
     * - `NetworksConnectedCalculator`: Calculates scores based on connected networks, specifically border-connected points.
     * - `LongestRoadCalculatorWithStations`: Calculates the longest road in a graph, accounting for stations and special cases.
     * - `LongestRailCalculatorWithStations`: Calculates the longest railway path in a graph.
     * - `FalseRouteCalculatorWithoutBoardNodes`: Calculates penalties for false routes, disregarding board nodes.
     * - `CentralCellsCalculator`: Calculates additional points for cells centrally located in a board layout.
     *
     * These calculators are added to the `scoreCalculatorMap` based on their respective unique identifiers.
     */
    protected void initScoreCalculatorMap(){
        ScoreCalculator borderConnectedPointsCalculator = new NetworksConnectedCalculator();
        ScoreCalculator roadCalculator = new LongestRoadCalculatorWithStations();
        ScoreCalculator railCalculator = new LongestRailCalculatorWithStations();
        ScoreCalculator falseRouteCalculator = new FalseRouteCalculatorWithoutBoardNodes();
        ScoreCalculator centralCellsCalculator = new CentralCellsCalculator();

        scoreCalculatorMap.put(borderConnectedPointsCalculator.getId(),borderConnectedPointsCalculator);
        scoreCalculatorMap.put(roadCalculator.getId(), roadCalculator);
        scoreCalculatorMap.put(railCalculator.getId(), railCalculator);
        scoreCalculatorMap.put(falseRouteCalculator.getId(), falseRouteCalculator);
        scoreCalculatorMap.put(centralCellsCalculator.getId(), centralCellsCalculator);
    }
}
