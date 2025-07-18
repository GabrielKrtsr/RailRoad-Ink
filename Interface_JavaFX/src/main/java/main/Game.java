package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import board.Board;
import board.BoardGame;
import score.ScoreManager;



/**
 * Represents a game session, managing players, rounds, and game logic.
 */
public class Game {

    private String id;




    /**
     * Map of players, indexed by their unique ID.
     */
    private Map<String, Player> players;


    /**
     * List of rounds played in the game.
     */
    private List<Round> rounds;


    /**
     * Score manager to handle scoring logic.
     */
    private ScoreManager scoreManager;

    /**
     * Number of rounds in the game.
     */
    private static final int NB_ROUNDS = 7;

    /**
     * Constructor initializing the game components.
     */
    public Game() {
        this.players = new HashMap<>();
        this.rounds = new ArrayList<>();
    }


    /**
     * Adds a player to the game.
     *
     * @param player the player to add.
     */
    public void addPlayer(Player player) {
        this.players.put(player.getId(), player);
    }

    /**
     * Retrieves a player by their ID.
     *
     * @param id the ID of the player.
     * @return the associated player, or null if not found.
     */
    public Player getPlayerById(String id) {
        return this.players.get(id);
    }


    /**
     * Retrieves the ID of the game.
     *
     * @return the ID of the game.
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the ID of the game.
     *
     * @param id the new ID of the game.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Retrieves the current round.
     *
     * @return the current round, or {@code null} if no rounds have started.
     */
    public Round getCurrentRound() {
        return this.rounds.isEmpty() ? null : this.rounds.get(this.rounds.size() - 1);
    }


    /**
     * Determines the list of winners ranked by their scores.
     *
     * @return a map of ranked players .
     */
    public Map<Integer, Player> determineListOfWinner() {
        Map<Integer, Player> listOfWinner = new HashMap<>();
        for (int i = 1; i <= players.size(); i++) {
            listOfWinner.put(i, null);
        }

        Player tmp;
        for (Player player : players.values()) {
            for (int i = 1; i <= players.size(); i++) {
                listOfWinner.putIfAbsent(i, player);
                if (listOfWinner.get(i).getScore() < player.getScore()) {
                    tmp = listOfWinner.get(i);
                    listOfWinner.put(i, player);
                    listOfWinner.put(i + 1, tmp);
                }
            }
        }
        return listOfWinner;
    }

    /**
     * Determines the winner of the game.
     *
     * @return the winning player, or null if no players exist.
     */
    public Player determineWinner() {
        Player winner = null;
        int bestScore = 0;

        for (Player player : players.values()) {
            int score = player.getScore();
            if (score > bestScore) {
                bestScore = score;
                winner = player;
            }
        }

        if (winner != null) {
            System.out.println("Le gagnant est : " + winner.getId() + " avec un score de " + bestScore);
        }

        return winner;
    }

    /**
     * Calculates and displays scores for all players.
     */
    public void calculateScores() {
        for (Player player : players.values()) {
            //
            System.out.println(player.getId() + " score at this round: " );
        }
    }

    /**
     * Retrieves the list of players.
     *
     * @return a list of players.
     */
    public List<Player> getPlayers() {
        return new ArrayList<>(this.players.values());
    }

    /**
     * Creates and starts a new round.
     *
     * @return the newly created round.
     */
    public Round newRound() {
        Round round = new Round();
        this.rounds.add(round);
        return round;
    }

    public void finishCurrentRound(){
        if(!rounds.isEmpty()){
            rounds.get(rounds.size() - 1).placeCurrentBoardState(this.getElectedPlayers());
        }
    }

    /**
     * Retrieves the list of elected players.
     *
     * @return a list of elected players.
     */
    public List<Player> getElectedPlayers() {
        return this.players.values().stream()
                .filter(Player::getElects)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public List<Round> getRounds() {
        return rounds;
    }
}
