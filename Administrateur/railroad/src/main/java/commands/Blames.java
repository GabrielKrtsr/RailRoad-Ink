package commands;

import interpreter.Command;
import util.Message;

/**
 * Represents the class associated with AGREES message
 */
public class Blames extends Command {

    /**
     * Represents the number of arguments needed in the message associated
     */
    private static final int ARGS = -1;

    /**
     * Constructor of the class
     */
    public Blames() {
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
