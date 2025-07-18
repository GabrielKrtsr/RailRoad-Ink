package interpreter;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.reflections.Reflections;
/**
 * Factory class for managing command instances.
 */
public class CommandFactory {

    /**
     *  A map storing command instances, indexed by their uppercase simple class names.
     *  */
    private static final Map<String, Command> commands = new HashMap<>();

    static {
        Reflections reflections = new Reflections("commands");
        Set<Class<? extends Command>> commandClasses = reflections.getSubTypesOf(Command.class);

        for (Class<? extends Command> commandClass : commandClasses) {
            try {
                Command command = commandClass.getDeclaredConstructor().newInstance();
                String commandId = commandClass.getSimpleName().toUpperCase();
                commands.put(commandId, command);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Retrieves a command instance based on its name.
     *
     * @param name the identifier of the command.
     * @return the associated command instance.
     * @throws IllegalArgumentException if no command is found with the given name.
     */
    public static Command getCommand(String name) {
        Command command = commands.get(name.toUpperCase());
        if (command == null) {
            throw new IllegalArgumentException("No strategy found with name: " + name);
        }
        return command;
    }

}
