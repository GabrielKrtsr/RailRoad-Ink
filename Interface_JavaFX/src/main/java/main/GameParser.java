package main;

import face.AbstractFace;
import interpreter.FaceFactory;
import interpreter.RotationFactory;
import score.calculation.*;
import util.Type;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;


import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;

public class GameParser {

    /** Command constant for player election */
    public static final String COMMAND_ELECTS = "ELECTS";
    /** Command constant for starting a game */
    public static final String COMMAND_PLAYS = "PLAYS";
    /** Command constant for throwing faces */
    public static final String COMMAND_THROWS = "THROWS";
    /** Command constant for placing faces */
    public static final String COMMAND_PLACES = "PLACES";
    /** Command constant for scoring */
    public static final String COMMAND_SCORES = "SCORES";
    /** Command constant for blaming */
    public static final String COMMAND_BLAMES = "BLAMES";
    /** Command constant for yielding */
    public static final String COMMAND_YIELDS = "YIELDS";
    /** Command constant for scoring a round */
    public static final String COMMAND_SCOREROUND = "SCOREROUND";

    private File file;

    private Map<String, Game> gamesWithIdsMap;

    private List<Game> games;

    private Map<String, GlobalPlayer> allPlayers;

    private List<Player> currentGamePlayer;

    private Game currentGame;

    private Round currentRound;

    private Set<String> usedIconNames; // Хранилище уже использованных иконок
    private List<String> availableIcons; // Доступные иконки из resources/icons

    /**
     * Creates a new GameParser that reads from the specified file
     *
     * @param fileUrl The path to the game file
     * @throws FileNotFoundException If the specified file cannot be found
     */
    public GameParser(String fileUrl) throws FileNotFoundException {
        this.file = new File(fileUrl);
        this.gamesWithIdsMap = new HashMap<>();
        this.games = new ArrayList<>();
        this.allPlayers = new HashMap<>();
        this.currentGamePlayer = new ArrayList<>();
        this.usedIconNames = new HashSet<>();            // Инициализация списков
        this.availableIcons = loadIconsFromResources(); // Загрузка доступных иконок
        this.currentGame = null;
        this.currentRound = null;
        parse();
    }

