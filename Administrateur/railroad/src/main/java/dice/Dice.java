package dice;

import face.AbstractFace;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Dice Class
 */
public abstract class Dice {

    /**
     * List of faces on the die.
     */
    protected final List<AbstractFace> faces;

    /**
     *  Random number generator used for rolling the die.
     */
    protected final Random random;

    /**
     * The number of throws allowed for this die.
     */
    protected int numberOfThrows;

    /**
     * Constructs of the dice
     *
     * @param nbThrows the number of times the die can be thrown
     */
    protected Dice(int nbThrows) {
        this.faces = new ArrayList<>();
        this.random = new Random();
        this.numberOfThrows = nbThrows;
        initializeFaces();
    }

    /**
     * Rolls the die and returns a randomly selected face.
     *
     * @return the {@code AbstractFace} that appears after rolling the die
     */
    public AbstractFace roll() {
        int index = random.nextInt(faces.size());
        return faces.get(index);
    }

    /**
     * Returns a list of all faces on the die.
     *
     * @return a copy of the list of {@code AbstractFace} objects
     */
    public List<AbstractFace> getFaces() {
        return new ArrayList<>(faces);
    }

    /**
     * Gets the number of throws allowed for this die.
     *
     * @return the number of throws
     */
    public int getNumberOfThrows() {
        return this.numberOfThrows;
    }

    /**
     * Abstract method to initialize the faces of the die.
     * Subclasses must implement this method to define the specific faces.
     */
    protected abstract void initializeFaces();
}
