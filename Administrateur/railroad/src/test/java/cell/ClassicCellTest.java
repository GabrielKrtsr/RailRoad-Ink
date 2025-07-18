package cell;

import board.Board;
import face.AbstractFace;
import face.HighwayFace;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.Path;
import util.util_for_nodes.Node;
import util.util_for_nodes.Side;

import static org.junit.jupiter.api.Assertions.*;

public class ClassicCellTest extends CellTest {

    @BeforeEach
    protected void setUp() {
        cell = new ClassicCell();
    }

    @Override
    protected int expectedPoints() {
        return 1;
    }

    @Override
    protected boolean expectedIsExit() {
        return false;
    }

    @Override
    protected Cell createCellWithSameProperties() {
        return new ClassicCell();
    }

    @Override
    protected Cell createDifferentCell() {
        return new BorderCell(Side.TOP, Path.ROAD, false);
    }


    @Override
    protected String expectedSting() {
        return "Classic";
    }
}
