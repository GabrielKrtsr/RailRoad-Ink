package scene.setting.controllers;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import scene.setting.SettingData;
import util.MusicManager;

/**
 * Class MusicController
 */
public class MusicController {

    private SettingData settingData;

    /**
     * Creates a new MusicController instance
     *
     * @param settingData The SettingData instance
     */
    public MusicController(SettingData settingData) {
        this.settingData = settingData;
    }

    /**
     * Creates a VBox containing music control elements
     *
     * @return A VBox with music control settings
     */
    public VBox createMusicControls() {
        VBox musicBox = new VBox(15);
        musicBox.setAlignment(javafx.geometry.Pos.CENTER);
        musicBox.setLayoutY(300);
        musicBox.setId("checkContainer");

        Label musicLabel = new Label("Music Settings:");
        musicLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 12px;");

        CheckBox enableMusicCheckbox = new CheckBox("Mute sound");
        enableMusicCheckbox.setSelected(!settingData.isMusicEnabled());
        enableMusicCheckbox.setOnAction(e -> {
            settingData.setMusicEnabled(!enableMusicCheckbox.isSelected());
        });

        Label volumeLabel = new Label("Volume:");
        Slider volumeSlider = new Slider(0, 1, settingData.getMusicVolume());
        volumeSlider.setShowTickLabels(true);
        volumeSlider.setShowTickMarks(true);
        volumeSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            settingData.setMusicVolume(newVal.doubleValue());
        });

        Label trackLabel = new Label("Choose a music");
        ComboBox<String> musicComboBox = new ComboBox<>();
        musicComboBox.getItems().addAll(MusicManager.getInstance().getMusicTracks().keySet());
        musicComboBox.setValue(settingData.getSelectedMusic());
        musicComboBox.setOnAction(e -> {
            settingData.setSelectedMusic(musicComboBox.getValue());
        });

        Button testMusicButton = new Button("Launch");
        testMusicButton.setOnAction(e -> {
            MusicManager.getInstance().playMusic(musicComboBox.getValue());
        });

        musicBox.getChildren().addAll(musicLabel, enableMusicCheckbox, volumeLabel, volumeSlider, trackLabel, musicComboBox, testMusicButton);

        return musicBox;
    }
}