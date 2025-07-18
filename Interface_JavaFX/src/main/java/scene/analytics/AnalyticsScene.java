package scene.analytics;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import main.Game;
import main.GameLoaderApp;
import main.GameParser;
import main.GlobalPlayer;
import scene.setting.SettingData;
import java.util.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Insets;
import javafx.geometry.Pos;


public class AnalyticsScene {

    private final GameLoaderApp gameLoaderApp;
    private final List<File> fileList = new ArrayList<>();
    private Scene scene;
    private final AnalyticsData data;
    private SettingData settingData;

    private String savedGameId = "";
    private String savedPlayerId = "";
    private String savedFileAreaText = "";

    private TextArea fileTextArea;
    private TextField gameIdInput;
    private TextField playerIdInput;
    private VBox gamesContainer;
    private VBox playersContainer;
    private Label gamesLabel;
    private Label playersLabel;
    private Button startGameButton;
    private Button startPlayerButton;

    public AnalyticsScene(GameLoaderApp application, AnalyticsData analyticsData, SettingData settingData) {
        this.gameLoaderApp = application;
        this.scene = create(application.getPrimaryStage());
        this.data = analyticsData;
        this.settingData = settingData;
        scene.getStylesheets().add(getClass().getResource(settingData.getStyle()).toExternalForm());
    }

    public Scene getScene() {
        return scene;
    }

    private Scene create(Stage primaryStage) {
        BorderPane layout = new BorderPane();
        VBox centerContent = createCenterContent(primaryStage);
        layout.setCenter(centerContent);
        HBox bottomButtons = createBottomButtons(primaryStage);
        layout.setBottom(bottomButtons);
        BorderPane.setMargin(centerContent, new Insets(10));
        BorderPane.setMargin(bottomButtons, new Insets(10));
        Scene scene = new Scene(layout,800,800);
        return scene;
    }

    private VBox createCenterContent(Stage primaryStage) {
        fileTextArea = createFileTextArea();
        gameIdInput = createTextField("Enter Game ID", savedGameId, false);
        playerIdInput = createTextField("Enter Player ID", savedPlayerId, false);
        Button uploadButton = createUploadButton(primaryStage);
        Button processButton = createProcessButton();
        gamesLabel = createLabel("Games:", false);
        playersLabel = createLabel("Players:", false);
        gamesContainer = createContainer(false);
        playersContainer = createContainer(false);
        startGameButton = createStartGameButton();
        startPlayerButton = createStartPlayerButton();
        VBox centerContent = new VBox(10, fileTextArea, uploadButton, processButton, gameIdInput, playerIdInput,
                startGameButton, startPlayerButton, gamesLabel, gamesContainer, playersLabel, playersContainer);
        centerContent.setPadding(new Insets(10));

        return centerContent;
    }

    private TextField createTextField(String placeholder, String initialValue, boolean isVisible) {
        TextField textField = new TextField();
        textField.setPromptText(placeholder);
        textField.setText(initialValue);
        textField.setVisible(isVisible);
        return textField;
    }

    private TextArea createFileTextArea() {
        TextArea textArea = new TextArea();
        textArea.setEditable(false);
        textArea.setText(savedFileAreaText);
        textArea.setPrefHeight(150);
        return textArea;
    }

    private Label createLabel(String text, boolean isVisible) {
        Label label = new Label(text);
        label.setVisible(isVisible);
        return label;
    }

    private VBox createContainer(boolean isVisible) {
        VBox container = new VBox(10);
        container.setVisible(isVisible);
        container.setAlignment(Pos.TOP_LEFT);
        return container;
    }

    private Button createUploadButton(Stage primaryStage) {
        Button uploadButton = new Button("Upload File");
        uploadButton.setOnAction(event -> handleFileUpload(primaryStage));
        return uploadButton;
    }

    private Button createProcessButton() {
        Button processButton = new Button("Process Files");
        processButton.setOnAction(event -> handleProcessFiles());
        return processButton;
    }

    private Button createStartGameButton() {
        Button button = new Button("Get Game Statistics");
        button.setVisible(false);
        button.setOnAction(event -> handleStartGame());
        return button;
    }

    private Button createStartPlayerButton() {
        Button button = new Button("Get Player Statistics");
        button.setVisible(false);
        button.setOnAction(event -> handleStartPlayer());
        return button;
    }

    private HBox createBottomButtons(Stage primaryStage) {
        Button backButton = new Button("Back to Start");
        backButton.setOnAction(event -> gameLoaderApp.showStarterScene());
        Button reloadButton = new Button("Reload Page");
        reloadButton.setOnAction(event -> handleReload(primaryStage));
        HBox bottomButtons = new HBox(10, backButton, reloadButton);
        bottomButtons.setAlignment(Pos.CENTER_RIGHT);
        return bottomButtons;
    }

