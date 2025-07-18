package util;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class MusicManager {

    private static MusicManager instance;
    private MediaPlayer mediaPlayer;
    private boolean isMusicEnabled = false;
    private double volume = 0.5;
    private String currentMusic;
    private Map<String, String> musicTracks = new HashMap<>();

    private MusicManager() {
        musicTracks.put("Americano", "/music/test.mp3");
        musicTracks.put("DOOM", "/music/DOOM.mp3");
        musicTracks.put("Lo-FI Jazz", "/music/jazz.mp3");
    }

    public static MusicManager getInstance() {
        if (instance == null) {
            instance = new MusicManager();
        }
        return instance;
    }

    public void playMusic(String musicName) {
        if (!isMusicEnabled) return;

        String musicPath = musicTracks.get(musicName);
        if (musicPath == null) return;

        stopMusic();

        try {
            String path = getClass().getResource(musicPath).toString();
            Media media = new Media(path);
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setVolume(volume);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            mediaPlayer.play();
            currentMusic = musicName;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.dispose();
            mediaPlayer = null;
        }
    }

    public void setMusicEnabled(boolean enabled) {
        isMusicEnabled = enabled;
        if (isMusicEnabled && currentMusic != null) {
            playMusic(currentMusic);
        } else if (!isMusicEnabled) {
            stopMusic();
        }
    }

    public boolean isMusicEnabled() {
        return isMusicEnabled;
    }

    public void setVolume(double volume) {
        this.volume = volume;
        if (mediaPlayer != null) {
            mediaPlayer.setVolume(volume);
        }
    }

    public double getVolume() {
        return volume;
    }

    public Map<String, String> getMusicTracks() {
        return musicTracks;
    }

    public String getCurrentMusic() {
        return currentMusic;
    }
}