package interpreter;

import face.AbstractFace;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import rotationStrategy.RotationStrategy;

import static org.junit.jupiter.api.Assertions.*;

public class RotationFactoryTest {

    @BeforeEach
    void setUp() {
        assertDoesNotThrow(() -> RotationFactory.getStrategy("R"));
    }

    @Test
    void testGetFaceValid() {
        RotationStrategy R = RotationFactory.getStrategy("R");
        assertNotNull(R);
        assertEquals("R", R.getId());
    }

    @Test
    void testGetFaceInvalid() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> FaceFactory.getFace("ANANAS"));
        assertEquals("No face found with name: ANANAS", exception.getMessage());
    }
}