    private void handleFileUpload(Stage primaryStage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose a File");
        File selectedFile = fileChooser.showOpenDialog(primaryStage);
        if (selectedFile != null) {
            try {
                GameParser parser = new GameParser(selectedFile.getAbsolutePath());
                this.data.addAllGames(parser.getGames());
                this.data.setPlayersWithIds(parser.getAllPlayers());
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            fileList.add(selectedFile);
            savedFileAreaText += "File added: " + selectedFile.getName() + "\n";
            fileTextArea.appendText("File added: " + selectedFile.getName() + "\n");
        }
    }

    private void handleProcessFiles() {
        if (!fileList.isEmpty()) {
            fileTextArea.appendText("Files processed. Ready to enter Game ID and Player ID.\n");
            updateGamesAndPlayers();
        } else {
            fileTextArea.appendText("No files to process. Please upload a file first.\n");
        }
    }

    private void updateGamesAndPlayers() {
        gamesContainer.getChildren().clear();
        playersContainer.getChildren().clear();

        data.getAllGames().forEach((id, game) -> {
            Button gameButton = new Button("Game ID: " + id);
            gameButton.setOnAction(event -> {
                data.setCurrentGame(game);
                gameLoaderApp.showGameScene();
            });
            gamesContainer.getChildren().add(gameButton);
        });

        data.getPlayersWithIds().forEach((id, player) -> {
            Button playerButton = new Button();
            playerButton.setId("player");
            playerButton.setText("Player: " + id);
            ImageView playerIcon = null;
            if (player.getIconPath() != null) {
                try {
                    Image icon = new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("images/icons/" + player.getIconPath())));
                    playerIcon = new ImageView(icon);
                    playerIcon.setFitWidth(50);
                    playerIcon.setFitHeight(50);
                    Circle clip = new Circle(playerIcon.getFitWidth() / 2, playerIcon.getFitHeight() / 2, Math.min(playerIcon.getFitWidth(), playerIcon.getFitHeight()) / 2);
                    playerIcon.setClip(clip);
                    StackPane iconContainer = new StackPane();
                    iconContainer.setStyle("-fx-background-color: white; -fx-border-color: gray; -fx-border-width: 2px; -fx-border-radius: 50%; -fx-background-radius: 50%;");
                    iconContainer.setPrefSize(playerIcon.getFitWidth(), playerIcon.getFitHeight());
                    iconContainer.getChildren().add(playerIcon);
                    playerButton.setGraphic(iconContainer);
                } catch (Exception e) {
                    System.err.println("Icon " + player.getIconPath() + " not found!");
                }
            }
            if (playerIcon != null) {
                playerButton.setGraphic(playerIcon);
            }
            playerButton.setStyle("-fx-font-size: 16px; -fx-pref-width: 250px; -fx-pref-height: 70px;");
            playerButton.setOnAction(event -> {
                data.setCurrentPlayer(player);
                gameLoaderApp.showPlayerScene();
            });
            playersContainer.getChildren().add(playerButton);
        });

        gamesLabel.setVisible(true);
        gamesContainer.setVisible(true);
        playersLabel.setVisible(true);
        playersContainer.setVisible(true);
        gameIdInput.setVisible(true);
        playerIdInput.setVisible(true);
        startGameButton.setVisible(true);
        startPlayerButton.setVisible(true);
    }

    private void handleStartGame() {
        String inputGameId = gameIdInput.getText();
        if (inputGameId.isEmpty()) {
            fileTextArea.appendText("Please enter Game ID\n");
            return;
        }

        if (data.getAllGames().containsKey(inputGameId)) {
            Game selectedGame = data.getAllGames().get(inputGameId);
            data.setCurrentGame(selectedGame);
            savedGameId = inputGameId;
            gameLoaderApp.showGameScene();
        } else {
            fileTextArea.appendText("Game ID not found.\n");
        }
    }

    private void handleStartPlayer() {
        String inputPlayerId = playerIdInput.getText();
        if (inputPlayerId.isEmpty()) {
            fileTextArea.appendText("Please enter Player ID\n");
            return;
        }

        if (data.getPlayersWithIds().containsKey(inputPlayerId)) {
            GlobalPlayer selectedPlayer = data.getPlayersWithIds().get(inputPlayerId);
            data.setCurrentPlayer(selectedPlayer);
            savedPlayerId = inputPlayerId;
            gameLoaderApp.showPlayerScene();
        } else {
            fileTextArea.appendText("Player ID not found.\n");
        }
    }

    private void handleReload(Stage primaryStage) {
        this.data.resetData();
        this.fileList.clear();
        this.savedGameId = "";
        this.savedPlayerId = "";
        this.savedFileAreaText = "";
        this.scene = create(primaryStage);
        gameLoaderApp.showAnalyticsScene();
    }
}