package main;

import java.util.ArrayList;
import java.util.List;

import board.Board;
import board.BoardGame;
import face.AbstractFace;
import util.State;

/**
 * Represents a player in the game.
 */
public class Player {



    /**
     * List of faces the player currently holds.
     */
    private List<AbstractFace> faces;

    /**
     * Unique identifier for the player.
     */
    private String id;

    /**
     * The player's board.
     */
    private Board board;

    /**
     * The player's current state.
     */
    private State state;

    /**
     * Boolean indicating if the player has been elected.
     */
    private boolean elects;

    /**
     * Number of tiles placed by the player in the current round.
     */
    private int tilesPlacedThisRound;

    /**
     * Number of special tiles placed by the player in the current round.
     */
    private int specialTilesPlacedThisRound;

    /**
     * The player's current score.
     */
    private int score;

    /**
     * Constructs a new player with the given ID.
     *
     * @param id The unique identifier for the player.
     */
    public Player(String id) {
        this.id = id;
        this.board = new BoardGame();
        this.state = State.ACTIVE;
        this.elects = false;
        this.faces = new ArrayList<>();
        this.tilesPlacedThisRound = 0;
        this.specialTilesPlacedThisRound = 0;
    }


    /**
     * Gets the number of tiles placed in the current round.
     *
     * @return Number of tiles placed.
     */
    public int getTilesPlacedThisRound() {
        return this.tilesPlacedThisRound;
    }

    /**
     * Gets the number of special tiles placed in the current round.
     *
     * @return Number of special tiles placed.
     */
    public int getSpecialsTilesPlacedThisRound() {
        return this.specialTilesPlacedThisRound;
    }

    /**
     * Resets the player's status for the next round.
     */
    public void resetForNextRound() {
        this.tilesPlacedThisRound = 0;
        this.specialTilesPlacedThisRound = 0;
        this.faces.clear();
    }

    /**
     * Gets the player's ID.
     *
     * @return The player's ID.
     */
    public String getId() {
        return this.id;
    }

    /**
     * Gets the player's current state.
     *
     * @return The player's state.
     */
    public State getState() {
        return this.state;
    }

    /**
     * Gets the player's board.
     *
     * @return The player's board.
     */
    public Board getBoard() {
        return this.board;
    }

    /**
     * Returns a string representation of the player.
     *
     * @return String describing the player.
     */
    public String toString() {
        return "This player's ID is " + this.id;
    }

    /**
     * Updates the player's state.
     *
     * @param state The new state of the player.
     */
    public void updateState(State state) {
        this.state = state;
    }

    /**
     * Sets whether the player has been elected.
     *
     * @param bool True if elected, false otherwise.
     */
    public void setElects(boolean bool) {
        this.elects = bool;
    }

    /**
     * Checks if the player has been elected.
     *
     * @return True if elected, false otherwise.
     */
    public boolean getElects() {
        return this.elects;
    }

    /**
     * Gets a face by its ID.
     *
     * @param id The face ID.
     * @return The corresponding face, or null if not found.
     */
    public AbstractFace getCellById(String id) {
        for(AbstractFace cell : this.faces) {
            if(cell.getId().contentEquals(id)) {
                return cell;
            }
        }
        return null;
    }

    /**
     * Removes a specific face from the player's collection.
     *
     * @param face The face to remove.
     */
    public void removeFace(AbstractFace face) {
        this.faces.remove(face);
    }



    /**
     * Adds a face to the player's collection.
     *
     * @param face The face to add.
     */
    public void addFaces(AbstractFace face) {
        this.faces.add(face);
    }

    /**
     * Checks if the player can play.
     *
     * @return Always true (placeholder for future conditions).
     */
    public boolean canPlay() {
        return true;
    }

    /**
     * Checks if the player needs to play before the next round.
     *
     * @return True if the player still has actions to take.
     */
    public boolean needToPlay() {
        return !(this.tilesPlacedThisRound == 4);
    }

    /**
     * Checks if the player is an admin.
     *
     * @return True if the player is an admin, false otherwise.
     */
    public boolean isAdmin() {
        return this.id.contentEquals("admin");
    }

    /**
     * Gets the list of faces the player currently holds.
     *
     * @return List of faces.
     */
    public List<AbstractFace> getFaces() {
        return this.faces;
    }

    /**
     * Adds points to the player's score.
     *
     * @param score The points to add.
     */
    public void addScore(int score) {
        this.score += score;
    }

    /**
     * Gets the player's current score.
     *
     * @return The player's score.
     */
    public int getScore(){
        return this.score;
    }
}

