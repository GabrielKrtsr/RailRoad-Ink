package commands;

import java.util.List;

import interpreter.Command;
import interpreter.CommandFactory;
import main.Game;
import main.Player;
import util.Message;

/**
 * Represents the class associated with ELECTS message
 */
public class Elects extends Command {

    /**
     * Represents the number of arguments needed in the message associated
     */
    private static final int ARGS = -1;

    /**
     * Constructor of the class
     */
    public Elects() {
        super(ARGS);
    }

    /**
     * Impact of this action when we receive the associated message
     * @param message The message received
     */
    @Override
    public boolean execute(Message message) {
        //message.getAuthor().setElects(true);
        List<String> players = message.getArgs();
        for(String arg : players) {
            Player player = Game.getCurrentGame().getPlayerById(arg);
            if(!arg.equals("admin")) {
                player.setElects(true);
            }

            player.addGrantedCommand(CommandFactory.getCommand("PLAYS").getClass());
            player.addGrantedCommand(CommandFactory.getCommand("THROWS").getClass());
            player.addGrantedCommand(CommandFactory.getCommand("PLACES").getClass());
            player.addGrantedCommand(CommandFactory.getCommand("YIELDS").getClass());
        }
        return true;
    }
    
}
