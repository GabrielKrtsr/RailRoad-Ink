package main;


import board.Board;
import cell.Cell;
import face.*;
import main.*;
import rotationStrategy.RotationStrategy;
import util.PossibilitiesForAllFaces;
import score.calculation.*;
import util.Tuple;
import util.Type;


import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Class PlayerAI
 */
public class PlayerAI extends Player {
    private List<AbstractFace> facesSpecial;
    private final LongestRailCalculator railCalculator;
    private final LongestRoadCalculator roadCalculator;
    private final NetworksConnectedCalculator networksCalculator;
    private final FalseRouteCalculator falseRouteCalculator;
    private final CentralCellsCalculator centralCellsCalculator;

    /**
     * coefficient of the nice move
     */
    private static final double FUTURE_POTENTIAL_WEIGHT = 0.8;

    /**
     * coefficient of the Networks
     */
    private static final int NETWORKS_WEIGHT = 2;

    /**
     * coefficient of the central cell
     */
    private static final int CENTRAL_CELLS_WEIGHT = 2;

    /**
     * coefficient of the false route
     */
    private static final int FALSE_ROUTE_WEIGHT = 1;

    /**
     * coefficient of the longest path
     */
    private static final int LONGEST_PATH_WEIGHT = 1;

    private int currentSpetial;

    /**
     * Constructs a new AI player with the specified ID.
     *
     * @param id The unique identifier for this player
     */
    public PlayerAI(
            String id
    ) {
        super(id);
        this.railCalculator = new LongestRailCalculatorWithoutStations();
        this.roadCalculator = new LongestRoadCalculatorWithoutStations();
        this.networksCalculator = new NetworksConnectedCalculator();
        this.falseRouteCalculator = new FalseRouteCalculatorWithBoardNodes();
        this.centralCellsCalculator = new CentralCellsCalculator();
        facesSpecial = new ArrayList<>(List.of(
                new HighwayHighwayFace(),
                new StationHighwayFace(),
                new StationHighwayRailFace(),
                new StationRailFace(),
                new StationStationFace(),
                new RailRailFace()
        ));


    }

