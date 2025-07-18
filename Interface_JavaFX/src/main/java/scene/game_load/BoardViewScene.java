package scene.game_load;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import main.Game;
import main.GameLoaderApp;
import main.Player;
import main.Round;
import scene.boardNavigation.BoardDisplayController;
import scene.boardNavigation.NavigationController;
import scene.detection.DetectionRegion;
import scene.detection.ScoreRegion;
import scene.game_load.GameData;
import scene.setting.SettingData;

/**
 * Class BoardViewScene
 */
public class BoardViewScene {
    private GameLoaderApp app;
    private Scene scene;
    private NavigationController navigationController;
    private BoardDisplayController boardDisplayController;
    private DetectionRegion detectionRegion;
    private SettingData settingData;
    private ScoreRegion scoreRegion;
    private GameData gameData;

    /**
     * Initializes a new instance of the board view screen
     *
     * @param app The instance of the main application
     * @param gameData The game data to use
     * @param setting The application settings to use
     */
    public BoardViewScene(GameLoaderApp app, GameData gameData, SettingData setting) {
        this.app = app;
        navigationController = new NavigationController(this, gameData);
        detectionRegion = new DetectionRegion();
        boardDisplayController = new BoardDisplayController(gameData,this,detectionRegion);
        this.gameData = gameData;
        Game currentGame = gameData.getAllGames().get(gameData.getCurrentGameId());
        Round currentRound = currentGame.getRounds().get(gameData.getCurrentRound());
        Player currentPlayer = currentGame.getPlayerById(gameData.getCurrentPlayerId());


        this.scoreRegion = new ScoreRegion(currentRound,currentPlayer);
        settingData = setting;
        BorderPane layout = createLayout();
        scene = new Scene(layout, 800, 800);
        scene.getStylesheets().add(getClass().getResource(settingData.getStyle()).toExternalForm());
        boardDisplayController.displayBoard();
    }

    /**
     * Creates the layout for the board view screen
     *
     * @return The configured BorderPane layout
     */
    private BorderPane createLayout() {
        HBox controlButtonsBox = createControlButtons();
        HBox navigationButtonsBox = navigationController.createNavigationButtons();
        VBox topControls = new VBox(10, controlButtonsBox, navigationButtonsBox);
        BorderPane layout = new BorderPane();
        layout.setTop(topControls);
        layout.setCenter(boardDisplayController.getBoardWebView());


        VBox scoreDetection = new VBox(10, detectionRegion.getRegion(), scoreRegion.getRegion());
        layout.setBottom(scoreDetection);

        return layout;
    }

    /**
     * Creates the control buttons for the board view
     *
     * @return An HBox containing the control buttons
     */
    private HBox createControlButtons() {
        Button backButton = new Button("Back to Selection");
        backButton.setId("selectionButton");
        backButton.setOnAction(e -> app.showSelectionScene());
        HBox controlButtonsBox = new HBox(10, backButton);
        controlButtonsBox.setPadding(new Insets(10));
        return controlButtonsBox;
    }

    /**
     * Updates the board display with new SVG content
     *
     * @param svg The SVG content to display
     */
    public void updateBoardDisplay(String svg){
        this.boardDisplayController.updateBoardDisplay(svg);
    }

    /**
     * Gets the JavaFX scene for this board view screen
     *
     * @return The configured JavaFX scene
     */
    public Scene getScene() {
        return scene;
    }

    /**
     * Gets the board display controller
     *
     * @return The board display controller
     */
    public BoardDisplayController getBoardDisplayController() {
        return boardDisplayController;
    }

    /**
     * Gets the detection region
     *
     * @return The detection region
     */
    public DetectionRegion getDetectionRegion() {
        return detectionRegion;
    }

    public ScoreRegion getScoreRegion(){
        return scoreRegion;
    }

}