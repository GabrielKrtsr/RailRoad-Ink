package dice;

import face.*;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for ConnectionsDice, extending DiceTest.
 */
public class ConnectionsDiceTest extends DiceTest {

    @BeforeEach
    @Override
    protected void setUp() {
        dice = new ConnectionsDice();
    }

    @Override
    protected int expectedNumberOfThrows() {
        return 1;
    }
}
