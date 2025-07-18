package scene.analytics;

import board.Board;
import main.Game;
import main.GlobalPlayer;
import main.Player;
import main.Round;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class AnalyticsData
 */
public class AnalyticsData {

    private GlobalPlayer currentPlayer;
    private Map<String, GlobalPlayer> playersWithIds;
    private Map<String, Game> allGames;
    private Game currentGame;
    private Round currentRound;


    public AnalyticsData() {
        this.currentPlayer = null;
        this.playersWithIds = new HashMap<>();
        this.allGames = new HashMap<>();
        this.currentGame = null;
        this.currentRound = null;
    }


    public Round getCurrentRound() {
        return currentRound;
    }

    public void setCurrentRound(Round currentRound) {
        this.currentRound = currentRound;
    }

    public GlobalPlayer getCurrentPlayer() {
        return currentPlayer;
    }

    public Map<String, GlobalPlayer> getPlayersWithIds() {
        return playersWithIds;
    }



    public Map<String, Game> getAllGames() {
        return allGames;
    }


    public void setCurrentPlayer(GlobalPlayer currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public void setPlayersWithIds(Map<String, GlobalPlayer> playersWithIds) {
        this.playersWithIds = playersWithIds;
    }


    public void setAllGames(Map<String, Game> allGames) {
        this.allGames = allGames;
    }


    public void addAllPlayersWithIds(Map<String, GlobalPlayer> playersWithIds) {
        this.playersWithIds.putAll(playersWithIds);
    }



    public void addAllGames(Map<String, Game> allGames) {
        this.allGames.putAll(allGames);
    }


    public boolean containsGameById(String id) {
        return this.allGames.containsKey(id);
    }


    public void setCurrentGame(Game selectedGame) {
        this.currentGame = selectedGame;
    }

    public Game getCurrentGame() {
        return currentGame;
    }


    public void resetData() {
        this.currentPlayer = null;
        this.playersWithIds.clear();
        this.allGames.clear();
        this.currentGame = null;
        this.currentRound = null;
    }
}
