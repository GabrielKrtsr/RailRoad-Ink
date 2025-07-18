package scene.analytics;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import main.Player;
import main.Round;

import java.util.Map;

import javafx.scene.paint.Color;

import java.util.HashMap;


import java.util.*;
import java.util.stream.Collectors;

public class RoundChart {

    private Round round;

    public RoundChart(Round round) {
        this.round = round;
    }

    public BarChart<String, Number> createPlayerRailsChart() {
        return createBarChart("Longest Rail Score Chart", "Player ID", "Score", round.getRailsScore());
    }

    public BarChart<String, Number> createPlayerRoadsChart() {
        return createBarChart("Longest Road Score Chart", "Player ID", "Score", round.getRoadsScore());
    }

    public BarChart<String, Number> createPlayerNetworksChart() {
        return createBarChart("Network Score Chart", "Player ID", "Score", round.getNetworksScore());
    }

    public BarChart<String, Number> createPlayerFalseRoutesChart() {
        return createBarChart("False Routes Score Chart", "Player ID", "Score", round.getFalseRoutesScore());
    }

    public BarChart<String, Number> createPlayerCenterCellsChart() {
        return createBarChart("Center Cells Score Chart", "Player ID", "Score", round.getCenterCellsScore());
    }

    public BarChart<String, Number> createPlayerTotalScoreChart() {
        return createBarChart("Total Score Chart", "Player ID", "Score", round.getTotalScore());
    }

    private BarChart<String, Number> createBarChart(String title, String xAxisLabel, String yAxisLabel, Map<Player, Integer> data) {
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel(xAxisLabel);

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel(yAxisLabel);

        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle(title);

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Score");

        Map<Player, Integer> sortedData = data.entrySet()
                .stream()
                .sorted((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));

        Map<String, Color> playerColors = new HashMap<>();

        for (Map.Entry<Player, Integer> entry : sortedData.entrySet()) {
            Player player = entry.getKey();
            Integer score = entry.getValue();

            XYChart.Data<String, Number> dataPoint = new XYChart.Data<>(player.getId(), score);
            series.getData().add(dataPoint);

            playerColors.putIfAbsent(player.getId(), generateRandomColor());
            Color playerColor = playerColors.get(player.getId());

            dataPoint.nodeProperty().addListener((observable, oldNode, newNode) -> {
                if (newNode != null) {
                    newNode.setStyle("-fx-bar-fill: " + toRgbString(playerColor) + ";");
                }
            });
        }

        barChart.getData().add(series);
        return barChart;
    }

    private Color generateRandomColor() {
        return Color.color(Math.random(), Math.random(), Math.random());
    }

    private String toRgbString(Color color) {
        return String.format("rgb(%d,%d,%d)",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));
    }
}
