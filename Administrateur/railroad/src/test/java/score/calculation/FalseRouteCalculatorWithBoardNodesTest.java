package score.calculation;

import junit.framework.TestCase;

public class FalseRouteCalculatorWithBoardNodesTest extends FalseRouteCalculatorTest{

    protected FalseRouteCalculator createCalculator() {
        return new FalseRouteCalculatorWithBoardNodes();
    }

    protected double expectedValue1() {
        return 2.0;
    }
    protected double expectedValue2() {
        return 1.0;
    }
}
