package commands;

import interpreter.Command;
import interpreter.CommandTest;
import org.junit.jupiter.api.Test;
import util.Message;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class YieldsTest extends CommandTest {

    @Override
    protected Command createCommand() {
        return new Yields();
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
        validMessage = new Message(testPlayer.getId() +" YIELDS",testPlayer,1,command,new ArrayList<>());
        testPlayer.setElects(true);
        assertTrue(command.execute(validMessage));
        assertTrue(testPlayer.isPassRound());
    }
}
