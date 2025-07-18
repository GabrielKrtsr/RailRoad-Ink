package interpreter;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.reflections.Reflections;

import rotationStrategy.RotationStrategy;

/**
 * Factory class for managing rotation strategy instances.
 */
public class RotationFactory {



    /**
     * A map storing rotation strategies, indexed by their unique identifiers.
     */
    private static final Map<String, RotationStrategy> strategies = new HashMap<>();

    static {
        Reflections reflections = new Reflections("rotationStrategy");
        Set<Class<? extends RotationStrategy>> strategyClasses = reflections.getSubTypesOf(RotationStrategy.class);
        for (Class<? extends RotationStrategy> strategyClass : strategyClasses) {
            try {
                RotationStrategy strategy = strategyClass.getDeclaredConstructor().newInstance();
                String strategyName = strategy.getId();
                strategies.put(strategyName, strategy);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Retrieves a rotation strategy instance based on its name.
     *
     * @param name the identifier of the rotation strategy (case insensitive).
     * @return the associated rotation strategy instance.
     * @throws IllegalArgumentException if no strategy is found with the given name.
     */
    public static RotationStrategy getStrategy(String name) {
        RotationStrategy strategy = strategies.get(name.toUpperCase());
        if (strategy == null) {
            throw new IllegalArgumentException("No strategy found with name: " + name);
        }
        return strategy;
    }
    
}
