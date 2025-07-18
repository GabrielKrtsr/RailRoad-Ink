package score;

import board.Board;
import board.BoardGame;
import cell.Cell;
import face.AbstractFace;
import face.HighwayCurveFace;
import face.HighwayFace;
import face.HighwayJunctionFace;
import rotationStrategy.FlippedLeftRotation;
import rotationStrategy.FlippedRotation;
import rotationStrategy.LeftRotation;
import rotationStrategy.RightRotation;
import score.calculation.FalseRouteCalculatorWithoutBoardNodes;
import score.calculation.LongestRailCalculatorWithoutStations;
import score.calculation.LongestRoadCalculatorWithoutStations;
import score.calculation.NetworksConnectedCalculator;
import util.util_for_nodes.Side;

public class ScoreMainWithCycles {
    public static void main(String[] args) {
        Board board = new BoardGame();

        AbstractFace face1 = new HighwayJunctionFace();
        
        AbstractFace face2 = new HighwayCurveFace();
        AbstractFace face3 = new HighwayFace();

        AbstractFace face4 = new HighwayCurveFace();
        AbstractFace face5 = new HighwayJunctionFace();

        AbstractFace face6 = new HighwayCurveFace();
        AbstractFace face7 = new HighwayFace();

        AbstractFace face8 = new HighwayCurveFace();
        AbstractFace face9 = new HighwayFace();

        AbstractFace face10 = new HighwayFace();



        face1.rotate(new LeftRotation());
        face2.rotate(new RightRotation());
        face5.rotate(new RightRotation());
        face6.rotate(new FlippedRotation());
        face8.rotate(new FlippedLeftRotation());
        
        Cell cell1 = board.getCell(0,1);
        Cell cell2 = board.getCell(0,0);
        Cell cell3 = board.getCell(1,0);
        Cell cell4 = board.getCell(2,0);
        Cell cell5 = board.getCell(2,1);
        Cell cell6 = board.getCell(2,2);
        Cell cell7 = board.getCell(1,2);
        Cell cell8 = board.getCell(0,2);

        Cell cell9 = board.getCell(3,1);

        Cell cell10 = board.getCell(4,4);
        
        cell1.setFace(face1);
        cell2.setFace(face2);
        cell3.setFace(face3);
        cell4.setFace(face4);
        cell5.setFace(face5);
        cell6.setFace(face6);
        cell7.setFace(face7);
        cell8.setFace(face8);
        cell9.setFace(face9);

        cell10.setFace(face10);






        System.out.println(cell1.getNode(Side.TOP).isExitNode());
        NetworksConnectedCalculator calculator = new NetworksConnectedCalculator();

        System.out.println(calculator.calculateScore(board));

        FalseRouteCalculatorWithoutBoardNodes calculator1 = new FalseRouteCalculatorWithoutBoardNodes();

        System.out.println(calculator1.calculateScore(board));

        LongestRoadCalculatorWithoutStations calculator2 = new LongestRoadCalculatorWithoutStations();

        System.out.println(calculator2.calculateScore(board));

        LongestRailCalculatorWithoutStations calculator3 = new LongestRailCalculatorWithoutStations();

        System.out.println(calculator3.calculateScore(board));


        System.out.println(board.getGraph());

        ScoreManager scoreManager = new ClassicScoreManager();
        System.out.println(scoreManager.calculateFinalScore(board));
    }
}
