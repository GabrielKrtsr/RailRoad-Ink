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

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ElectsTest extends CommandTest {

    @Override
    protected Command createCommand() {
        return new Elects();
    }

    @Override
    protected List<String> invalid() {
        return List.of();
    }

    @Override
    protected List<String> valid() {
        return List.of("test");
    }

    @Test
    void testExecute() throws CloneNotSupportedException {
        Game g = new Game();
        g.addPlayer(testPlayer);
        Player tmp = new Player("ananas");
        Game.getCurrentGame().addPlayer(tmp);
        List<String> tmpS = new ArrayList<>();
        tmpS.add("ananas");
        Message m = new Message("testPlayer ELECTS", testPlayer, 1,createCommand(),tmpS);
        assertTrue(command.execute(m));
        assertTrue(tmp.getElects());
        List<Class<? extends Command>> c =new ArrayList<>();
        c.add(CommandFactory.getCommand("PLAYS").getClass());
        c.add(CommandFactory.getCommand("THROWS").getClass());
        c.add(CommandFactory.getCommand("PLACES").getClass());
        c.add(CommandFactory.getCommand("YIELDS").getClass());
        assertEquals(tmp.getGrantedCommands(),c);
    }
}
