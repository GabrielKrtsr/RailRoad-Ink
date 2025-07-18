package main;

import static org.junit.jupiter.api.Assertions.*;

import commands.Enters;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import util.Message;
import interpreter.Command;

class GameTest {

    private Game game;
    private Player player1;
    private Player player2;
    private Command Command;

    @BeforeEach
    void setUp() {
        game = new Game();
        player1 = new Player("player1");
        player2 = new Player("player2");
        Command = null;
    }

    @Test
    void testSingletonInstance() {
        assertEquals(game, Game.getCurrentGame());
    }

    @Test
    void testAddAndGetPlayer() {
        game.addPlayer(player1);
        assertEquals(player1, game.getPlayerById("player1"));
    }

    @Test
    void testAddMessage() {
        Message message = new Message("Hello World", player1, 1, Command, new ArrayList<>());
        game.addMessage(message);
        assertEquals(message, game.getMessageByRank(1));
    }


    @Test
    void testCalculateScores() {
        player1.addScore(10);
        player2.addScore(20);
        game.addPlayer(player1);
        game.addPlayer(player2);
        game.calculateScores();
        assertTrue(player1.getScore() >= 0);
        assertTrue(player2.getScore() >= 0);
    }

    @Test
    void testDetermineWinner() {
        player1.addScore(50);
        player2.addScore(100);
        game.addPlayer(player1);
        game.addPlayer(player2);
        assertEquals(player2, game.determineWinner());
        assertEquals(100, player2.getScore());
    }

    @Test
    void testGetElectedPlayers() {
        player1.setElects(true);
        player2.setElects(false);
        game.addPlayer(player1);
        game.addPlayer(player2);
        List<Player> electedPlayers = game.getElectedPlayers();
        assertEquals(1, electedPlayers.size());
        assertEquals(player1, electedPlayers.get(0));
    }

    @Test
    void testGetRankOfNextMessage() {
        assertEquals(0, game.getRankOfNextMessage());
        Message message = new Message("Test", player1, 1, new Enters(), new ArrayList<>());
        game.addMessage(message);
        assertEquals(1, game.getRankOfNextMessage());
        game.addMessage(message);
        assertEquals(2, game.getRankOfNextMessage());
    }

    @Test
    void testDetermineWinnerWithEqualScores() {

        player1.addScore(100);
        player2.addScore(100);
        game.addPlayer(player1);
        game.addPlayer(player2);

        Player winner = game.determineWinner();
        assertNotNull(winner);
        assertEquals(100, winner.getScore());
    }

    @Test
    void testDetermineWinnerWithNoPlayers() {
        Player winner = game.determineWinner();
        assertNull(winner);
    }

    @Test
    void testNewRound() {
        Round round = game.newRound();
        assertNotNull(round);
        assertEquals(round, game.getCurrentRound());
    }

    @Test
    void testGetMessagesEmpty() {
        List<Message> messages = game.getMessages();
        assertNotNull(messages);
        assertTrue(messages.isEmpty());
    }

    @Test
    void testGetCurrentRoundNull() {
        assertNull(game.getCurrentRound());
    }
}

