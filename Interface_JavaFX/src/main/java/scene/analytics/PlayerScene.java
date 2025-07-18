package scene.analytics;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.GameLoaderApp;
import main.GlobalPlayer;
import main.Player;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.util.Objects;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import scene.setting.SettingData;

import java.util.Objects;

public class PlayerScene {

    private final Scene scene;
    private final AnalyticsData analyticsData;
    private final GameLoaderApp gameLoaderApp;
    private SettingData settingData;

    public PlayerScene(AnalyticsData analyticsData, GameLoaderApp gameLoaderApp,SettingData settingData) {
        this.analyticsData = analyticsData;
        this.gameLoaderApp = gameLoaderApp;
        this.scene = createScene();
        this.settingData = settingData;
        scene.getStylesheets().add(getClass().getResource(settingData.getStyle()).toExternalForm());
    }

    private Scene createScene() {
        BorderPane layout = new BorderPane();
        layout.setPadding(new Insets(20));

        if (analyticsData.getCurrentPlayer() == null) {
            Label errorMessage = new Label("No current player selected.");
            errorMessage.setStyle("-fx-text-fill: red; -fx-font-size: 18px;");
            layout.setTop(errorMessage);
            return new Scene(layout, 800, 800);
        }

        GlobalPlayer currentPlayer = analyticsData.getCurrentPlayer();
        HBox playerInfoSection = createPlayerInfoSection(currentPlayer);
        layout.setTop(playerInfoSection);

        VBox centerSection = new VBox(20);
        centerSection.getChildren().add(createGamesListSection(currentPlayer));
        centerSection.getChildren().add(createPlayerScoreChart(currentPlayer));
        layout.setCenter(centerSection);

        HBox backButtonSection = createBackButtonSection();
        layout.setBottom(backButtonSection);

        return new Scene(layout, 800, 800);
    }

    private HBox createPlayerInfoSection(GlobalPlayer player) {
        HBox playerBox = new HBox(15);
        playerBox.setPadding(new Insets(10));
        playerBox.setAlignment(Pos.CENTER_LEFT);

        ImageView playerIcon = createPlayerIcon(player);

        Label playerInfoText = new Label("Player ID: " + player.getId());
        playerInfoText.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        playerBox.getChildren().addAll(playerIcon, playerInfoText);
        return playerBox;
    }

    private TitledPane createGamesListSection(GlobalPlayer player) {
        VBox gameButtonsBox = new VBox(10);
        gameButtonsBox.setPadding(new Insets(10));

        player.getGamePlayerMap().keySet().forEach(game -> {
            Button gameButton = new Button("Game ID: " + game.getId());
            gameButton.setStyle("-fx-font-size: 14px;");
            gameButton.setPrefHeight(40);
            gameButton.setOnAction(event -> {
                analyticsData.setCurrentGame(game);
                gameLoaderApp.showGameScene();
            });
            gameButtonsBox.getChildren().add(gameButton);
        });

        TitledPane gamesPane = new TitledPane("Games", gameButtonsBox);
        gamesPane.setCollapsible(false);
        return gamesPane;
    }

    private Node createPlayerScoreChart(GlobalPlayer player) {
        GameScoreChart gameScoreChart = new GameScoreChart();
        return gameScoreChart.createPlayerTotalScoreChart(player);
    }

    private HBox createBackButtonSection() {
        HBox backButtonBox = new HBox();
        backButtonBox.setAlignment(Pos.BOTTOM_RIGHT);
        backButtonBox.setPadding(new Insets(10));

        Button backButton = new Button("Back to Start");
        backButton.setStyle("-fx-font-size: 14px;");
        backButton.setPrefWidth(150);
        backButton.setPrefHeight(40);
        backButton.setOnAction(event -> gameLoaderApp.showAnalyticsScene());

        backButtonBox.getChildren().add(backButton);
        return backButtonBox;
    }

    private ImageView createPlayerIcon(GlobalPlayer player) {
        String iconPath = "images/icons/" + player.getIconPath();
        Image iconImage;
        try {
            iconImage = new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(iconPath)));
        } catch (Exception e) {
            System.err.println("Error loading icon: " + iconPath);
            iconImage = new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("images/icons/default.png")));
        }

        ImageView imageView = new ImageView(iconImage);
        imageView.setFitWidth(50);
        imageView.setFitHeight(50);

        Circle clip = new Circle(25, 25, 25);
        clip.setStroke(Color.GRAY);
        clip.setStrokeWidth(2);
        clip.setFill(Color.WHITE);
        imageView.setClip(clip);

        return imageView;
    }

    public Scene getScene() {
        return scene;
    }
}