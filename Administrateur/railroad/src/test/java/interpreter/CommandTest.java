package interpreter;

import static org.junit.jupiter.api.Assertions.*;


import main.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.Message;
import main.Player;
import java.util.ArrayList;
import java.util.List;


public abstract class CommandTest {

    protected Command command;
    protected Message validMessage;
    protected Message invalidMessage;
    protected Player testPlayer;
    protected abstract Command createCommand();


    @BeforeEach
    public void setUp() {
        command = createCommand();
        testPlayer = new Player("TestPlayer");
        validMessage = new Message("valid", testPlayer, 1, command, valid());
        invalidMessage = new Message("invalid", testPlayer, 1, command, invalid());

    }


    @Test
    public void testGetArgs() {
        assertEquals(command.getArgs(), command.getArgs());
    }


    @Test
    public void testCheckArgsValid() {
        assertTrue(command.checkArgs(validMessage));
    }


    @Test
    public void testCheckArgsInvalid() {
        assertFalse(command.checkArgs(invalidMessage));
    }

    protected abstract List<String> invalid();


    protected abstract List<String> valid();
}
