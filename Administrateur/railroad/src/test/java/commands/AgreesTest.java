package commands;

import interpreter.Command;
import interpreter.CommandTest;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class AgreesTest extends CommandTest {

    @Override
    protected Command createCommand() {

        return new Agrees();
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
        assertTrue(command.execute(validMessage));
    }
}
