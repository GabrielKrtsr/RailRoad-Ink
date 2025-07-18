package commands;

import interpreter.Command;
import main.Game;
import util.Message;

/**
 * Represents the class associated with AGREES message
 */
public class Agrees extends Command {

    /**
     * Represents the number of arguments needed in the message associated
     */
    private static final int ARGS = -1;

    /**
     * Constructor of the class
     */
    public Agrees() {
        super(ARGS);
    }

    /**
     * Impact of this action when we receive the associated message
     * @param message The message received
     */
    @Override
    public boolean execute(Message message) {
        return true;
    }
    
}
