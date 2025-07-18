package scene.boardNavigation;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import main.Game;
import main.Player;
import main.Round;
import scene.game_load.BoardViewScene;
import scene.game_load.GameData;

import java.util.ArrayList;
import java.util.List;

/**
 * Class NavigationController
 */
public class NavigationController {
    private BoardViewScene boardViewScene;
    private GameData gameData;

    /**
     * Constructs a new navigation controller.
     *
     * @param boardViewScene the board display scene to update during navigation
     * @param gameData the game data containing information about players, rounds, and games
     */
    public NavigationController(BoardViewScene boardViewScene, GameData gameData) {
        this.boardViewScene = boardViewScene;
        this.gameData = gameData;
    }

    /**
     * Creates and configures navigation buttons organized in an HBox container.
     * Includes buttons for navigating between players, rounds, and games.
     *
     * @return an HBox container with all configured navigation buttons
     */
    public HBox createNavigationButtons() {
        Button prevPlayerButton = new Button("◀ Prev Player");
        Button nextPlayerButton = new Button("Next Player ▶");
        Button prevRoundButton = new Button("◀ Prev Round");
        Button nextRoundButton = new Button("Next Round ▶");
        Button prevGameButton = new Button("◀ Prev Game");
        Button nextGameButton = new Button("Next Game ▶");
        prevPlayerButton.setOnAction(e -> navigatePlayer(-1));
        nextPlayerButton.setOnAction(e -> navigatePlayer(1));
        prevRoundButton.setOnAction(e -> navigateRound(-1));
        nextRoundButton.setOnAction(e -> navigateRound(1));
        prevGameButton.setOnAction(e -> navigateGame(-1));
        nextGameButton.setOnAction(e -> navigateGame(1));
        HBox playerNav = new HBox(5, prevPlayerButton, nextPlayerButton);
        HBox roundNav = new HBox(5, prevRoundButton, nextRoundButton);
        HBox gameNav = new HBox(5, prevGameButton, nextGameButton);
        HBox navigationButtons = new HBox(15, gameNav, playerNav, roundNav);
        navigationButtons.setPadding(new javafx.geometry.Insets(10));
        return navigationButtons;
    }

    /**
     * Navigates to the previous or next player in the selected players list.
     *
     * @param delta the direction of navigation (-1 for previous, 1 for next)
     */
    private void navigatePlayer(int delta) {
        List<String> playerIds = new ArrayList<>(gameData.getSelectedPlayers());
        if (playerIds.isEmpty()) return;
        int currentIndex = playerIds.indexOf(gameData.getCurrentPlayerId());
        int newIndex = (currentIndex + delta + playerIds.size()) % playerIds.size();
        gameData.setCurrentPlayerId(playerIds.get(newIndex));
        updateAfterNavigation();
    }

    /**
     * Navigates to the previous or next round in the selected rounds list.
     *
     * @param delta the direction of navigation (-1 for previous, 1 for next)
     */
    private void navigateRound(int delta) {
        List<Integer> rounds = new ArrayList<>(gameData.getSelectedRounds());
        if (rounds.isEmpty()) return;
        int currentIndex = rounds.indexOf(gameData.getCurrentRound());
        int newIndex = (currentIndex + delta + rounds.size()) % rounds.size();
        gameData.setCurrentRound(rounds.get(newIndex));
        updateAfterNavigation();
    }

    /**
     * Navigates to the previous or next game in the selected games list.
     *
     * @param delta the direction of navigation (-1 for previous, 1 for next)
     */
    private void navigateGame(int delta) {
        List<String> gameIds = new ArrayList<>(gameData.getSelectedGames());
        if (gameIds.isEmpty()) return;
        int currentIndex = gameIds.indexOf(gameData.getCurrentGameId());
        int newIndex = (currentIndex + delta + gameIds.size()) % gameIds.size();
        gameData.setCurrentGameId(gameIds.get(newIndex));
        updateAfterNavigation();
    }

    /**
     * Updates the board display and detection region after navigation.
     * This method refreshes the UI to reflect the newly selected player, round, or game.
     */
    private void updateAfterNavigation() {
        boardViewScene.getBoardDisplayController().displayBoard();
        boardViewScene.getDetectionRegion().resetCheckBox();
        Game currentGame = gameData.getAllGames().get(gameData.getCurrentGameId());
        Round currentRound = currentGame.getRounds().get(gameData.getCurrentRound());
        Player currentPlayer = currentGame.getPlayerById(gameData.getCurrentPlayerId());
        boardViewScene.getScoreRegion().updateScoreRegion(currentRound,currentPlayer);
    }
}