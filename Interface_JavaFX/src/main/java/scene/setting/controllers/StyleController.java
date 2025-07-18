package scene.setting.controllers;

import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import scene.setting.SettingData;

/**
 * Class StyleController
 */
public class StyleController {

    private final String[][] availableStyles = {
            {"/style/board_view_style.css", "Original"},
            {"/style/board_view_style_white.css", "Light"},
            {"/style/board_view_style_dark.css", "Dark"}
    };

    private AnchorPane anchorPane;
    private SettingData settingData;

    /**
     * Creates a new StyleController instance
     *
     * @param anchorPane  The main AnchorPane of the scene
     * @param settingData The SettingData instance that holds application settings
     */
    public StyleController(AnchorPane anchorPane, SettingData settingData) {
        this.anchorPane = anchorPane;
        this.settingData = settingData;
    }

    /**
     * Creates a VBox containing style selection controls
     *
     * @return A VBox with style selection checkboxes
     */
    public VBox createStyleControls() {
        VBox styleBox = new VBox(15);
        styleBox.setLayoutY(130);
        styleBox.setAlignment(javafx.geometry.Pos.CENTER);
        styleBox.setId("checkContainer");
        Label styleLabel = new Label("Choose a style :");
        styleLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 12px;");
        styleBox.getChildren().add(styleLabel);

        CheckBox[] styleCheckboxes = new CheckBox[availableStyles.length];

        for (int i = 0; i < availableStyles.length; i++) {
            String stylePath = availableStyles[i][0];
            String styleName = availableStyles[i][1];

            CheckBox styleCheckbox = new CheckBox(styleName);
            styleCheckboxes[i] = styleCheckbox;

            if (stylePath.equals(settingData.getStyle())) {
                styleCheckbox.setSelected(true);
            }

            final int index = i;
            styleCheckbox.setOnAction(event -> {
                for (int j = 0; j < styleCheckboxes.length; j++) {
                    if (j != index) {
                        styleCheckboxes[j].setSelected(false);
                    }
                }

                if (styleCheckbox.isSelected()) {
                    settingData.setStyle(stylePath);
                    updateSceneStyle(stylePath);
                } else {
                    styleCheckbox.setSelected(true);
                }
            });

            styleBox.getChildren().add(styleCheckbox);
        }

        return styleBox;
    }

    /**
     * Updates the scene with the selected style
     *
     * @param stylePath The path to the new CSS style file
     */
    public void updateSceneStyle(String stylePath) {
        try {
            anchorPane.getStylesheets().clear();
            String cssUrl = getClass().getResource(stylePath).toExternalForm();
            anchorPane.getStylesheets().add(cssUrl);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    /**
     * Applies the currently selected style to the scene
     */
    public void applyCurrentStyle() {
        updateSceneStyle(settingData.getStyle());
    }
}