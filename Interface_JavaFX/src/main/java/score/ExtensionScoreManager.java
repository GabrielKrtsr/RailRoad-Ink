package score;

import score.calculation.*;

public class ExtensionScoreManager extends ScoreManager{

    protected void initScoreCalculatorMap(){
        ScoreCalculator borderConnectedPointsCalculator = new NetworksConnectedCalculator();
        ScoreCalculator roadCalculator = new LongestRoadCalculatorWithStations();
        ScoreCalculator railCalculator = new LongestRailCalculatorWithStations();
        ScoreCalculator falseRouteCalculator = new FalseRouteCalculatorWithBoardNodes();
        ScoreCalculator centralCellsCalculator = new CentralCellsCalculator();

        scoreCalculatorMap.put(borderConnectedPointsCalculator.getId(),borderConnectedPointsCalculator);
        scoreCalculatorMap.put(roadCalculator.getId(), roadCalculator);
        scoreCalculatorMap.put(railCalculator.getId(), railCalculator);
        scoreCalculatorMap.put(falseRouteCalculator.getId(), falseRouteCalculator);
        scoreCalculatorMap.put(centralCellsCalculator.getId(), centralCellsCalculator);
    }
}
