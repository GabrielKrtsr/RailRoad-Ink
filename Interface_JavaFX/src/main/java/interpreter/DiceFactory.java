package interpreter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.reflections.Reflections;

import dice.Dice;


/**
 * Factory class for managing dice instances.
 */
public class DiceFactory {

    /**
     * A list storing all instantiated dice objects.
     */
    private static final List<Dice> dices = new ArrayList<>();

    static {
        Reflections reflections = new Reflections("dice");
        Set<Class<? extends Dice>> dicesClasses = reflections.getSubTypesOf(Dice.class);

        for (Class<? extends Dice> diceClasses : dicesClasses) {
            try {
                Dice dice = diceClasses.getDeclaredConstructor().newInstance();
                dices.add(dice);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Function to get a dices of the game
     *
     * @return the list of dices to play
     */
    public static List<Dice> getDices() {
        return dices;
    }
    
}
