package commands;

import interpreter.Command;
import interpreter.CommandTest;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ScoresTest extends CommandTest {

    @Override
    protected Command createCommand() {
        return new Scores();
    }

    @Test
    public void testExecute()  {
        assertTrue(command.execute(validMessage));
    }


    @Override
    protected List<String> invalid() {
        return List.of();
    }

    @Override
    protected List<String> valid() {
        return List.of("test1","test2","test3");
    }

}
