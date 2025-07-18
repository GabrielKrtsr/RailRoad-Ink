package score.detection;

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
import util.Path;

import java.util.List;

public class DetectorsMain {

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

        System.out.println(FalseRouteDetectorWithoutBoardNodes.detectFalseRoutesWithSides(board));

        List<Cell> cells = LongestPathDetectorWithoutStations.detectLongestPath(board, Path.ROAD);

        System.out.println(cells);


        List<List<Cell>> networks = NetworksDetector.detectNetworks(board);

        for(int i = 0; i < board.getCells().length; i++){
            for(int j = 0; j < board.getCells().length; j++){
                if(cells.contains(board.getCell(i,j))){
                    System.out.println("("+ i + ", " + j +")");
                }
            }
        }


        System.out.print("[");
        for (int k = 0; k < networks.size(); k++) {
            List<Cell> network = networks.get(k);
            System.out.print("[");
            for (int m = 0; m < network.size(); m++) {
                Cell cell = network.get(m);
                int row = -1, col = -1;

                // Locate the cell's row and column on the board
                for (int i = 0; i < board.getCells().length; i++) {
                    for (int j = 0; j < board.getCells()[i].length; j++) {
                        if (board.getCell(i, j) == cell) {
                            row = i;
                            col = j;
                            break;
                        }
                    }
                    if (row != -1) break;
                }

                System.out.print("(" + row + "," + col + ")");
                if (m < network.size() - 1) {
                    System.out.print(",");
                }
            }
            System.out.print("]");
            if (k < networks.size() - 1) {
                System.out.print(",");
            }
        }
        System.out.println("]");




    }
}
