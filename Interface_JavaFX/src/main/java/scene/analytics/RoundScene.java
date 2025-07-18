package scene.analytics;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import main.GameLoaderApp;
import main.Round;

import javafx.scene.control.ScrollPane;


import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.chart.BarChart;
import javafx.stage.Stage;
import scene.setting.SettingData;

public class RoundScene {
    private final AnalyticsData data;
    private final GameLoaderApp app;
    private final Scene scene;
    private SettingData settingData;

    public RoundScene(AnalyticsData data, GameLoaderApp app, int number, SettingData settingData) {
        this.data = data;
        this.app = app;
        this.scene = buildRoundScene(number);
        this.settingData = settingData;
        scene.getStylesheets().add(getClass().getResource(settingData.getStyle()).toExternalForm());
    }

    public Scene getScene() {
        return scene;
    }

    private Scene buildRoundScene(int number) {
        BorderPane layout = new BorderPane();
        layout.setPadding(new Insets(20));

        Round currentRound = data.getCurrentRound();

        Label roundInfoLabel = createRoundInfoLabel(number);
        layout.setTop(roundInfoLabel);

        VBox chartsContainer = new VBox(10);
        chartsContainer.setPadding(new Insets(10));
        ScrollPane chartsScrollPane = new ScrollPane(chartsContainer);
        chartsScrollPane.setFitToWidth(true);
        chartsScrollPane.setPadding(new Insets(10));
        layout.setCenter(chartsScrollPane);

        VBox checkBoxSection = createCheckBoxSection(currentRound, chartsContainer);
        layout.setLeft(checkBoxSection);

        Button backButton = createBackButton();
        HBox backButtonBox = new HBox(backButton);
        backButtonBox.setAlignment(Pos.BOTTOM_RIGHT);
        backButtonBox.setPadding(new Insets(10));
        layout.setBottom(backButtonBox);

        return new Scene(layout, 800, 600);
    }

    /**
     * Creates a label with round information.
     */
    private Label createRoundInfoLabel(int number) {
        Label roundInfoLabel = new Label("Round: " + number);
        roundInfoLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        BorderPane.setMargin(roundInfoLabel, new Insets(10));
        return roundInfoLabel;
    }

    /**
     * Creates a section with checkboxes to manage charts.
     */
    private VBox createCheckBoxSection(Round currentRound, VBox chartsContainer) {
        VBox checkBoxContainer = new VBox(10);
        checkBoxContainer.setPadding(new Insets(10));
        checkBoxContainer.setStyle("-fx-border-color: gray; -fx-border-width: 1; -fx-border-radius: 5;");

        RoundChart roundChart = new RoundChart(currentRound);
        CheckBox[] checkBoxes = new CheckBox[]{
                createChartCheckBox("Show Rails Chart", roundChart.createPlayerRailsChart(), chartsContainer),
                createChartCheckBox("Show Roads Chart", roundChart.createPlayerRoadsChart(), chartsContainer),
                createChartCheckBox("Show Networks Chart", roundChart.createPlayerNetworksChart(), chartsContainer),
                createChartCheckBox("Show False Routes Chart", roundChart.createPlayerFalseRoutesChart(), chartsContainer),
                createChartCheckBox("Show Center Cells Chart", roundChart.createPlayerCenterCellsChart(), chartsContainer),
                createChartCheckBox("Show Total Score Chart", roundChart.createPlayerTotalScoreChart(), chartsContainer),
        };

        checkBoxContainer.getChildren().addAll(checkBoxes);

        return checkBoxContainer;
    }

    /**
     * Creates a checkbox to manage a specific chart.
     */
    private CheckBox createChartCheckBox(String labelText, BarChart<String, Number> chart, VBox chartsContainer) {
        CheckBox checkBox = new CheckBox(labelText);

        System.out.println("Creating checkbox for chart: " + labelText);

        checkBox.setSelected(false);
        checkBox.setOnAction(event -> {
            if (checkBox.isSelected()) {
                if (!chartsContainer.getChildren().contains(chart)) {
                    chartsContainer.getChildren().add(chart);
                    System.out.println("Chart added: " + labelText);
                }
            } else {
                chartsContainer.getChildren().remove(chart);
                System.out.println("Chart removed: " + labelText);
            }
        });

        return checkBox;
    }

    /**
     * Creates a button to go back to the main game view.
     */
    private Button createBackButton() {
        Button backButton = new Button("Back to Game");
        backButton.setPrefWidth(150);
        backButton.setPrefHeight(40);
        backButton.setStyle("-fx-font-size: 14px;");
        backButton.setOnAction(event -> app.showGameScene());
        return backButton;
    }
}
