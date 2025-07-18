package main;

import static org.junit.jupiter.api.Assertions.*;

import commands.Places;
import face.HighwayCurveFace;
import face.HighwayFace;
import interpreter.Command;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import face.AbstractFace;
import util.State;

class PlayerTest {

    private Player player;

    @BeforeEach
    void setUp() {
        player = new Player("player1");
    }

    @Test
    void testPlayerCreation() {
        assertEquals("player1", player.getId());
        assertEquals(State.ACTIVE, player.getState());
        assertNotNull(player.getBoard());
        assertFalse(player.getElects());
        assertEquals(0, player.getScore());
    }

    @Test
    void testAddScore() {
        player.addScore(10);
        assertEquals(10, player.getScore());
        player.addScore(5);
        assertEquals(15, player.getScore());
    }

    @Test
    void testUpdateState() {
        player.updateState(State.PASSIVE);
        assertEquals(State.PASSIVE, player.getState());
    }

    @Test
    void testSetElects() {
        player.setElects(true);
        assertTrue(player.getElects());
        player.setElects(false);
        assertFalse(player.getElects());
    }

    @Test
    void testAdminCheck() {
        Player admin = new Player("admin");
        assertTrue(admin.isAdmin());
        assertFalse(player.isAdmin());
    }


    @Test
    void testFaceManagement() {
        AbstractFace face = new  HighwayFace();
        player.addFaces(face);
        assertTrue(player.getFaces().contains(face));

        player.removeFace(face);
        assertFalse(player.getFaces().contains(face));
    }

    @Test
    void testGrantedCommands() {
        Class<? extends Command> commandClass = Places.class;
        player.addGrantedCommand(commandClass);
        assertTrue(player.getGrantedCommands().contains(commandClass));

        player.removeGrantedCommand(commandClass);
        assertFalse(player.getGrantedCommands().contains(commandClass));
    }

    @Test
    void testTilePlacement() {
        player.addScore(10);
        assertEquals(0, player.getTilesPlacedThisRound());
        assertEquals(0, player.getSpecialsTilesPlacedThisRound());
    }

    @Test
    void testResetForNextRound() {
        player.addScore(10);
        player.resetForNextRound();
        assertEquals(0, player.getTilesPlacedThisRound());
        assertEquals(0, player.getSpecialsTilesPlacedThisRound());
        assertTrue(player.getFaces().isEmpty());
    }



    @Test
    void testClearFaces() {
        AbstractFace face1 = new HighwayFace();
        AbstractFace face2 = new HighwayFace();
        player.addFaces(face1);
        player.addFaces(face2);

        assertEquals(2, player.getFaces().size());
        player.resetForNextRound();
        assertEquals(0, player.getFaces().size());
    }

    @Test
    void testToString() {
        String expected = "This player's ID is player1";
        assertEquals(expected, player.toString());
        Player player2 = new Player("player2");
        assertEquals("This player's ID is player2", player2.toString());
    }

    @Test
    void testGetCellById() {
        AbstractFace face1 = new HighwayFace();
        AbstractFace face2 = new HighwayCurveFace();
        String id1 = face1.getId();
        String id2 = face2.getId();

        player.addFaces(face1);
        player.addFaces(face2);

        assertEquals(face1, player.getCellById(id1));
        assertEquals(face2, player.getCellById(id2));
        assertNull(player.getCellById("nonexistent-id"));
    }

    @Test
    void testCanPlay() {
        assertTrue(player.canPlay());
    }


    @Test
    void testPassRound() {
        assertFalse(player.isPassRound());

        player.setPassRound(true);
        assertTrue(player.isPassRound());

        player.setPassRound(false);
        assertFalse(player.isPassRound());
    }

    @Test
    void testSetBoard() {
        assertNotNull(player.getBoard());
    }

}
