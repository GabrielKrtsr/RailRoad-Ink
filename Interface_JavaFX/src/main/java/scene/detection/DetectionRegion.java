package scene.detection;

import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

/**
 * Class DetectionRegion
 */
public class DetectionRegion {

    private final CheckBox falseRouteCheckBox;
    private final CheckBox longestRoadCheckBox;
    private final CheckBox longestRailCheckBox;
    private final CheckBox centerCellsCheckBox;
    private final CheckBox networksCheckBox;

    /**
     * Constructs a new DetectionRegion with all checkboxes initialized
     */
    public DetectionRegion(){
        this.falseRouteCheckBox = new CheckBox("False Route");
        this.longestRoadCheckBox = new CheckBox("Longest Road");
        this.longestRailCheckBox = new CheckBox("Longest Rail");
        this.centerCellsCheckBox = new CheckBox("Center Cells");
        this.networksCheckBox = new CheckBox("Networks");
    }

    /**
     * Creates a Region containing all checkboxes
     *
     * @return A Region containing all checkboxes
     */
    public Region getRegion(){
        HBox box = new HBox(10);
        box.getChildren().addAll(falseRouteCheckBox,
                longestRoadCheckBox,
                longestRailCheckBox,
                centerCellsCheckBox,
                networksCheckBox);

        box.setId("checkContainer");
        box.setSpacing(20);
        box.setAlignment(Pos.CENTER);
        return box;
    }

    /**
     * Gets the False Route checkbox.
     *
     * @return The checkbox for False Route detection
     */
    public CheckBox getFalseRouteCheckBox() {
        return falseRouteCheckBox;
    }

    /**
     * Gets the Longest Road checkbox.
     *
     * @return The checkbox for Longest Road detection
     */
    public CheckBox getLongestRoadCheckBox() {
        return longestRoadCheckBox;
    }

    /**
     * Gets the Longest Rail checkbox.
     *
     * @return The checkbox for Longest Rail detection
     */
    public CheckBox getLongestRailCheckBox() {
        return longestRailCheckBox;
    }

    /**
     * Gets the Center Cells checkbox.
     *
     * @return The checkbox for Center Cells detection
     */
    public CheckBox getCenterCellsCheckBox() {
        return centerCellsCheckBox;
    }

    /**
     * Gets the Networks checkbox.
     *
     * @return The checkbox for Networks detection
     */
    public CheckBox getNetworksCheckBox() {
        return networksCheckBox;
    }

    /**
     * Resets all checkboxes to their unselected state.
     */
    public void resetCheckBox() {
        falseRouteCheckBox.setSelected(false);
        longestRoadCheckBox.setSelected(false);
        longestRailCheckBox.setSelected(false);
        centerCellsCheckBox.setSelected(false);
        networksCheckBox.setSelected(false);
    }
}

