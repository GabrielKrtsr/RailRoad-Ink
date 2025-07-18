package util;

import static org.junit.jupiter.api.Assertions.*;

import main.Game;
import org.junit.jupiter.api.Test;
import java.util.List;

class ParserTest {

    @Test
    void testParseExtractsCorrectArguments() {
        Game g = new Game();
        String message = "123 ENTERS";
        String message2 = "123 ELECTS 123";

        Message tmp = Parser.parse(message);
        Message parsedMessage = Parser.parse(message2);

        List<String> expectedArgs = List.of("123");
        assertEquals(expectedArgs, parsedMessage.getArgs());
    }
}
