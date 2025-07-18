package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import client.Client;
import dice.Dice;
import face.AbstractFace;
import interpreter.DiceFactory;
import interpreter.Interpreter;
import score.ClassicScoreManager;
import score.ScoreManager;
import util.Message;

import static java.lang.Thread.sleep;

/**
 * Represents a game session, managing players, rounds, and game logic.
 */
public class Game {

    /**
     * Map of players, indexed by their unique ID.
     */
    private Map<String, Player> players;

    /**
     * List of messages exchanged during the game.
     */
    private List<Message> messages;

    /**
     * Static instance of the game (singleton pattern).
     */
    private static Game currentGame;

    /**
     * Client instance used for communication.
     */
    private Client client = Client.getInstance();



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
        this.messages = new ArrayList<>();
        this.rounds = new ArrayList<>();
        this.scoreManager = new ClassicScoreManager();
        currentGame = this;
    }

    /**
     * Retrieves the current instance of the game.
     *
     * @return the current game instance.
     */
    public static Game getCurrentGame() {
        return currentGame;
    }

    /**
     * Handles an incoming message by interpreting and executing the corresponding command.
     *
     * @param message the message to interpret.
     * @throws CloneNotSupportedException if an error occurs during execution.
     */
    public void handleMessage(String message) throws CloneNotSupportedException {
        Interpreter interpreter = Interpreter.getInstance();
        interpreter.interpret(message);
    }

    /**
     * get the list of messages
     *
     * @return the list of messages
     */
    public List<Message> getMessages() {
        return messages;
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
     * Adds a new message to the game.
     *
     * @param message the message to add.
     */
    public void addMessage(Message message) {
        this.messages.add(message);
    }

    /**
     * Retrieves the rank of the next message to be sent.
     *
     * @return the next message rank.
     */
    public int getRankOfNextMessage() {
        return this.messages.size();
    }

    /**
     * Retrieves a message by its rank.
     *
     * @param rank the rank of the message.
     * @return the associated message.
     */
    public Message getMessageByRank(int rank) {
        return this.messages.get(rank - 1);
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
     * Rolls all dice and sends the resulting face values to the client.
     */
    public void throwFaces() {
        String message = "admin THROWS";
        for (Dice dice : DiceFactory.getDices()) {
            for (int i = dice.getNumberOfThrows(); i > 0; i--) {
                AbstractFace face = dice.roll();
                message += " " + face.getId();
            }
        }
        this.client.sendMessage(message);
    }

    /**
     * Sends a score message to the client based on the specified rank, score type, and point value.
     *
     * @param rang The rank or classification associated with the score.
     * @param type The type of score being sent.
     * @param nbPoints The number of points to include in the score message.
     */
    public void sendScoresByType(String rang, String type, String nbPoints){
        String message = "admin SCORES";
        message += " " + rang + " " + type + " " + nbPoints;
        this.client.sendMessage(message);
    }

    /**
     * Starts the game loop, managing rounds and scoring.
     *
     * @throws InterruptedException if the thread sleep is interrupted.
     */
    public void play() throws InterruptedException {
        System.out.println("Game started!");
        for (int i = 0; i < Game.NB_ROUNDS; i++) {
            this.throwFaces();

            for (Player p : this.getElectedPlayers()) {
                sleep(1000);
                while ((!p.getFaces().isEmpty()|| p.getSpecialsTilesPlacedThisRound()==0)&& !p.isPassRound()) {
                    // Waiting for the player to process faces
                }
                p.setPassRound(false);
            }
            this.calculateScores();
        }
        for(Player p : this.getElectedPlayers()){
            System.out.println("the player "+p.getId()+" have "+ p.getScore()+" points");
        }
        System.out.println("Game ended!");
    }

    /**
     * Connects to the game reflector using the existing Client class.
     */
    public void connectToReflector() {
        try {
            client.connect();
        } catch (InterruptedException e) {
            System.err.println("Failed to connect to the reflector: " + e.getMessage());
        }
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
        for (Player player : this.getElectedPlayers()) {
            int score = (int) scoreManager.calculateFinalScore(player.getBoard());
            System.out.println(player.getId() + " score at this round: " + score);
            player.setScore(score);
            Client.getInstance().sendMessage("admin SCOREROUND "+player.getId()+" "+player.getScore());
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
}
