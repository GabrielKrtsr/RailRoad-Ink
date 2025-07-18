package util;

import java.util.Arrays;
import java.util.List;
import interpreter.CommandFactory;
import main.Game;
import java.util.stream.*;

/**
 * Represents a parser object to analyze messages during the game
 */
public class Parser {

    /**
     * Parse the message and return a new Message object
     * @param message
     * @return message object with information
     */
    public static Message parse(String message) {

        String[] elems = message.split(" ");
        
        List<String> args = Arrays.stream(elems)
                                  .collect(Collectors.toList());
        args.remove(0); args.remove(0);

        return new Message(message, Game.getCurrentGame().getPlayerById(message.split(" ")[0]), Game.getCurrentGame().getRankOfNextMessage(), CommandFactory.getCommand(message.split(" ")[1]), args);
    }

    
}