    /**
     * Factory method to load available icons from the "resources/icons" directory.
     *
     * @return A list of available icon file names.
     */
    private List<String> loadIconsFromResources() {
        List<String> icons = new ArrayList<>();
        try {
            URL resourceUrl = getClass().getClassLoader().getResource("images/icons");
            if (resourceUrl != null) {
                File iconsDirectory = new File(resourceUrl.toURI());
                if (iconsDirectory.isDirectory()) {
                    File[] files = iconsDirectory.listFiles();
                    if (files != null) {
                        for (File file : files) {
                            if (file.isFile() && (file.getName().endsWith(".png") || file.getName().endsWith(".jpg"))) {
                                icons.add(file.getName());
                            }
                        }
                    }
                }
            } else {
                System.err.println("Error: 'icons' directory not found!");
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return icons;
    }

    /**
     * Generates a unique icon name for a player from the list of available icons.
     *
     * @return A unique icon name for the player.
     */
    private String generateUniqueIconName() {
        if (availableIcons.isEmpty()) {
            throw new IllegalStateException("No available icons to assign!");
        }
        Random random = new Random();
        String iconName;
        do {
            iconName = availableIcons.get(random.nextInt(availableIcons.size()));
        } while (usedIconNames.contains(iconName));
        usedIconNames.add(iconName);
        return iconName;
    }

    /**
     * Adds an icon to a new global player.
     *
     * @param player The player to whom the icon will be assigned.
     * @return GlobalPlayer with an assigned icon.
     */
    private GlobalPlayer addIconToPlayer(Player player) {
        String iconName = generateUniqueIconName();
        GlobalPlayer globalPlayer = new GlobalPlayer(player.getId());
        globalPlayer.setIconPath(iconName);
        return globalPlayer;
    }

    /**
     * States that represent the current parsing context.
     */
    private enum State {
        INIT,
        ELECTS,
        PLAYS,
        THROWS,
        PLACES
    }

    /**
     * Parses the game file and processes all commands
     */
    private void parse() {
        try (Scanner scanner = new Scanner(file)) {
            State state = State.INIT;

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                List<String> tokens = Arrays.asList(line.split(" "));
                String commandId = tokens.get(0);
                String command = tokens.get(1);

                System.out.println(command + " " + state);

                state = processTransitions(state, command, tokens, commandId, currentGame, currentRound);
            }

            if (currentRound != null && currentGame != null) {
                currentGame.finishCurrentRound();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }



    /**
     * Handles the PLAYS command by creating a new game and adding all current players to it.
     *
     * @return A newly created game.
     */
    private Game handlePLAYS() {
        Game game = new Game();
        String id = this.generateUniqueId();
        game.setId(id);
        for (Player player : currentGamePlayer) {
            game.addPlayer(player);
            if (this.allPlayers.containsKey(player.getId())) {
                GlobalPlayer globalPlayer = allPlayers.get(player.getId());
                globalPlayer.addGamePlayer(game, player);
            } else {
                GlobalPlayer globalPlayer = addIconToPlayer(player); // Создаем игрока с иконкой
                allPlayers.put(globalPlayer.getId(), globalPlayer);
                globalPlayer.addGamePlayer(game, player);
            }
        }
        games.add(game);
        gamesWithIdsMap.put(id, game);
        currentGamePlayer = new ArrayList<>(); // Reinitialize currentGamePlayer
        return game;
    }




    /**
     * Processes state transitions based on the current state and command.
     *
     * @param state The current state of the parser
     * @param command The command being processed
     * @param tokens The tokenized command line
     * @param commandId The ID of the command
     * @param currentGame The current game in context
     * @param currentRound The current round in context
     * @return The new state after processing the command
     */
    private State processTransitions(State state, String command, List<String> tokens, String commandId, Game currentGame, Round currentRound) {
        switch (state) {
            case INIT:
                if (COMMAND_ELECTS.equals(command)) {
                    handleELECTS(tokens);
                    return State.ELECTS;
                }
                break;

            case ELECTS:
                if (COMMAND_PLAYS.equals(command)) {
                    this.currentGame = handlePLAYS();
                    return State.PLAYS;
                } else if (COMMAND_ELECTS.equals(command)) {
                    handleELECTS(tokens);
                    return State.ELECTS;
                }
                break;

            case PLAYS:
                if (COMMAND_THROWS.equals(command)) {
                    this.currentRound = handleTHROWS(currentGame, tokens);
                    return State.THROWS;
                }
                break;

            case THROWS:
                if (COMMAND_PLACES.equals(command)) {
                    handlePLACES(commandId, currentGame, tokens);
                    return State.PLACES;
                } else {
                    if (currentRound != null && currentGame != null) {
                        currentGame.finishCurrentRound();
                    }
                    return State.INIT;
                }

            case PLACES:
                if (COMMAND_PLACES.equals(command) ) {
                    handlePLACES(commandId, currentGame, tokens);
                    return State.PLACES;
                }
                else if(COMMAND_BLAMES.equals(command)){
                    handleBlames(currentGame, tokens);
                    return State.PLACES;
                }
                else if(COMMAND_SCORES.equals(command) || COMMAND_SCOREROUND.equals(command) || COMMAND_YIELDS.equals(command)){
                    return State.PLACES;
                }
                else {
                    if (currentRound != null && currentGame != null) {
                        currentGame.finishCurrentRound();
                    }
                    if (COMMAND_THROWS.equals(command)) {
                        this.currentRound = handleTHROWS(currentGame, tokens);
                        return State.THROWS;
                    } else if (COMMAND_ELECTS.equals(command)) {
                        handleELECTS(tokens);
                        return State.ELECTS;
                    } else {
                        return State.INIT;
                    }
                }
            default:
                return State.INIT;
        }
        return state;
    }

    /**
     * Handles the ELECTS command by creating players and adding them to the current game player list
     *
     * @param tokens The tokenized command line with player IDs
     */
    private void handleELECTS(List<String> tokens) {
        for (int i = 2; i < tokens.size(); i++) {
            String playerId = tokens.get(i);
            Player player = new Player(playerId);
            if(!player.isAdmin()){
                player.setElects(true);
            }

            currentGamePlayer.add(player);
        }
        PlayerAI playerAI = new PlayerAI("AIComparator");
        playerAI.setElects(true);
        currentGamePlayer.add(playerAI);

    }


    /**
     * Handles the BLAMES command by incrementing the number of blames for the GlobalPlayer with the given ID.
     *
     * @param currentGame The current game in context
     * @param tokens      The tokenized command line
     */
    private void handleBlames(Game currentGame, List<String> tokens) {
        String playerId = tokens.get(0);
        if (allPlayers.containsKey(playerId)) {
            GlobalPlayer globalPlayer = allPlayers.get(playerId);
            globalPlayer.incrementNbBlames(currentGame);
        }
    }





    /**
     * Handles the THROWS command by creating a new round and adding the specified faces
     *
     * @param currentGame The current game in context
     * @param tokens The tokenized command line with face specifications
     * @return The newly created round
     */
    private Round handleTHROWS(Game currentGame, List<String> tokens) {

        Round round = currentGame.newRound();
        for (int i = 2; i < tokens.size(); i++) {
            try {
                round.addFace(FaceFactory.getFace(tokens.get(i)).clone());
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }
        try {
            round.distributeFaces(currentGame.getElectedPlayers());
        } catch (Exception e) {
            System.err.println("Error distributing faces: " + e.getMessage());
        }
        return round;
    }

    /**
     * Handles the PLACES command by placing a face on the board at the specified position
     *
     * @param id The ID of the player placing the face
     * @param currentGame The current game in context
     * @param tokens The tokenized command line with placement details
     */
    private void handlePLACES(String id, Game currentGame, List<String> tokens) {
        String tile = tokens.get(2);
        String orientation = tokens.get(3);
        String cell = tokens.get(4).toUpperCase();

        int y = cell.charAt(0) - 'A';
        int x = Integer.parseInt(cell.substring(1)) - 1;

        Game latestGame = currentGame;
        if (latestGame == null){
            return;
        }
        Player author = latestGame.getPlayerById(id);

        AbstractFace face = author.getCellById(tile);
        if (face != null && canPlaceFace(author, face)) {
            if (!"null".equals(orientation)) {
                RotationFactory.getStrategy(orientation).rotate(face);
            }
            if (author.getBoard().placeFaceInTheCell(x, y, face)) {
                author.removeFace(face);
                //author.getBoard().display();
            }
        }
    }


    /**
     * Checks if a player can place a face based on game rules.
     *
     * @param player The player attempting to place a face
     * @param face The face to be placed
     * @return true if the face can be placed, false otherwise
     */
    private boolean canPlaceFace(Player player, AbstractFace face) {
        if (face.getType() == Type.SPECIAL) {
            return player.getSpecialsTilesPlacedThisRound() < 1;
        } else {
            return player.getTilesPlacedThisRound() < 4;
        }
    }



    /**
     * Generates a random 5-character ID consisting of letters and numbers.
     *
     * @return A randomly generated ID string
     */
    private String generateId() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder id = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            int index = random.nextInt(characters.length());
            id.append(characters.charAt(index));
        }
        return id.toString();
    }

    /**
     * Generates a unique ID for a game by repeatedly calling generateId until a unique ID is found.
     *
     * @return A unique ID string
     */
    private String generateUniqueId() {
        String id;
        do {
            id = generateId();
        } while (gamesWithIdsMap.containsKey(id));
        return id;
    }

    /**
     * Gets all players from all games parsed.
     *
     * @return A map of player IDs to Player objects
     */
    public Map<String, GlobalPlayer> getAllPlayers() {
        return allPlayers;
    }

    /**
     * Gets all games parsed from the file with their IDs.
     *
     * @return A map of game IDs to Game objects
     */
    public Map<String, Game> getGames() {
        return gamesWithIdsMap;
    }
}

