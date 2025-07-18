package util.util_for_nodes;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import util.Path;

class ConnectionTest {

    @Test
    void testGetPathTypeForRail() {
        Connection connection = new Connection(Path.RAIL);
        Assertions.assertEquals(Path.RAIL, connection.getPathType(), "Expected path type to be RAIL");
    }

    @Test
    void testGetPathTypeForRoad() {
        Connection connection = new Connection(Path.ROAD);
        Assertions.assertEquals(Path.ROAD, connection.getPathType(), "Expected path type to be ROAD");
    }

    @Test
    void testSetAndGetPathType() {
        Connection connection = new Connection(Path.RAIL);
        connection.setPathType(Path.ROAD);
        Assertions.assertEquals(Path.ROAD, connection.getPathType(), "Expected path type to be ROAD after setting it");
    }
}