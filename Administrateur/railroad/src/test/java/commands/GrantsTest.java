package commands;

import interpreter.Command;
import interpreter.CommandFactory;
import interpreter.CommandTest;
import main.Game;
import main.Player;
import org.junit.jupiter.api.Test;
import util.Message;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GrantsTest extends CommandTest {
    @Override
    protected Command createCommand() {
        return new Grants();
    }

    @Override
    protected List<String> invalid() {
        return List.of();
    }

    @Override
    protected List<String> valid() {
        return List.of("test1","test2");
    }


    @Test
    public void testExecute() throws CloneNotSupportedException {

        Game g = new Game();
        Player player1 = new Player("player1");
        Player player2 = new Player("player2");
        g.addPlayer(player1);
        g.addPlayer(player2);
        Player author = new Player("admin");
        g.addPlayer(author);
        Message m = new Message("admin GRANTS player1 SCORES", author, 1,createCommand(), List.of("player1", "SCORES"));
        Class<? extends Command> scoresCommandClass = CommandFactory.getCommand("SCORES").getClass();
        player1.addGrantedCommand(scoresCommandClass);
        player2.addGrantedCommand(scoresCommandClass);
        assertTrue(command.execute(m));
        assertTrue(player1.getGrantedCommands().contains(scoresCommandClass));
        assertFalse(player2.getGrantedCommands().contains(scoresCommandClass));
        assertTrue(author.getGrantedCommands().contains(createCommand().getClass()));
    }
}
