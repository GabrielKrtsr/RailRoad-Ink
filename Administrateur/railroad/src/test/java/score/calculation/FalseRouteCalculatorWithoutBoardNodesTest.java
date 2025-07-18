package score.calculation;

public class FalseRouteCalculatorWithoutBoardNodesTest extends FalseRouteCalculatorTest{

    protected FalseRouteCalculator createCalculator() {
        return new FalseRouteCalculatorWithoutBoardNodes();
    }

    protected double expectedValue1() {
        return 2.0;
    }
    protected double expectedValue2() {
        return 1.0;
    }
}
