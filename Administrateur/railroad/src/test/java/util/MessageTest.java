package util;

import static org.junit.jupiter.api.Assertions.*;

import commands.Enters;
import main.Player;
import org.junit.jupiter.api.Test;
import java.util.List;

class MessageTest {

    @Test
    void testGetContent() {
        Message message = new Message("Hello", new Player("Alice"), 5, new Enters(), List.of("arg1", "arg2"));
        assertEquals("Hello", message.getContent());
    }

    @Test
    void testGetRank() {
        Message message = new Message("Hello", new Player("Alice"), 5, new Enters(), List.of("arg1", "arg2"));
        assertEquals(5, message.getRank());
    }

    @Test
    void testGetAuthor() {
        Player player = new Player("Alice");
        Message message = new Message("Hello", player, 5, new Enters(), List.of("arg1", "arg2"));
        assertEquals(player, message.getAuthor());
    }

    @Test
    void testGetCommand() {
        Enters command = new Enters();
        Message message = new Message("Hello", new Player("Alice"), 5, command, List.of("arg1", "arg2"));
        assertEquals(command, message.getCommand());
    }

    @Test
    void testGetArgs() {
        List<String> args = List.of("arg1", "arg2");
        Message message = new Message("Hello", new Player("Alice"), 5, new Enters(), args);
        assertEquals(args, message.getArgs());
    }
}

