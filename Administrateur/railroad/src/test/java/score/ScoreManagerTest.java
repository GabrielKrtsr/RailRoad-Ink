package score;

import board.Board;
import board.BoardGame;
import face.HighwayFace;
import face.RailFace;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import rotationStrategy.LeftRotation;
import util.util_for_nodes.Node;
import graph.Edge;
import graph.Graph;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ScoreManagerTest {
    private ScoreManager scoreManagerC;
    private ScoreManager scoreManagerE;
    private Board board;

    @BeforeEach
    void setUp() {
        scoreManagerC = new ClassicScoreManager();
        scoreManagerE = new ExtensionScoreManager();

        board = new BoardGame();
        Node borderNode = new Node();
        borderNode.setId(1);
        borderNode.setExitNode(true);

        board.getGraph().addVertex(Set.of(borderNode));
    }




    @Test
    void calculateFinalScore_withValidRoad_returnsPositiveScore() {
        HighwayFace h = new HighwayFace();
        HighwayFace h1 = new HighwayFace();
        board.placeFaceInTheCell(0,1,h);
        board.placeFaceInTheCell(1,1,h1);
        double score = scoreManagerC.calculateFinalScore(board);
        double score2 = scoreManagerE.calculateFinalScore(board);
        assertTrue(score > 0);
        assertTrue(score2 > 0);
    }

    @Test
    void calculateFinalScore_withValidRail_returnsPositiveScore() {
        RailFace r = new RailFace();
        RailFace r1 = new RailFace();
        r.rotate(new LeftRotation());
        r1.rotate(new LeftRotation());
        board.placeFaceInTheCell(1,0,r);
        board.placeFaceInTheCell(1,1,r1);
        double score = scoreManagerC.calculateFinalScore(board);
        double score2 = scoreManagerE.calculateFinalScore(board);
        assertTrue(score > 0);
        assertTrue(score2 > 0);
    }

    @Test
    void testBoardHasNodes() {
        assertNotNull(board.getGraph());
        assertFalse(board.getGraph().getVertices().isEmpty());
    }


}