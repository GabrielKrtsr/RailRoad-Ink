package interpreter;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.reflections.Reflections;

import face.AbstractFace;

/**
 * Factory class for managing face instances.
 */
public class FaceFactory {

    /**
     * A map storing face instances, indexed by their unique identifiers.
     */
    private static final Map<String, AbstractFace> faces = new HashMap<>();

    static {
        Reflections reflections = new Reflections("face");
        Set<Class<? extends AbstractFace>> strategyClasses = reflections.getSubTypesOf(AbstractFace.class);

        for (Class<? extends AbstractFace> strategyClass : strategyClasses) {
            try {
                AbstractFace strategy = strategyClass.getDeclaredConstructor().newInstance();
                String strategyName = strategy.getId();
                faces.put(strategyName, strategy);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     /**
     * Retrieves a face instance based on its name.
     *
     * @param name the identifier of the face.
     * @return the associated face instance.
     * @throws IllegalArgumentException if no face is found with the given name.
     */
    public static AbstractFace getFace(String name) {
        AbstractFace strategy = faces.get(name);
        if (strategy == null) {
            throw new IllegalArgumentException("No face found with name: " + name);
        }
        return strategy;
    }
    
}
