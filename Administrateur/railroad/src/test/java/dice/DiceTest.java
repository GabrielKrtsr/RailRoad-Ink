package dice;

import face.AbstractFace;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Base test class for Dice. Concrete subclasses should extend this class
 * to test specific implementations of Dice.
 */
public abstract class DiceTest {

    protected Dice dice;

    /**
     * Creates a new instance of a Dice subclass before each test.
     * Concrete subclasses must override this method.
     */
    @BeforeEach
    protected abstract void setUp();

    @Test
    public void testGetNumberOfThrows() {
        assertEquals(expectedNumberOfThrows(), dice.getNumberOfThrows());
    }

    @Test
    public void testRollReturnsValidFace() {
        AbstractFace result = dice.roll();
        assertNotNull(result);
        assertTrue(dice.getFaces().contains(result));
    }

    @Test
    public void testGetFaces() {
        List<AbstractFace> faces = dice.getFaces();
        assertNotNull(faces);
        assertFalse(faces.isEmpty());
    }


    protected abstract int expectedNumberOfThrows();
}
