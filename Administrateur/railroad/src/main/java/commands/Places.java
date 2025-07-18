package commands;

import client.Client;
import face.AbstractFace;
import interpreter.Command;
import interpreter.RotationFactory;
import main.Game;
import main.Player;
import score.ClassicScoreManager;
import score.ScoreManager;
import util.Message;
import util.Type;

/**
 * Represents the class associated with PLACES message
 */
public class Places extends Command {

    /**
     * Represents the number of arguments needed in the message associated
     */
    private static final int ARGS = 3;

    /**
     * Constructor of the class
     */
    public Places() {
        super(ARGS);
    }

    /**
     * Impact of this action when we receive the associated message
     * @param message The message received
     */
    @Override
    public boolean execute(Message message) {
        String tile = message.getArgs().get(0);
        String orientation = message.getArgs().get(1);
        String cell = message.getArgs().get(2).toUpperCase();
        message.getAuthor().setCanYields(true);
        int y = (int)((int) cell.charAt(0)) - 65;
        int x = Integer.valueOf(cell.substring(1,cell.length())) - 1;
        System.out.println("The Player : " + message.getAuthor().getId() + " want to place a face in the position : " + x + "," + y);
        AbstractFace face = message.getAuthor().getCellById(tile);
        if(face == null){
            face = message.getAuthor().getCellSpecialById(tile);
        }
        if((face != null && face.getType() == Type.SPECIAL && message.getAuthor().getSpecialsTilesPlacedThisRound() < 1 && message.getAuthor().getSpecialTilesPlaced()<3) || (face != null &&  face.getType() == Type.CLASSIC && message.getAuthor().getTilesPlacedThisRound() < 4)) {
            if(!orientation.contentEquals("null")) {
                RotationFactory.getStrategy(orientation).rotate(face);
            }
            if(message.getAuthor().getBoard().placeFaceInTheCell(x,y,face)) {
                if (face.getType() == Type.SPECIAL){
                    message.getAuthor().removeFacesSpecial(face);
                    message.getAuthor().addSpecialTilesPlacedThisRound();
                }
                else {
                    message.getAuthor().removeFace(face);
                }
                sendScores(message.getAuthor(),Game.getCurrentGame().getMessages().get(message.getRank()-1).getRank()+""); // sends scores after PLACES RANG???????????WTF
                System.out.println("\nThe Player : " + message.getAuthor().getId() + " place a face.");
                return true;
            }
        }
        message.getAuthor().setCanYields(false);
        System.out.println("\nThe Player : " + message.getAuthor().getId() + " fail to place a face.");
        return false;

    }

    /**
     * Sends the scores of the player to the game using different score calculators.
     *
     * @param player The player whose scores are to be calculated and sent.
     * @param rang A string parameter indicating the rank or type association for the score.
     */
    private void sendScores(Player player, String rang){
        Game game = Game.getCurrentGame();

        ScoreManager scoreManager = new ClassicScoreManager();
        int total = (int) scoreManager.calculateFinalScore(player.getBoard());
        for(String idScoreCalculator : scoreManager.getScoreCalculatorMap().keySet()){
            String nbPoints = String.valueOf((int) scoreManager.getScoreCalculatorMap().get(idScoreCalculator).calculateScore(player.getBoard()));
            game.sendScoresByType(player.getId(), idScoreCalculator,nbPoints);
        }
        Client.getInstance().sendMessage("admin SCORES "+player.getId()+" TOTAL "+ total);
    }
    
}
