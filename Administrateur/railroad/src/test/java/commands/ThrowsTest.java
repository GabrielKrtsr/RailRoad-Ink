package commands;

import face.AbstractFace;
import face.HighwayFace;
import interpreter.Command;
import interpreter.CommandFactory;
import interpreter.CommandTest;
import main.Game;
import main.Player;
import main.Round;
import org.junit.jupiter.api.Test;
import util.Message;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ThrowsTest extends CommandTest {

    @Override
    protected Command createCommand() {
        return new Throws();
    }


    @Override
    protected List<String> invalid() {
        return List.of();
    }

    @Override
    protected List<String> valid() {
        return List.of("test1");
    }


    @Test
    public void testExecute() throws CloneNotSupportedException {
        Game game = new Game();
        Player player = new Player("testPlayer");
        player.setElects(true);
        game.addPlayer(player);
        Message message = new Message("admin THROWS H H H H", player, 1, command, List.of("H","H","H","H"));
        boolean result = command.execute(message);
        assertTrue(result);
        assertNotNull(game.getCurrentRound());

        Round round = game.getCurrentRound();
        assertNotEquals(null,round.getFaceByid("H"));
    }

    @Test
    public void testExecuteNotElectedOrAdmin() {
        Game game = new Game();
        Player player = new Player("testPlayer");
        player.setElects(true);
        game.addPlayer(player);
        Player regularPlayer = new Player("regularPlayer");
        game.addPlayer(regularPlayer);

        // Create the message with the THROWS command
        Message message = new Message("regularPlayer THROWS test1", regularPlayer, 1, command, List.of("test1"));

        // Execute the command
        boolean result = command.execute(message);

        // Verify the command failed
        assertFalse(result);
    }

    @Test
    public void testExecuteNoElectedPlayers() {
        Game game = new Game();
        Player player = new Player("testPlayer");
        game.addPlayer(player);
        player.setElects(false);
        Message message = new Message("testPlayer THROWS test1", player, 1, command, List.of("test1"));
        boolean result = command.execute(message);
        assertFalse(result);
    }
}
