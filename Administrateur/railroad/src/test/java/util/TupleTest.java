package util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TupleTest {

    @Test
    void testGetWithInteger() {
        Tuple<Integer, String> tuple = new Tuple<>(5, "Five");
        int result = tuple.getType1();
        assertEquals(5, result);
    }

    @Test
    void testGetWithString() {
        Tuple<String, Integer> tuple = new Tuple<>("Five", 5);
        String result = tuple.getType1();
        assertEquals("Five", result);
    }

    @Test
    void testGetWithChar() {
        Tuple<Character, String> tuple = new Tuple<>('a', "a");
        char result = tuple.getType1();
        assertEquals('a', result);
    }

    @Test
    void testGetWithDouble() {
        Tuple<Double, String> tuple = new Tuple<>(5.0, "Five point zero");
        double result = tuple.getType1();
        assertEquals(5.0, result);
    }

    @Test
    void testSetType1AndType2() {
        Tuple<String, Integer> tuple = new Tuple<>("Hello", 42);
        tuple.setType1("World");
        tuple.setType2(100);
        assertEquals("World", tuple.getType1());
        assertEquals(100, tuple.getType2());
    }

    @Test
    void testToString() {
        Tuple<String, Integer> tuple = new Tuple<>("Hello", 42);
        assertEquals("{ Hello, 42 }", tuple.toString());
    }
}