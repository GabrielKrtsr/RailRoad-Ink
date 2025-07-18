package scene.detection;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Region;
import main.Player;
import main.Round;

import java.util.Map;

public class ScoreRegion {

    private final Label networksScoreLabel;
    private final Label railsScoreLabel;
    private final Label roadsScoreLabel;
    private final Label falseRoutesScoreLabel;
    private final Label centerCellsScoreLabel;

    /**
     * Constructs a new ScoreRegion and initializes score labels based on the given Round and Player.
     *
     * @param round  The Round object containing scores.
     * @param player The Player whose scores will be displayed.
     */
    public ScoreRegion(Round round, Player player) {
        this.networksScoreLabel = new Label("Networks Score: " + getScore(round.getNetworksScore(), player));
        this.railsScoreLabel = new Label("Rails Score: " + getScore(round.getRailsScore(), player));
        this.roadsScoreLabel = new Label("Roads Score: " + getScore(round.getRoadsScore(), player));
        this.falseRoutesScoreLabel = new Label("False Routes Score: " + getScore(round.getFalseRoutesScore(), player));
        this.centerCellsScoreLabel = new Label("Center Cells Score: " + getScore(round.getCenterCellsScore(), player));
    }

    /**
     * Creates and returns a Region containing all score labels.
     *
     * @return A Region containing all score labels.
     */
    public Region getRegion() {
        VBox box = new VBox(10);
        box.getChildren().addAll(
                networksScoreLabel,
                railsScoreLabel,
                roadsScoreLabel,
                falseRoutesScoreLabel,
                centerCellsScoreLabel
        );

        box.setId("scoreContainer");
        box.setSpacing(10);
        box.setAlignment(Pos.CENTER_LEFT);
        return box;
    }

    /**
     * Returns the label showing the Networks score.
     *
     * @return The label for Networks score.
     */
    public Label getNetworksScoreLabel() {
        return networksScoreLabel;
    }

    /**
     * Returns the label showing the Rails score.
     *
     * @return The label for Rails score.
     */
    public Label getRailsScoreLabel() {
        return railsScoreLabel;
    }

    /**
     * Returns the label showing the Roads score.
     *
     * @return The label for Roads score.
     */
    public Label getRoadsScoreLabel() {
        return roadsScoreLabel;
    }

    /**
     * Returns the label showing the False Routes score.
     *
     * @return The label for False Routes score.
     */
    public Label getFalseRoutesScoreLabel() {
        return falseRoutesScoreLabel;
    }

    /**
     * Returns the label showing the Center Cells score.
     *
     * @return The label for Center Cells score.
     */
    public Label getCenterCellsScoreLabel() {
        return centerCellsScoreLabel;
    }

    /**
     * Extracts and returns the score for the specified player from the given score map.
     *
     * @param scoreMap The map containing scores for each player.
     * @param player   The player whose score is to be retrieved.
     * @return The score for the specified player, or 0 if no score was found.
     */
    private int getScore(Map<Player, Integer> scoreMap, Player player) {
        return scoreMap.getOrDefault(player, 0);
    }

    /**
     * Updates the score labels with the latest scores from the given Round object.
     *
     * @param round  The Round object containing updated scores.
     * @param player The Player for whom scores need to be updated.
     */
    public void updateScoreRegion(Round round, Player player) {
        networksScoreLabel.setText("Networks Score: " + getScore(round.getNetworksScore(), player));
        railsScoreLabel.setText("Rails Score: " + getScore(round.getRailsScore(), player));
        roadsScoreLabel.setText("Roads Score: " + getScore(round.getRoadsScore(), player));
        falseRoutesScoreLabel.setText("False Routes Score: " + getScore(round.getFalseRoutesScore(), player));
        centerCellsScoreLabel.setText("Center Cells Score: " + getScore(round.getCenterCellsScore(), player));
    }
}