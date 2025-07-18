package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import board.Board;
import cell.Cell;
import face.AbstractFace;
import score.ClassicScoreManager;
import score.calculation.*;
import score.detection.*;
import util.Path;
import util.util_for_nodes.Side;

/**
 * The Round class represents a game round where faces are distributed to players.
 * It manages the faces that are thrown and their distribution to players.
 */
public class Round {

    private Map<Player,Board> playerBoardMap;

    private Map<Player, List<List<Cell>>> networks;
    private Map<Player, List<Cell>> rails;
    private Map<Player, List<Cell>> roads;
    private Map<Player, Map<Cell, List<Side>>> falseRoutes;
    private Map<Player, List<Cell>> centerCells;


    private Map<Player, Integer> networksScore;
    private Map<Player, Integer> railsScore;
    private Map<Player, Integer> roadsScore;
    private Map<Player, Integer> falseRoutesScore;
    private Map<Player, Integer> centerCellsScore;

    private Map<Player, Integer> totalScore;

    private List<AbstractFace> throwsTiles;

    /**
     * Constructor for the Round class.
     * Initializes the list of thrown faces.
     */
    public Round(){
        this.throwsTiles = new ArrayList<>();
        this.playerBoardMap = new HashMap<>();


        this.networks = new HashMap<>();
        this.rails = new HashMap<>();
        this.roads = new HashMap<>();
        this.falseRoutes = new HashMap<>();
        this.centerCells = new HashMap<>();
        

        this.networksScore = new HashMap<>();
        this.railsScore = new HashMap<>();
        this.roadsScore = new HashMap<>();
        this.falseRoutesScore = new HashMap<>();
        this.centerCellsScore = new HashMap<>();
        this.totalScore = new HashMap<>();
    }

    /**
     * Distributes faces to the specified players by cloning each face for each player.
     *
     * @param targets List of players to distribute faces to.
     * @throws CloneNotSupportedException If cloning a face fails.
     */
    public void distributeFaces(List<Player> targets) throws CloneNotSupportedException {
        PlayerAI playerAI = null;
        for(Player player : targets) {
            player.resetForNextRound();
            for(AbstractFace face : this.throwsTiles) {
                player.addFaces(face.clone());
                if(player.getId().equals("AIComparator")){
                    playerAI = (PlayerAI) player;
                }
            }
        }
        if(playerAI != null){
            playerAI.play();
        }
    }

    /**
     * Adds a face to the list of faces thrown during this round.
     *
     * @param face The face to add.
     */
    public void addFace(AbstractFace face) {
        this.throwsTiles.add(face);
    }

    /**
     * Searches for a face by its identifier in the list of thrown faces.
     *
     * @param id The identifier of the face to search for.
     * @return The corresponding face if found, otherwise null.
     */
    public AbstractFace getFaceByid(String id) {
        for(AbstractFace face : this.throwsTiles) {
            if(face.getId().contentEquals(id.toUpperCase())) {
                return face;
            }
        }
        return null;
    }


    /**
     * Places the current state of the board for each player.
     *
     * @param targets List of players whose boards need to be placed.
     */
    public void placeCurrentBoardState(List<Player> targets) {
        for (Player player : targets) {
            Board currentBoard = player.getBoard();
            this.networks.put(player, NetworksDetector.detectNetworks(player.getBoard()));
            this.rails.put(player, LongestPathDetectorWithStations.detectLongestPath(player.getBoard(), Path.RAIL));
            this.roads.put(player, LongestPathDetectorWithStations.detectLongestPath(player.getBoard(), Path.ROAD));
            this.falseRoutes.put(player, FalseRouteDetectorWithoutBoardNodes.detectFalseRoutesWithSides(player.getBoard()));
            this.centerCells.put(player, CentralCellsDetector.detectCentralCellsWithFaces(player.getBoard()));


            this.networksScore.put(player, (int) new NetworksConnectedCalculator().calculateScore(player.getBoard()));
            this.railsScore.put(player, (int) new LongestRailCalculatorWithStations().calculateScore(player.getBoard()));
            this.roadsScore.put(player, (int) new LongestRoadCalculatorWithStations().calculateScore(player.getBoard()));
            this.falseRoutesScore.put(player, (int) new FalseRouteCalculatorWithoutBoardNodes().calculateScore(player.getBoard()));
            this.centerCellsScore.put(player, (int) new CentralCellsCalculator().calculateScore(player.getBoard()));
            this.totalScore.put(player, (int) new ClassicScoreManager().calculateFinalScore(player.getBoard()));
            
            this.playerBoardMap.put(player, currentBoard.clone());
        }
    }

    /**
     * Gets network scores.
     *
     * @return Map of player network scores
     */
    public Map<Player, Integer> getNetworksScore() {
        return networksScore;
    }

    /**
     * Gets rail scores.
     *
     * @return Map of player rail scores
     */
    public Map<Player, Integer> getRailsScore() {
        return railsScore;
    }

    /**
     * Gets road scores.
     *
     * @return Map of player road scores
     */
    public Map<Player, Integer> getRoadsScore() {
        return roadsScore;
    }

    /**
     * Gets false route scores.
     *
     * @return Map of player false route scores
     */
    public Map<Player, Integer> getFalseRoutesScore() {
        return falseRoutesScore;
    }

    /**
     * Gets center cell scores.
     *
     * @return Map of player center cell scores
     */
    public Map<Player, Integer> getCenterCellsScore() {
        return centerCellsScore;
    }

    /**
     * Gets total scores.
     *
     * @return Map of player total scores
     */
    public Map<Player, Integer> getTotalScore() {
        return totalScore;
    }

    /**
     * Gets player networks.
     *
     * @return Map of player networks
     */
    public Map<Player, List<List<Cell>>> getNetworks() {
        return networks;
    }

    /**
     * Gets player center cells.
     *
     * @return Map of player center cells
     */
    public Map<Player, List<Cell>> getCenterCells() {
        return centerCells;
    }

    /**
     * Gets player rails.
     *
     * @return Map of player rails
     */
    public Map<Player, List<Cell>> getRails() {
        return rails;
    }

    /**
     * Gets player roads.
     *
     * @return Map of player roads
     */
    public Map<Player, List<Cell>> getRoads() {
        return roads;
    }

    /**
     * Gets player false routes.
     *
     * @return Map of player false routes
     */
    public Map<Player, Map<Cell, List<Side>>> getFalseRoutes() {
        return falseRoutes;
    }

    /**
     * Get player and him board
     *
     * @return the map with all player and him board
     */
    public Map<Player, Board> getPlayerBoardMap() {
        return playerBoardMap;
    }
}
