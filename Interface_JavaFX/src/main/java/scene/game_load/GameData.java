package scene.game_load;

import java.util.*;

import main.Game;
import main.Player;

/**
 * Class GameData
 */
public class GameData {
    private Map<String, Game> allGames = new HashMap<>();
    private String currentPlayerId;
    private String currentGameId;
    private int currentRound = -1;
    private String boardSvgTemplate;
    private Set<String> selectedPlayers = new HashSet<>();
    private Set<String> selectedGames = new HashSet<>();
    private Set<Integer> selectedRounds = new HashSet<>();



    /**
     * Gets the map of all games in the system
     *
     * @return Map of all games indexed by game ID
     */
    public Map<String, Game> getAllGames() {
        return allGames;
    }

    /**
     * Sets the map of all games
     *
     * @param allGames Map of games indexed by game ID
     */
    public void setAllGames(Map<String, Game> allGames) {
        this.allGames = allGames;
    }

    /**
     * Gets the ID of the currently selected player
     *
     * @return The current player ID
     */
    public String getCurrentPlayerId() {
        return currentPlayerId;
    }

    /**
     * Sets the ID of the currently selected player
     *
     * @param currentPlayerId The player ID to set as current
     */
    public void setCurrentPlayerId(String currentPlayerId) {
        this.currentPlayerId = currentPlayerId;
    }

    /**
     * Gets the ID of the currently selected game
     *
     * @return The current game ID
     */
    public String getCurrentGameId() {
        return currentGameId;
    }

    /**
     * Sets the ID of the currently selected game
     *
     * @param currentGameId The game ID to set as current
     */
    public void setCurrentGameId(String currentGameId) {
        this.currentGameId = currentGameId;
    }

    /**
     * Gets the currently selected round number
     *
     * @return The current round number
     */
    public int getCurrentRound() {
        return currentRound;
    }

    /**
     * Sets the currently selected round number
     *
     * @param currentRound The round number to set as current
     */
    public void setCurrentRound(int currentRound) {
        this.currentRound = currentRound;
    }

    /**
     * Gets the SVG template for the game board
     *
     * @return The board SVG template as a string
     */
    public String getBoardSvgTemplate() {
        return boardSvgTemplate;
    }

    /**
     * Sets the SVG template for the game board
     *
     * @param boardSvgTemplate The SVG template string to set
     */
    public void setBoardSvgTemplate(String boardSvgTemplate) {
        this.boardSvgTemplate = boardSvgTemplate;
    }

    /**
     * Gets the set of selected player IDs
     *
     * @return Set of selected player IDs
     */
    public Set<String> getSelectedPlayers() {
        return selectedPlayers;
    }

    /**
     * Gets the set of selected game IDs
     *
     * @return Set of selected game IDs
     */
    public Set<String> getSelectedGames() {
        return selectedGames;
    }

    /**
     * Gets the set of selected round numbers
     *
     * @return Set of selected round numbers
     */
    public Set<Integer> getSelectedRounds() {
        return selectedRounds;
    }

    /**
     * Clears all selections
     */
    public void clearSelections() {
        selectedGames.clear();
        selectedPlayers.clear();
        selectedRounds.clear();
    }
}