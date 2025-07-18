package scene.analytics;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.Chart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import main.*;
import scene.setting.SettingData;


public class GameScene {
    private final AnalyticsData data;
    private final Scene scene;
    private final GameLoaderApp app;
    private SettingData settingData;

    public GameScene(AnalyticsData data, GameLoaderApp app,SettingData settingData) {
        this.data = data;
        this.scene = buildGameScene();
        this.app = app;
        this.settingData = settingData;
        scene.getStylesheets().add(getClass().getResource(settingData.getStyle()).toExternalForm());
    }

    public Scene getScene() {
        return scene;
    }

    /**
     * Builds the main game scene layout.
     */
    private Scene buildGameScene() {
        BorderPane layout = new BorderPane();

        VBox topSection = createGameInfoSection();
        layout.setTop(topSection);

        HBox centerSection = createCenterSection();
        layout.setCenter(centerSection);

        VBox bottomSection = createBottomSection();
        layout.setBottom(bottomSection);

        BorderPane.setMargin(topSection, new Insets(15));
        BorderPane.setMargin(centerSection, new Insets(15));
        BorderPane.setMargin(bottomSection, new Insets(15));

        return new Scene(layout, 800, 800);
    }

    /**
     * Creates the top section displaying game information.
     */
    private VBox createGameInfoSection() {
        Game game = data.getCurrentGame();

        Label gameIdLabel = new Label("Game ID: " + game.getId());
        gameIdLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: black;");

        VBox topSection = new VBox(10, gameIdLabel);
        topSection.setAlignment(Pos.CENTER);
        return topSection;
    }

    /**
     * Creates the center section with players and a chart.
     */
    private HBox createCenterSection() {
        VBox playersSection = createPlayersSection();

        Chart scoreChart = new PlayersScoreChart().createPlayerTotalScoreChart(data.getCurrentGame());

        HBox centerSection = new HBox(20, playersSection, scoreChart);
        centerSection.setAlignment(Pos.CENTER);
        return centerSection;
    }

    /**
     * Creates the players section with player buttons.
     */
    private VBox createPlayersSection() {
        VBox playersSection = new VBox(15);
        playersSection.setPadding(new Insets(10));
        playersSection.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-border-radius: 5;");
        playersSection.getChildren().add(new Label("Players"));

        for (Player player : data.getCurrentGame().getElectedPlayers()) {
            Image playerIcon = new Image("file:resources/player-icon.png");
            ImageView playerIconView = new ImageView(playerIcon);
            playerIconView.setFitWidth(20);
            playerIconView.setFitHeight(20);

            Button playerButton = new Button("Player: " + player.getId(), playerIconView);
            playerButton.setId(player.getId());

            playerButton.setPrefWidth(200);
            playerButton.setPrefHeight(40);

            playerButton.setOnAction(event -> {
                String currentPlayerId = playerButton.getId();
                GlobalPlayer currentPlayer = data.getPlayersWithIds().get(currentPlayerId);
                if (currentPlayer != null) {
                    data.setCurrentPlayer(currentPlayer);
                    app.showPlayerScene();
                }
            });

            playersSection.getChildren().add(playerButton);
        }

        return playersSection;
    }

    /**
     * Creates the bottom section with rounds and a back button.
     */
    private VBox createBottomSection() {
        VBox roundsSection = new VBox(10);
        roundsSection.setPadding(new Insets(10));
        roundsSection.setStyle("-fx-border-color: #000000; -fx-border-width: 2; -fx-border-radius: 5;");
        roundsSection.getChildren().add(new Label("Rounds"));

        int roundNumber = 1;
        for (Round round : data.getCurrentGame().getRounds()) {
            Button roundButton = new Button("Round " + roundNumber);
            int currentRoundNumber = roundNumber;
            roundButton.setOnAction(event -> {
                data.setCurrentRound(round);
                RoundScene roundScene = new RoundScene(data, app, currentRoundNumber,settingData);
                app.getPrimaryStage().setScene(roundScene.getScene());
            });
            roundsSection.getChildren().add(roundButton);
            roundNumber++;
        }

        HBox backButtonBox = new HBox();
        backButtonBox.setAlignment(Pos.BOTTOM_RIGHT);
        backButtonBox.setPadding(new Insets(15));
        Button backButton = new Button("Back");
        backButton.setOnAction(event -> app.showAnalyticsScene());
        backButtonBox.getChildren().add(backButton);

        VBox bottomSection = new VBox(10, roundsSection, backButtonBox);
        return bottomSection;
    }
}