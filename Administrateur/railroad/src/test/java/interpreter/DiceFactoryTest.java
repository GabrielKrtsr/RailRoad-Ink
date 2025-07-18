package interpreter;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import dice.Dice;
import java.util.List;

class DiceFactoryTest {

    @BeforeAll
    static void setup() {
        new DiceFactory();
    }

    @Test
    void testGetDicesNotEmpty() {
        List<Dice> dices = DiceFactory.getDices();
        assertNotNull(dices, "La liste de dés ne doit pas être null");
        assertFalse(dices.isEmpty(), "La liste de dés ne doit pas être vide si des dés existent");
    }
}
