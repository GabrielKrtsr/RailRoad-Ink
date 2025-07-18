package score.calculation;

public class LongestRoadCalculatorWithStationTest extends LongestRoadCalculatorTest {



    @Override
    protected LongestRoadCalculator createCalculator() {
        return new LongestRoadCalculatorWithStations();
    }
}
