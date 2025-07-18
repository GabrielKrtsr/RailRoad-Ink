package commands;

import java.util.List;
import interpreter.Command;
import interpreter.CommandFactory;
import main.Game;
import main.Player;
import util.Message;

/**
 * Represents the class associated with GRANTS message
 */
public class Grants extends Command {

    /**
     * Represents the number of arguments needed in the message associated
     */
    private static final int ARGS = 2;
 
    /**
     * Constructor of the class
     */
     public Grants() {
        super(ARGS);
    }

    /**
     * Impact of this action when we receive the associated message
     * @param message The message received
     */
    @Override
    public boolean execute(Message message) {
        message.getAuthor().addGrantedCommand(message.getCommand().getClass());
        Player enable = Game.getCurrentGame().getPlayerById(message.getArgs().get(0));
        List<Player> disable = Game.getCurrentGame().getPlayers();
        disable.remove(enable);
        for(Player player : disable) {
            player.removeGrantedCommand(CommandFactory.getCommand(message.getArgs().get(1)).getClass());
        }
        return true;
    }
    
}
