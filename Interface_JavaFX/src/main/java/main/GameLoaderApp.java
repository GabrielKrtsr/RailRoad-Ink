package main;

import javafx.application.Application;
import javafx.stage.Stage;
import scene.*;
import scene.analytics.*;
import scene.game_load.*;
import scene.game_load.GameData;
import scene.game_load.SelectionScene;
import scene.setting.SettingData;
import scene.setting.SettingScene;
import util.MusicManager;

public class GameLoaderApp extends Application {

    private Stage primaryStage;
    private GameData gameData;
    private AnalyticsData analyticsData;
    private SettingData settingData;
    private AnalyticsScene analyticsScene;

    /**
     * Entry point of the application. Launches the JavaFX application.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Initializes the application, sets the initial data, and displays the starter screen.
     *
     * @param primaryStage the main container of the application
     */
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.gameData = new GameData();
        this.analyticsData = new AnalyticsData();
        this.settingData = new SettingData(this);
        this.analyticsScene = new AnalyticsScene(this, analyticsData, settingData);

        primaryStage.setTitle("RailRoad");
        showStarterScene();
        initializeMusic();
        primaryStage.setWidth(800);
        primaryStage.setHeight(800);
        primaryStage.show();
        primaryStage.getScene().getStylesheets()
                .add(getClass().getResource("/style/starter_style.css").toExternalForm());
    }

    /**
     * Displays the round selection scene.
     *
     * @param number the round number
     */
    public void showRoundScene(int number) {
        RoundScene scene = new RoundScene(analyticsData, this, number,settingData);
        primaryStage.setScene(scene.getScene());
    }

    public void showAnalyticsScene(){
        analyticsScene.getScene().getStylesheets().clear();
        analyticsScene.getScene().getStylesheets().add(getClass().getResource(settingData.getStyle()).toExternalForm());
        primaryStage.setScene(analyticsScene.getScene());
    }

    /**
     * Displays the game information scene.
     */
    public void showGameScene() {
        GameScene scene = new GameScene(analyticsData, this, settingData);
        primaryStage.setScene(scene.getScene());
    }

    /**
     * Displays the player information scene.
     */
    public void showPlayerScene() {
        PlayerScene scene = new PlayerScene(analyticsData, this,settingData);
        primaryStage.setScene(scene.getScene());
    }

    /**
     * Displays the starter screen (initial scene).
     */
    public void showStarterScene() {
        StarterScene scene = new StarterScene(this, gameData, settingData);
        primaryStage.setScene(scene.getScene());
    }


    /**
     * Displays the selection menu scene.
     */
    public void showSelectionScene() {
        SelectionScene selectionScene = new SelectionScene(this, gameData, settingData);
        primaryStage.setScene(selectionScene.getScene());
    }

    /**
     * Displays the game board view scene.
     */
    public void showBoardViewScene() {
        BoardViewScene boardViewScene = new BoardViewScene(this, gameData, settingData);
        primaryStage.setScene(boardViewScene.getScene());
    }

    /**
     * Displays the settings screen.
     */
    public void showSettingsScene() {
        SettingScene scene = new SettingScene(this, settingData);
        primaryStage.setScene(scene.getScene());
    }

    /**
     * Initializes the background music based on the current settings.
     */
    private void initializeMusic() {
        if (settingData.isMusicEnabled()) {
            MusicManager.getInstance().setVolume(settingData.getMusicVolume());
            MusicManager.getInstance().playMusic(settingData.getSelectedMusic());
        }
    }

    /**
     * Returns the application's primary stage.
     *
     * @return Stage the main container of the application
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }
}