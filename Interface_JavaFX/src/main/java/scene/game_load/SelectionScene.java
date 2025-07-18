package scene.game_load;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import main.GameLoaderApp;
import scene.selection.GameSelectionController;
import scene.selection.PlayerSelectionController;
import scene.selection.RoundSelectionController;
import scene.setting.SettingData;

/**
 * Class SelectionScene
 */
public class SelectionScene {
    private GameLoaderApp app;
    private GameData gameData;
    private Scene scene;
    private Button applySelectionButton;
    private Button backButton;
    private Button settingsButton;
    private GameSelectionController gameSelection;
    private PlayerSelectionController playerSelection;
    private RoundSelectionController roundSelection;
    private SettingData settingData;


    /**
     * Initializes a new instance of the selection screen.
     *
     * @param app The instance of the main application
     * @param gameData The game data to use
     * @param settingData The application settings to use
     */
    public SelectionScene(GameLoaderApp app, GameData gameData,SettingData settingData) {
        this.app = app;
        this.gameData = gameData;
        this.settingData = settingData;
        applySelectionButton = new Button("Apply Selection");
        applySelectionButton.setDisable(true);
        applySelectionButton.setOnAction(e -> applySelection());
        backButton = new Button("Back to Load");
        settingsButton = new Button("Settings");
        settingsButton.setOnAction(e->{
            settingData.setOldScene("Select");
            app.showSettingsScene();
        });
        backButton.setOnAction(e -> app.showStarterScene());
        gameSelection = new GameSelectionController(gameData, this::updatePlayerPane, this::updateApplyButtonState);
        playerSelection = new PlayerSelectionController(gameData, this::updateRoundPane, this::updateApplyButtonState);
        roundSelection = new RoundSelectionController(gameData, this::updateApplyButtonState);
        VBox mainSelectionContainer = new VBox(10,
                new HBox(10, backButton, applySelectionButton, settingsButton),
                gameSelection.getPane(),
                playerSelection.getPane(),
                roundSelection.getPane()
        );
        mainSelectionContainer.setPadding(new Insets(10));
        BorderPane layout = new BorderPane();
        layout.setTop(mainSelectionContainer);
        scene = new Scene(layout, 800, 800);
        scene.getStylesheets().add(getClass().getResource(settingData.getStyle()).toExternalForm());
        gameSelection.populateCheckBoxes();
    }

    /**
     * Updates the player selection pane based on the selected game
     *
     * @param gameId The ID of the selected game
     */
    private void updatePlayerPane(String gameId) {
        playerSelection.updatePlayerCheckBoxes(gameId);
    }


    /**
     * Updates the round selection pane based on the selected game
     *
     * @param gameId The ID of the selected game
     */
    private void updateRoundPane(String gameId) {
        roundSelection.updateRoundCheckBoxes(gameId);
    }

    /**
     * Updates the enabled state of the Apply Selection button
     */
    private void updateApplyButtonState() {
        applySelectionButton.setDisable(gameData.getSelectedGames().isEmpty() || gameData.getSelectedPlayers().isEmpty() || gameData.getSelectedRounds().isEmpty());
    }
    /**
     * Applies the current selection and navigates to the board view scene
     */
    private void applySelection() {
        if (gameData.getSelectedGames().isEmpty() || gameData.getSelectedPlayers().isEmpty() || gameData.getSelectedRounds().isEmpty()) {
            return;
        }
        gameData.setCurrentGameId(gameData.getSelectedGames().iterator().next());
        gameData.setCurrentPlayerId(gameData.getSelectedPlayers().iterator().next());
        gameData.setCurrentRound(gameData.getSelectedRounds().iterator().next());
        app.showBoardViewScene();
    }


    /**
     * Gets the JavaFX scene for this selection screen.
     *
     * @return The configured JavaFX scene
     */
    public Scene getScene() {
        return scene;
    }
}