package commands;

import interpreter.Command;
import interpreter.CommandFactory;
import main.Game;
import main.Player;
import util.Message;
import util.State;

/**
 * Represents the class associated with ENTERS message
 */
public class Enters extends Command {

    /**
     * Represents the number of arguments needed in the message associated
     */
    private static final int ARGS = 0;

    /**
     * Constructor of the class
     */
    public Enters() {
        super(ARGS);
    }   

    /**
     * Impact of this action when we receive the associated message
     * @param message The message received
     */
    @Override
    public boolean execute(Message message) {
        if(message.getAuthor() == null) {
            
            Player player = new Player(message.getContent().split(" ")[0]);

            player.addGrantedCommand(CommandFactory.getCommand("ENTERS").getClass());
            player.addGrantedCommand(CommandFactory.getCommand("LEAVES").getClass());
            player.addGrantedCommand(CommandFactory.getCommand("SCORES").getClass());
            player.addGrantedCommand(CommandFactory.getCommand("BLAMES").getClass());
            player.addGrantedCommand(CommandFactory.getCommand("GRANTS").getClass());
            player.addGrantedCommand(CommandFactory.getCommand("AGREES").getClass());
            player.addGrantedCommand(CommandFactory.getCommand("ELECTS").getClass());

            Game.getCurrentGame().addPlayer(player);
            message.setAuthor(player);
            System.out.println(player.toString());
        } else {
            message.getAuthor().updateState(State.ACTIVE);
            System.out.println(message.getAuthor().getId() + " is back ! ");
        }
        return true;
    }

}
