package commands;

import interpreter.Command;
import main.Game;
import util.Message;

public class Plays extends Command {

    /**
     * Represents the number of arguments needed in the message associated
     */
    private static final int ARGS = 0;

    /**
     * Constructor of the class
     */
    public Plays() {
        super(ARGS);
    }

    /**
     * Impact of this action when we receive the associated message
     * @param message The message received
     */
    @Override
    public boolean execute(Message message) {

        new Thread(() -> {
            try {
                Game.getCurrentGame().play();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
        return true;
    }

}

