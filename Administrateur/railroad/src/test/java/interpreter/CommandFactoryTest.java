package interpreter;

import static org.junit.jupiter.api.Assertions.*;

import commands.Agrees;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class CommandFactoryTest {

    @BeforeAll
    static void setup() {
        new CommandFactory();
    }

    @Test
    void testGetCommandValid() {
        Command command = CommandFactory.getCommand("Agrees");
        assertNotNull(command);
        assertInstanceOf(Agrees.class, command);
    }

    @Test
    void testGetCommandInvalid() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            CommandFactory.getCommand("InvalidCommand");
        });

        assertEquals("No strategy found with name: InvalidCommand", exception.getMessage());
    }
}
