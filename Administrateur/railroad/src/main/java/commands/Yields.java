package commands;

import interpreter.Command;
import util.Message;

/**
 * Represents the class associated with YIELD message
 */
public class Yields extends Command {

    /**
     * Represents the number of arguments needed in the message associated
     */
    private static final int ARGS = 0;

    /**
     * Constructor of the class
     */
    public Yields() {
        super(ARGS);
    }

    /**
     * Impact of this action when we receive the associated message
     * @param message The message received
     */
    @Override
    public boolean execute(Message message) {
        if(message.getAuthor().getElects()&& message.getAuthor().isCanYields()){
            message.getAuthor().setPassRound(true);
        }
        return true;
    }

}