    /**
     * Main AI method that iteratively places faces on the board until
     */
    public void play() {
        try {
            currentSpetial = 0;
            List<AbstractFace> faces = this.getFaces();
            Board board = this.getBoard();
            while (!faces.isEmpty()) {
                List<AbstractFace> nextFaces = new ArrayList<>(faces);
                if(currentSpetial == 0 && facesSpecial.size() >3){
                    nextFaces.addAll(facesSpecial);
                }
                Move bestMove = findBestMoveForLongestPath(nextFaces, board);
                if (bestMove != null) {
                    AbstractFace bestFace = bestMove.getFace();
                    if(bestFace.getType() == Type.SPECIAL){
                        currentSpetial++;
                        facesSpecial.remove(bestFace);
                    }
                    bestFace.setImageSvg();
                    bestFace.rotate(bestMove.getRotation());
                    Tuple<Integer, Integer> point = board.getPointOfCell(bestMove.getCell());
                    board.placeFaceInTheCell(point.getType1(), point.getType2(), bestFace);
                    faces.remove(bestFace);
                } else {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Finds the best move to maximize path length and overall score.
     *
     * @param faces The list of faces available for placement
     * @param board The current board state
     * @return The best move found, or null if no valid moves exist
     */
    private Move findBestMoveForLongestPath(List<AbstractFace> faces, Board board) {
        Move bestMove = null;
        double bestPathScore = Double.NEGATIVE_INFINITY;
        double niceMove = Double.NEGATIVE_INFINITY;
        Map<Tuple<AbstractFace, RotationStrategy>, List<Cell>> possibilities =
                PossibilitiesForAllFaces.getAllPossibilities(faces, board);

        for (Map.Entry<Tuple<AbstractFace, RotationStrategy>, List<Cell>> entry : possibilities.entrySet()) {
            AbstractFace face = entry.getKey().getType1();
            RotationStrategy rotation = entry.getKey().getType2();

            for (Cell cell : entry.getValue()) {
                face.rotate(rotation);
                Tuple<Integer, Integer> point = board.getPointOfCell(cell);
                board.placeFaceInTheCell(point.getType1(), point.getType2(), face);
                double totalScore = evaluateCurrentScore(board) * FUTURE_POTENTIAL_WEIGHT;
                if (totalScore>niceMove) {
                    niceMove = totalScore;
                    double futurePotential = evaluateFuturePotential(faces, board, face);
                    totalScore += FUTURE_POTENTIAL_WEIGHT * futurePotential;
                }
                if (totalScore > bestPathScore) {
                    bestPathScore = totalScore;
                    bestMove = new PlayerAI.Move(face, rotation, cell);
                }

                board.removeFaceFromCell(point.getType1(), point.getType2());
                face.rotate(rotation.getOppositeRotation());
            }
        }

        return bestMove;
    }


    /**
     * Calculates the current score of the board based on multiple metrics.
     *
     * @param board The current board state to evaluate
     * @return A score of the board
     */
    private double evaluateCurrentScore(Board board) {
        return railCalculator.calculateScore(board) * LONGEST_PATH_WEIGHT
                + roadCalculator.calculateScore(board) * LONGEST_PATH_WEIGHT
                + networksCalculator.calculateScore(board) * NETWORKS_WEIGHT
                + centralCellsCalculator.calculateScore(board) * CENTRAL_CELLS_WEIGHT
                - falseRouteCalculator.calculateScore(board) * FALSE_ROUTE_WEIGHT ;
    }


    /**
     * Evaluates the future potential of the board after a move.
     *
     * @param faces The remaining faces available for placement
     * @param board The board state after the current move
     * @param usedFace The face that was placed in the current move
     * @return A score representing the future potential of the board
     */
    private double evaluateFuturePotential(List<AbstractFace> faces, Board board, AbstractFace usedFace) {
        double futureBestScore = Double.NEGATIVE_INFINITY;

        List<AbstractFace> nextFaces = new ArrayList<>(faces);
        nextFaces.remove(usedFace);

        Map<Tuple<AbstractFace, RotationStrategy>, List<Cell>> possibilities =
                null;
        possibilities = PossibilitiesForAllFaces.getAllPossibilities(nextFaces, board);

        for (Map.Entry<Tuple<AbstractFace, RotationStrategy>, List<Cell>> entry : possibilities.entrySet()) {
            AbstractFace face = entry.getKey().getType1();
            RotationStrategy rotation = entry.getKey().getType2();

            for (Cell cell : entry.getValue()) {
                face.rotate(rotation);
                Tuple<Integer, Integer> point = board.getPointOfCell(cell);
                board.placeFaceInTheCell(point.getType1(), point.getType2(), face);
                double score = evaluateCurrentScore(board);
                futureBestScore = Math.max(futureBestScore, score);

                board.removeFaceFromCell(point.getType1(), point.getType2());
                face.rotate(rotation.getOppositeRotation());
            }
        }

        return futureBestScore;
    }



    /**
     * Utility class to represent a possible move.
     */
    private static class Move {
        private final AbstractFace face;
        private final RotationStrategy rotation;
        private final Cell cell;

        /**
         * Constructs a new Move with the specified parameters.
         *
         * @param face     The face to place
         * @param rotation The rotation to apply to the face
         * @param cell     The cell where the face should be placed
         */
        public Move(AbstractFace face, RotationStrategy rotation, Cell cell) {
            this.face = face;
            this.rotation = rotation;
            this.cell = cell;
        }

        /**
         * Gets the face associated with this move.
         *
         * @return The face to be placed
         */
        public AbstractFace getFace() {
            return face;
        }

        /**
         * Gets the rotation strategy associated with this move.
         *
         * @return The rotation to apply to the face
         */
        public RotationStrategy getRotation() {
            return rotation;
        }

        /**
         * Gets the target cell associated with this move.
         *
         * @return The cell where the face should be placed
         */
        public Cell getCell() {
            return cell;
        }
    }
}
