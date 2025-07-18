package scene.selection;

import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import main.Game;
import main.Player;
import scene.game_load.GameData;

import java.util.function.Consumer;

/**
 * Class PlayerSelectionController
 */
public class PlayerSelectionController {
    private GameData gameData;
    private VBox checkBoxContainer;
    private Consumer<String> roundUpdateCallback;
    private Runnable applyButtonUpdateCallback;


    /**
     * Constructs a PlayerSelectionController.
     *
     * @param gameData The game data
     * @param roundUpdateCallback A callback function to update the round information
     * @param applyButtonUpdateCallback A callback function to update the apply button state
     */
    public PlayerSelectionController(GameData gameData, Consumer<String> roundUpdateCallback, Runnable applyButtonUpdateCallback) {
        this.gameData = gameData;
        this.roundUpdateCallback = roundUpdateCallback;
        this.applyButtonUpdateCallback = applyButtonUpdateCallback;
        checkBoxContainer = createScrollableCheckBoxContainer("Players");
    }

    /**
     * Retrieves the main container pane.
     *
     * @return The VBox containing player selection UI elements
     */
    public VBox getPane() {
        return checkBoxContainer;
    }

    /**
     * Updates the list of checkboxes to reflect the currently elected players in the given game
     *
     * @param gameId The ID of the game
     */
    public void updatePlayerCheckBoxes(String gameId) {
        Game game = gameData.getAllGames().get(gameId);
        VBox container = (VBox) ((ScrollPane) checkBoxContainer.getChildren().get(1)).getContent();
        container.getChildren().clear();
        for (Player player : game.getElectedPlayers()) {
            String playerId = player.getId();
            CheckBox playerCheckBox = new CheckBox(playerId);
            playerCheckBox.setUserData(playerId);
            playerCheckBox.setOnAction(e -> handlePlayerSelection(playerId, playerCheckBox.isSelected()));
            container.getChildren().add(playerCheckBox);
        }
        roundUpdateCallback.accept(gameId);
    }

    /**
     * Handles the selection or deselection of a player.
     *
     * @param playerId   The ID of the selected player.
     * @param isSelected True if the player is selected, false otherwise.
     */
    private void handlePlayerSelection(String playerId, boolean isSelected) {
        if (isSelected) {
            gameData.getSelectedPlayers().add(playerId);
        } else {
            gameData.getSelectedPlayers().remove(playerId);
        }
        applyButtonUpdateCallback.run();
    }

    /**
     * Creates a scrollable container for displaying player checkboxes.
     *
     * @param labelText The label to display above the checkboxes.
     * @return A VBox containing the scrollable list of checkboxes.
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