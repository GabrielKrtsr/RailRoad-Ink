package interpreter;

import main.Game;
import util.Message;
import util.Parser;
import client.Client;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class InterpreterTest {

    @Test
    void testGetInstance() {
        Interpreter instance1 = Interpreter.getInstance();
        Interpreter instance2 = Interpreter.getInstance();
        assertNotNull(instance1);
        assertSame(instance1, instance2);
    }

    @Test
    void testInterpretWithValidMessage() throws CloneNotSupportedException {
        Game g = new Game();
        Interpreter interpreter = Interpreter.getInstance();
        String message = "1 ENTERS";

        int initialMessageCount = Game.getCurrentGame().getMessages().size();

        interpreter.interpret(message);

        int finalMessageCount = Game.getCurrentGame().getMessages().size();

        assertEquals(initialMessageCount + 1, finalMessageCount);
    }

    @Test
    void testInterpretWithNullPlayer() throws CloneNotSupportedException {
        Game g = new Game();
        Interpreter interpreter = Interpreter.getInstance();
        String message = "999 ENTERS";

        Message mes = Parser.parse(message);

        assertNull(Game.getCurrentGame().getPlayerById("999"));
        assertNotNull(mes.getCommand());
    }
}
