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

class BlamesTest extends CommandTest {

    @Override
    protected Command createCommand() {
        return new Blames();
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
    public void testExecute() throws CloneNotSupportedException {
        assertTrue(command.execute(validMessage));
    }
}
