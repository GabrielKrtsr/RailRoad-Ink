package commands;

import interpreter.Command;
import util.Message;

public class Scoreround extends Command {

    /**
     * Represents the number of arguments needed in the message associated
     */
    private static final int ARGS = 3;

    /**
     * Constructor of the class
     */
    public Scoreround() {
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
