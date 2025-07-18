package score;

import score.calculation.*;

public class ClassicScoreManager extends ScoreManager {

    protected void initScoreCalculatorMap(){
        ScoreCalculator borderConnectedPointsCalculator = new NetworksConnectedCalculator();
        ScoreCalculator roadCalculator = new LongestRoadCalculatorWithoutStations();
        ScoreCalculator railCalculator = new LongestRailCalculatorWithoutStations();
        ScoreCalculator falseRouteCalculator = new FalseRouteCalculatorWithoutBoardNodes();
        ScoreCalculator centralCellsCalculator = new CentralCellsCalculator();

        scoreCalculatorMap.put(borderConnectedPointsCalculator.getId(),borderConnectedPointsCalculator);
        scoreCalculatorMap.put(roadCalculator.getId(), roadCalculator);
        scoreCalculatorMap.put(railCalculator.getId(), railCalculator);
        scoreCalculatorMap.put(falseRouteCalculator.getId(), falseRouteCalculator);
        scoreCalculatorMap.put(centralCellsCalculator.getId(), centralCellsCalculator);
    }
}
