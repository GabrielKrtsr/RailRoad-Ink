package commands;

import interpreter.Command;
import interpreter.CommandTest;
import org.junit.jupiter.api.Test;
import util.State;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LeavesTest extends CommandTest {

    @Override
    protected Command createCommand() {
        return new Leaves();
    }

    @Test
    public void testExecute() throws CloneNotSupportedException {

        assertTrue(command.execute(validMessage));
        assertEquals(testPlayer.getState(), State.PASSIVE);
    }

    @Override
    protected List<String> invalid() {
        return List.of("test");
    }

    @Override
    protected List<String> valid() {
        return List.of();
    }


}
