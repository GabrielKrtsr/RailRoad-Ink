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

class EntersTest extends CommandTest {

    @Override
    protected Command createCommand() {
        return new Enters();
    }

    @Override
    protected List<String> invalid() {
        return List.of("test");
    }

    @Override
    protected List<String> valid() {
        return List.of();
    }

    @Test
    public void testExecute() throws CloneNotSupportedException {
        Game g = new Game();
        Message m = new Message("ananas ENTERS", null, 1,createCommand(),new ArrayList<>());
        assertTrue(command.execute(m));
        List<Class<? extends Command>> c =new ArrayList<>();
        c.add(CommandFactory.getCommand("ENTERS").getClass());
        c.add(CommandFactory.getCommand("LEAVES").getClass());
        c.add(CommandFactory.getCommand("SCORES").getClass());
        c.add(CommandFactory.getCommand("BLAMES").getClass());
        c.add(CommandFactory.getCommand("GRANTS").getClass());
        c.add(CommandFactory.getCommand("AGREES").getClass());
        c.add(CommandFactory.getCommand("ELECTS").getClass());
        assertNotNull(g.getPlayerById("ananas"));
        Player p = g.getPlayerById("ananas");
        assertEquals(c,p.getGrantedCommands());
    }

}
