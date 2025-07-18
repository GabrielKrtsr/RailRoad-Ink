package scene.analytics;

import javafx.scene.chart.*;
import javafx.scene.paint.Color;
import main.Game;
import main.GlobalPlayer;
import main.Player;
import main.Round;

import java.util.*;
import java.util.stream.Collectors;

import javafx.scene.chart.*;
import javafx.scene.paint.Color;

import java.util.*;
import java.util.stream.Collectors;

public class GameScoreChart {

    /**
     * Method to create a bar chart of the player's total score if the GlobalPlayer ID
     * matches the player ID from the `totalScoreMap` of the last round of each game.
     *
     * @param globalPlayer GlobalPlayer object that aggregates data about games and players.
     * @return BarChart object displaying the data.
     */
    public BarChart<String, Number> createPlayerTotalScoreChart(GlobalPlayer globalPlayer) {
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Player ID");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Total Score");

        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Player's Total Score Across All Games (Last Rounds)");

        Set<Game> games = globalPlayer.getGamePlayerMap().keySet();

        Map<String, Integer> matchedScoresById = new HashMap<>();

        for (Game game : games) {
            List<Round> rounds = game.getRounds();

            if (rounds == null || rounds.isEmpty()) {
                continue;
            }

            Round lastRound = rounds.get(rounds.size() - 1);
            Map<Player, Integer> totalScoreMap = lastRound.getTotalScore();

            totalScoreMap.forEach((player, score) -> {
                if (player.getId().equals(globalPlayer.getId())) {
                    matchedScoresById.put(game.getId(), score);
                }
            });
        }

        List<Map.Entry<String, Integer>> sortedEntries = matchedScoresById.entrySet().stream()
                .sorted((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()))
                .collect(Collectors.toList());

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Total Scores");

        Map<String, Color> playerColors = new HashMap<>();

        for (Map.Entry<String, Integer> entry : sortedEntries) {
            String playerId = entry.getKey();
            Integer score = entry.getValue();
            XYChart.Data<String, Number> chartData = new XYChart.Data<>(playerId, score);
            series.getData().add(chartData);

            playerColors.putIfAbsent(playerId, generateRandomColor());
            Color playerColor = playerColors.get(playerId);

            chartData.nodeProperty().addListener((observable, oldNode, newNode) -> {
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
