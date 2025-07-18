package commands;

import java.util.ArrayList;
import java.util.List;

import face.AbstractFace;
import interpreter.Command;
import interpreter.FaceFactory;
import main.Game;
import main.Player;
import util.Message;
import main.Round;

/**
 * Represents the class associated with THROWS message
 */
public class Throws extends Command {

    /**
     * Represents the number of arguments needed in the message associated
     */
    private static final int ARGS = -1;

    /**
     * Constructor of the class
     */
    public Throws() {
        super(ARGS);
    }

    /**
     * Impact of this action when we receive the associated message
     * @param message The message received
     */
    @Override
    public boolean execute(Message message) {
        if(message.getAuthor().getElects() || message.getAuthor().isAdmin()) {

            List<Player> players = Game.getCurrentGame().getElectedPlayers();

            if(players.size() == 0) {
                return false;
            }


            if(Game.getCurrentGame().getCurrentRound() != null) {
                for(Player player : players) {
                    if(!player.needToPlay() || !player.canPlay()) {
                        return false;
                    }
                }
            }

            Round round = Game.getCurrentGame().newRound();

            for(String arg : message.getArgs()) {
                try {
                    round.addFace((AbstractFace)FaceFactory.getFace(arg).clone());
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
            }
            try {
                round.distributeFaces(players);
            }catch (Exception e){
                e.getMessage();
            }

            return true;

        }

        return false;
        
    }


}