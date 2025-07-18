package scene.selection;

import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import main.Game;
import scene.game_load.GameData;

/**
 * Class RoundSelectionController
 */
public class RoundSelectionController {

    private GameData gameData;
    private VBox checkBoxContainer;
    private Runnable applyButtonUpdateCallback;

    /**
     * Creates a new RoundSelectionController instance
     *
     * @param gameData The GameData instance
     * @param applyButtonUpdateCallback A callback function to update the apply button state
     */
    public RoundSelectionController(GameData gameData, Runnable applyButtonUpdateCallback) {
        this.gameData = gameData;
        this.applyButtonUpdateCallback = applyButtonUpdateCallback;
        checkBoxContainer = createScrollableCheckBoxContainer("Rounds");
    }

    /**
     * Gets the main container for round selection checkboxes
     *
     * @return A VBox containing the checkboxes for round selection
     */
    public VBox getPane() {
        return checkBoxContainer;
    }

    /**
     * Updates the checkboxes based on the selected game, reflecting the number of rounds available
     *
     * @param gameId The ID of the selected game
     */
    public void updateRoundCheckBoxes(String gameId) {
        Game game = gameData.getAllGames().get(gameId);
        int numRounds = game.getRounds().size();
        VBox container = (VBox) ((ScrollPane) checkBoxContainer.getChildren().get(1)).getContent();
        container.getChildren().clear();
        for (int i = 0; i < numRounds; i++) {
            final int roundIndex = i;
            CheckBox roundCheckBox = new CheckBox("Round " + (i + 1));
            roundCheckBox.setUserData(roundIndex);
            roundCheckBox.setOnAction(e -> handleRoundSelection(roundIndex, roundCheckBox.isSelected()));
            container.getChildren().add(roundCheckBox);
        }
    }

    /**
     * Handles the selection of a round, adding or removing it from the selected rounds list
     *
     * @param roundIndex The index of the selected round
     * @param isSelected True if the round is selected, false otherwise
     */
    private void handleRoundSelection(int roundIndex, boolean isSelected) {
        if (isSelected) {
            gameData.getSelectedRounds().add(roundIndex);
        } else {
            gameData.getSelectedRounds().remove(roundIndex);
        }
        applyButtonUpdateCallback.run();
    }

    /**
     * Creates a scrollable container for round selection checkboxes
     *
     * @param labelText The label text to display above the checkboxes
     * @return A VBox containing a label and a scrollable list of checkboxes
     */
    private VBox createScrollableCheckBoxContainer(String labelText) {
        Label label = new Label(labelText + " (select one or more):");
        VBox checkBoxContainer = new VBox(5);
        ScrollPane scrollPane = new ScrollPane(checkBoxContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefHeight(120);
        return new VBox(5, label, scrollPane);
    }
}