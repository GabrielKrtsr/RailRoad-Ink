package dice;

import org.junit.jupiter.api.BeforeEach;

/**
 * Test class for RouteOrRailRoadDice, extending DiceTest.
 */
public class ClassicDiceTest extends DiceTest {

    @BeforeEach
    @Override
    protected void setUp() {
        dice = new ClassicDice();
    }

    @Override
    protected int expectedNumberOfThrows() {
        return 3;
    }
}
