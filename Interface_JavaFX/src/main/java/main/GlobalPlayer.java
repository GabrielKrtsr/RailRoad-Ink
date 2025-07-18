package main;

import java.util.HashMap;
import java.util.Map;

public class GlobalPlayer {

    private String id;

    private final Map<Game, Player> gamePlayerMap;
    
    private final Map<Game, Integer> nbBlames;


    private String iconPath;

    public String getIconPath() {
        return iconPath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

    public GlobalPlayer(String id) {
        this.id = id;
        this.gamePlayerMap = new HashMap<>();
        this.nbBlames = new HashMap<>();
    }

    public Map<Game, Integer> getNbBlames() {
        return nbBlames;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<Game, Player> getGamePlayerMap() {
        return gamePlayerMap;
    }

    public void addGamePlayer(Game game, Player player) {
        this.gamePlayerMap.put(game, player);
        this.nbBlames.put(game, 0);

    }

    public void incrementNbBlames (Game game){
        this.nbBlames.put(game, this.nbBlames.getOrDefault(game, 0) + 1);
    }


    public Player getPlayerByGame(Game game) {
        return this.gamePlayerMap.get(game);
    }

    @Override
    public String toString() {
        return "GlobalPlayer{id='" + id + "', gamePlayerMap=" + gamePlayerMap + '}';
    }
}
