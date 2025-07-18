package scene.setting;

import main.GameLoaderApp;
import util.MusicManager;

/**
 * Class SettingData
 */
public class SettingData {

    private String style = "/style/board_view_style.css";
    private String styleStarter = "../style/starter_style.css";
    private boolean musicEnabled = false;
    private double musicVolume = 0.5;
    private String selectedMusic = "Lo-FI Jazz";
    private String oldScene = "Starter";
    private GameLoaderApp app;

    /**
     * Creates a new SettingData instance linked to the application
     *
     * @param app The main application instance
     */
    public SettingData(GameLoaderApp app) {
        this.app = app;
    }

    /**
     * Gets the starter style path
     *
     * @return The path of the starter style
     */
    public String getStyleStarter() {
        return styleStarter;
    }

    /**
     * Sets the starter style path
     *
     * @param styleStarter The new path for the starter style
     */
    public void setStyleStarter(String styleStarter) {
        this.styleStarter = styleStarter;
    }

    /**
     * Gets the current style path
     *
     * @return The path of the current style
     */
    public String getStyle() {
        return style;
    }

    /**
     * Sets the style path
     *
     * @param style The new path for the style
     */
    public void setStyle(String style) {
        this.style = style;
    }

    /**
     * Navigates back to the previously visited scene.
     */
    public void goToOldScene() {
        switch (oldScene) {
            case "Starter":
                app.showStarterScene();
                break;
            case "Select":
                app.showSelectionScene();
                break;
        }
    }

    /**
     * Sets the previous scene before switching
     *
     * @param oldScene The name of the previous scene
     */
    public void setOldScene(String oldScene) {
        this.oldScene = oldScene;
    }

    /**
     * Checks if music is enabled
     *
     * @return True if music is enabled, false otherwise
     */
    public boolean isMusicEnabled() {
        return musicEnabled;
    }

    /**
     * Enables or disables music
     *
     * @param musicEnabled True to enable music, false to disable it
     */
    public void setMusicEnabled(boolean musicEnabled) {
        this.musicEnabled = musicEnabled;
        MusicManager.getInstance().setMusicEnabled(musicEnabled);
    }

    /**
     * Gets the current music volume
     *
     * @return The current music volume level
     */
    public double getMusicVolume() {
        return musicVolume;
    }

    /**
     * Sets the music volume
     *
     * @param musicVolume The new volume level
     */
    public void setMusicVolume(double musicVolume) {
        this.musicVolume = musicVolume;
        MusicManager.getInstance().setVolume(musicVolume);
    }

    /**
     * Gets the currently selected music track
     *
     * @return The name of the selected music track
     */
    public String getSelectedMusic() {
        return selectedMusic;
    }

    /**
     * Sets the selected music track and plays it if music is enabled
     *
     * @param selectedMusic The name of the new music track
     */
    public void setSelectedMusic(String selectedMusic) {
        this.selectedMusic = selectedMusic;
        if (musicEnabled) {
            MusicManager.getInstance().playMusic(selectedMusic);
        }
    }
}
