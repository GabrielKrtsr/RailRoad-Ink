package scene.analytics;

import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.paint.Color;
import main.Game;
import main.Player;
import main.Round;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PlayersScoreChart {

    /**
     * Creates a bar chart displaying the total scores of players from the last game round.
     *
     * @param game Game object containing rounds and player scores.
     * @return BarChart displaying player scores.
     */
    public BarChart<String, Number> createPlayerTotalScoreChart(Game game) {
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Player");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Total Score");

        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Players Total Score (Last Round)");

        List<Round> rounds = game.getRounds();
        if (rounds == null || rounds.isEmpty()) {
            throw new IllegalArgumentException("The game contains no rounds.");
        }

        Round lastRound = rounds.get(rounds.size() - 1);
        Map<Player, Integer> totalScoreMap = lastRound.getTotalScore();

        List<Map.Entry<Player, Integer>> sortedEntries = totalScoreMap.entrySet().stream()
                .sorted((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()))
                .collect(Collectors.toList());

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Total Scores");

        for (Map.Entry<Player, Integer> entry : sortedEntries) {
            String playerName = entry.getKey().getId();
            Integer score = entry.getValue();
            XYChart.Data<String, Number> chartData = new XYChart.Data<>(playerName, score);
            series.getData().add(chartData);

            chartData.nodeProperty().addListener((observable, oldNode, newNode) -> {
                if (newNode != null) {
                    Color color = Color.color(Math.random(), Math.random(), Math.random());
                    newNode.setStyle("-fx-bar-fill: " + toRgbString(color) + ";");
                }
            });
        }

        barChart.getData().add(series);
        return barChart;
    }

    private String toRgbString(Color color) {
        return String.format("rgb(%d,%d,%d)",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));
    }
}
