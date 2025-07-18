package score.calculation;

public class LongestRailCalculatorWithBranchesTest extends LongestRailCalculatorTest {
    @Override
    protected LongestRailCalculator createCaculator() {
        return new LongestRailCalculatorWithBranches();
    }

    @Override
    protected double expectedValue1() {
        return 2.0;
    }

    @Override
    protected double expectedValue2() {
        return 1.0;
    }

    @Override
    protected double expectedValue3() {
        return 2.0;
    }

    @Override
    protected double expectedValue4() {
        return 2.0;
    }
}
