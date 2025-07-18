package interpreter;

import face.AbstractFace;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FaceFactoryTest {

    @BeforeAll
    static void setUp() {
        assertDoesNotThrow(() -> FaceFactory.getFace("H"));
    }

    @Test
    void testGetFaceValid() {
        AbstractFace face = FaceFactory.getFace("H");
        assertNotNull(face);
        assertEquals("H", face.getId());
    }

    @Test
    void testGetFaceInvalid() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> FaceFactory.getFace("ANANAS"));
        assertEquals("No face found with name: ANANAS", exception.getMessage());
    }
}