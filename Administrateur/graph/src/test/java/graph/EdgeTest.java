package graph;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EdgeTest {

    @Test
    void testGetSource_WithIntegerVertex_ShouldReturnCorrectSource() {
        Integer source = 1;
        Integer target = 2;
        double weight = 1.0;
        Edge<Integer> edge = new Edge<>(source, target, weight);

        Integer resultSource = edge.getSource();

        assertEquals(source, resultSource);
    }

    @Test
    void testGetSource_WithCharacterVertex_ShouldReturnCorrectSource() {
        Character source = 'A';
        Character target = 'B';
        double weight = 1.0;
        Edge<Character> edge = new Edge<>(source, target, weight);

        Character resultSource = edge.getSource();

        assertEquals(source, resultSource);
    }

    @Test
    void testGetSource_WithStringVertex_ShouldReturnCorrectSource() {
        String source = "Node1";
        String target = "Node2";
        double weight = 1.0;
        Edge<String> edge = new Edge<>(source, target, weight);

        String resultSource = edge.getSource();

        assertEquals(source, resultSource);
    }


    @Test
    void testMutation_WithAlteredSource_ShouldFailEqualityCheck() {
        Integer source = 1;
        Integer target = 2;
        double weight = 1.0;
        Edge<Integer> edge1 = new Edge<>(source, target, weight);
        Edge<Integer> edge2 = new Edge<>(2, target, weight);

        assertNotEquals(edge1, edge2, "Edges with different sources should not be equal");
    }

    @Test
    void testMutation_WithAlteredTarget_ShouldFailEqualityCheck() {
        Integer source = 1;
        Integer target = 2;
        double weight = 1.0;
        Edge<Integer> edge1 = new Edge<>(source, target, weight);
        Edge<Integer> edge2 = new Edge<>(source, 3, weight);

        assertNotEquals(edge1, edge2, "Edges with different targets should not be equal");
    }

    @Test
    void testMutation_WithAlteredWeight_ShouldFailEqualityCheck() {
        Integer source = 1;
        Integer target = 2;
        double weight = 1.0;
        Edge<Integer> edge1 = new Edge<>(source, target, weight);
        Edge<Integer> edge2 = new Edge<>(source, target, 2.0);

        assertNotEquals(edge1, edge2, "Edges with different weights should not be equal");
    }

    @Test
    void testMutation_WithNullSource_ShouldHandleProperly() {
        Integer target = 2;
        double weight = 1.0;

        assertThrows(NullPointerException.class, () -> {
            new Edge<>(null, target, weight);
        });

    }

    @Test
    void testMutation_WithNullTarget_ShouldHandleProperly() {
        Integer source = 1;
        double weight = 1.0;

        assertThrows(NullPointerException.class, () -> {
            new Edge<>(source, null, weight);
        });

    }

    @Test
    void testMutation_WithNegativeWeight_ShouldHandleProperly() {
        Integer source = 1;
        Integer target = 2;

        assertThrows(IllegalArgumentException.class, () -> {
            new Edge<>(source, target, -1.0);
        });
    }

    @Test
    void testMutation_HashCodeConsistency() {
        Integer source = 1;
        Integer target = 2;
        double weight = 1.0;

        Edge<Integer> edge = new Edge<>(source, target, weight);
        int hashCode1 = edge.hashCode();
        int hashCode2 = edge.hashCode();

        assertEquals(hashCode1, hashCode2, "Hash codes of the same edge instance should remain consistent");
    }

    @Test
    void testMutation_NotEqualsWithNull() {
        Integer source = 1;
        Integer target = 2;
        double weight = 1.0;

        Edge<Integer> edge = new Edge<>(source, target, weight);

        assertNotEquals(edge, null, "An edge should not be equal to null");
    }

    @Test
    void testMutation_NotEqualsWithDifferentType() {
        Integer source = 1;
        Integer target = 2;
        double weight = 1.0;

        Edge<Integer> edge = new Edge<>(source, target, weight);

        assertNotEquals(edge, "A string object", "An edge should not be equal to an object of another type");
    }


    @Test
    void testToString_ShouldReturnCorrectFormat() {
        Integer source = 1;
        Integer target = 2;
        double weight = 1;


        Edge<Integer> edge = new Edge<>(source, target, weight);
        String expectedString = String.format("Edge[source=%s, target=%s, weight=%f]", source, target, weight);

        assertEquals(expectedString, edge.toString(), "toString should return the correct formatted string");
    }

    @Test
    void testEquals_SameObject_ShouldReturnTrue() {
        Integer source = 1;
        Integer target = 2;
        double weight = 1.5;

        Edge<Integer> edge = new Edge<>(source, target, weight);

        assertEquals(edge, edge, "An edge should be equal to itself");
    }

    @Test
    void testEquals_DifferentObjectWithSameAttributes_ShouldReturnTrue() {
        Integer source = 1;
        Integer target = 2;
        double weight = 1.5;

        Edge<Integer> edge1 = new Edge<>(source, target, weight);
        Edge<Integer> edge2 = new Edge<>(source, target, weight);

        assertEquals(edge1, edge2, "Edges with the same attributes should be equal");
    }

    @Test
    void testEquals_DifferentSource_ShouldReturnFalse() {
        Integer source1 = 1;
        Integer source2 = 2;
        Integer target = 3;
        double weight = 1.5;

        Edge<Integer> edge1 = new Edge<>(source1, target, weight);
        Edge<Integer> edge2 = new Edge<>(source2, target, weight);

        assertNotEquals(edge1, edge2, "Edges with different sources should not be equal");
    }

    @Test
    void testEquals_DifferentTarget_ShouldReturnFalse() {
        Integer source = 1;
        Integer target1 = 2;
        Integer target2 = 3;
        double weight = 1.5;

        Edge<Integer> edge1 = new Edge<>(source, target1, weight);
        Edge<Integer> edge2 = new Edge<>(source, target2, weight);

        assertNotEquals(edge1, edge2, "Edges with different targets should not be equal");
    }

    @Test
    void testEquals_DifferentWeight_ShouldReturnFalse() {
        Integer source = 1;
        Integer target = 2;
        double weight1 = 1.5;
        double weight2 = 2.5;

        Edge<Integer> edge1 = new Edge<>(source, target, weight1);
        Edge<Integer> edge2 = new Edge<>(source, target, weight2);

        assertNotEquals(edge1, edge2, "Edges with different weights should not be equal");
    }

}
