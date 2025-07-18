package scene.setting;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import main.GameLoaderApp;
import scene.animations.BirdAnimation;
import scene.setting.controllers.MusicController;
import scene.setting.controllers.StyleController;

public class SettingScene {

    private GameLoaderApp app;
    private Scene scene;
    private SettingData settingData;
    private StyleController styleManager;
    private MusicController musicManager;

    /**
     * Initializes a new instance of the settings screen
     *
     * @param app The instance of the main application
     * @param data The settings data to use
     */
    public SettingScene(GameLoaderApp app, SettingData data) {
        this.app = app;
        this.settingData = data;
        this.scene = createScene();
    }

    /**
     * Creates and configures the settings scene with all its user interface elements
     *
     * @return The configured JavaFX scene
     */
    private Scene createScene() {
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setPrefSize(800, 800);
        VBox vBox = new VBox(20);
        vBox.setPrefSize(516, 419);
        styleManager = new StyleController(anchorPane,settingData);
        musicManager = new MusicController(settingData);
        styleManager.applyCurrentStyle();
        anchorPane.setId("background");
        vBox.getChildren().add(styleManager.createStyleControls());
        vBox.getChildren().add(musicManager.createMusicControls());
        vBox.setAlignment(javafx.geometry.Pos.CENTER);
        Button backButton = new Button("Back");
        backButton.setLayoutX(50);
        backButton.setLayoutY(700);
        backButton.setOnAction(event -> settingData.goToOldScene());
        anchorPane.getChildren().add(backButton);
        AnchorPane.setLeftAnchor(vBox, 135.0);
        AnchorPane.setTopAnchor(vBox, 135.0);
        anchorPane.getChildren().add(vBox);
        BirdAnimation birdAnimation = new BirdAnimation(anchorPane);
        birdAnimation.setupAnimation();
        birdAnimation.startAnimation();
        return new Scene(anchorPane);
    }

    /**
     * Gets the JavaFX scene for this settings screen
     *
     * @return The configured JavaFX scene
     */
    public Scene getScene() {
        return scene;
    }
}