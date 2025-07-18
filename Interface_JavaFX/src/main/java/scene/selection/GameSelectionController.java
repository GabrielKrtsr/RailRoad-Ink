package scene.selection;

import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import scene.game_load.GameData;

import java.util.function.Consumer;


/**
 * Class GameSelectionController
 */
public class GameSelectionController {

    private GameData gameData;
    private VBox checkBoxContainer;
    private Consumer<String> playerUpdateCallback;
    private Runnable applyButtonUpdateCallback;


    /**
     * Constructor for the game selection controller.
     *
     * @param gameData The game data
     * @param playerUpdateCallback Callback to call when players are updated
     * @param applyButtonUpdateCallback Callback to call to update the apply button state
     */
    public GameSelectionController(GameData gameData, Consumer<String> playerUpdateCallback, Runnable applyButtonUpdateCallback) {
        this.gameData = gameData;
        this.playerUpdateCallback = playerUpdateCallback;
        this.applyButtonUpdateCallback = applyButtonUpdateCallback;
        checkBoxContainer = createScrollableCheckBoxContainer("Games");
    }

    /**
     * Returns the main container of the game selection interface.
     *
     * @return The VBox container holding all interface elements
     */
    public VBox getPane() {
        return checkBoxContainer;
    }

    /**
     * Populates the container with checkboxes corresponding to available games
     */
    public void populateCheckBoxes() {
        VBox container = (VBox) ((ScrollPane) checkBoxContainer.getChildren().get(1)).getContent();
        container.getChildren().clear();
        for (String gameId : gameData.getAllGames().keySet()) {
            CheckBox gameCheckBox = new CheckBox("Game: " + gameId);
            gameCheckBox.setUserData(gameId);
            gameCheckBox.setOnAction(e -> handleGameSelection(gameId, gameCheckBox.isSelected()));
            container.getChildren().add(gameCheckBox);
        }
    }

    /**
     * Handles the selection or deselection of a game
     *
     * @param gameId The ID of the selected or deselected game
     * @param isSelected true if the game is selected, false otherwise
     */
    private void handleGameSelection(String gameId, boolean isSelected) {
        if (isSelected) {
            gameData.getSelectedGames().add(gameId);
            playerUpdateCallback.accept(gameId);
        } else {
            gameData.getSelectedGames().remove(gameId);
            clearAndResetSelections();
        }
        applyButtonUpdateCallback.run();
    }

    /**
     * Clears and resets all player and round selections.
     * This method is called when a game is deselected.
     */
    private void clearAndResetSelections() {
        gameData.getSelectedPlayers().clear();
        gameData.getSelectedRounds().clear();
    }

    /**
     * Creates a scrollable container for checkboxes with a label
     *
     * @param labelText The text of the label to display above the container
     * @return A VBox container with the label and scrollable panel
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