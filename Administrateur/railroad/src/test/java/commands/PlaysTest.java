package commands;

import interpreter.Command;
import interpreter.CommandTest;
import main.Game;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PlaysTest extends CommandTest {

    @Override
    protected Command createCommand() {
        Game c = new Game();
        return new Plays();
    }


    @Override
    protected List<String> invalid() {
        return List.of("ananas");
    }

    @Override
    protected List<String> valid() {
        return List.of();
    }

}
