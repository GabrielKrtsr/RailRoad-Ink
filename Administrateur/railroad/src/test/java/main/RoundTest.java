package main;

import face.AbstractFace;
import face.HighwayFace;
import face.HighwayJunctionFace;
import face.RailFace;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class RoundTest {

    private Round round;
    private Player player1;
    private Player player2;
    private AbstractFace face1;
    private AbstractFace face2;

    @BeforeEach
    void setUp() {
        round = new Round();

        player1 = new Player("ananas");
        player2 = new Player("ananas2");

        face1 = new HighwayFace();
        face2 = new HighwayJunctionFace();
        round.addFace(face1);
        round.addFace(face2);
    }

    @Test
    void testAddFace() {
        AbstractFace newFace = new RailFace();
        assertEquals(newFace.getId(), "R");

        round.addFace(newFace);

        assertEquals(newFace, round.getFaceByid("R"));
    }

    @Test
    void testGetFaceById_ExistingFace() {
        assertEquals(face1, round.getFaceByid("H"));
        assertEquals(face2, round.getFaceByid("Hj"));
    }


    @Test
    void testGetFaceById_NonExistingFace() {
        assertNull(round.getFaceByid("S"));
    }


    @Test
    void testDistributeFaces() throws CloneNotSupportedException {
        Player testPlayer1 = new Player("player1");
        Player testPlayer2 = new Player("player2");
        List<Player> players = new ArrayList<>();
        players.add(testPlayer1);
        players.add(testPlayer2);

        assertTrue(testPlayer1.getFaces().isEmpty());
        assertTrue(testPlayer2.getFaces().isEmpty());

        round.distributeFaces(players);

        assertEquals(2, testPlayer1.getFaces().size());
        assertEquals(2, testPlayer2.getFaces().size());

        assertEquals("H", testPlayer1.getFaces().get(0).getId());
        assertEquals("Hj", testPlayer1.getFaces().get(1).getId());
        assertEquals("H", testPlayer2.getFaces().get(0).getId());
        assertEquals("Hj", testPlayer2.getFaces().get(1).getId());
        AbstractFace extraFace = new RailFace();
        testPlayer1.addFaces(extraFace);
        assertEquals(3, testPlayer1.getFaces().size());
        round.distributeFaces(players);
        assertEquals(2, testPlayer1.getFaces().size());
    }

    @Test
    void testEmptyRound() {
        Round emptyRound = new Round();
        Player testPlayer = new Player("testPlayer");
        List<Player> players = new ArrayList<>();
        players.add(testPlayer);

        try {
            emptyRound.distributeFaces(players);
            assertTrue(testPlayer.getFaces().isEmpty());
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testGetFaceByIdCaseInsensitive() {
        assertEquals(face1, round.getFaceByid("H"));
        assertEquals(face2, round.getFaceByid("Hj"));
    }

    @Test
    void testToUpperCaseInGetFaceById() {
        AbstractFace mockFace = new HighwayFace();
        Round testRound = new Round();
        testRound.addFace(mockFace);
        assertEquals(mockFace, testRound.getFaceByid("H"));
    }

}
