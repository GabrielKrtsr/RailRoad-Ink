package commands;

import interpreter.Command;
import util.Message;
import util.State;

/**
 * Represents the class associated with LEAVES message
 */
public class Leaves extends Command {

    /**
     * Represents the number of arguments needed in the message associated
     */
    private static final int ARGS = 0;

    /**
     * Constructor of the class
     */
    public Leaves() {
        super(ARGS);
    }

    /**
     * Impact of this action when we receive the associated message
     */
    @Override
    public boolean execute(Message message) {
        message.getAuthor().updateState(State.PASSIVE);
        System.out.println(message.getAuthor().getId() + " is now passive !");
        return true;
    }
    
}
