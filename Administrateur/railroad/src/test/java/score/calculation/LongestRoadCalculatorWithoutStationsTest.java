package score.calculation;

public class LongestRoadCalculatorWithoutStationsTest extends LongestRoadCalculatorTest {



    @Override
    protected LongestRoadCalculator createCalculator() {
        return new LongestRoadCalculatorWithoutStations();
    }
}
