package main;


import board.Board;
import cell.Cell;
import face.AbstractFace;
import rotationStrategy.RotationStrategy;
import score.calculation.*;
import util.PossibilitiesForAllFaces;
import util.Tuple;

import java.util.*;

/**
 * Class PlayerAI2
 */
public class PlayerAI2 extends Player {
    private final int depth;
    private final int numberOfShuffles;
    private LongestRailCalculator railCalculator;
    private LongestRoadCalculator roadCalculator;
    private NetworksConnectedCalculator networksCalculator;
    private FalseRouteCalculator falseRouteCalculator;
    private CentralCellsCalculator centralCellsCalculator;


    /**
     * Constructs a new PlayerAI2 with the specified player ID.
     *
     * @param id The unique identifier for this player
     */
    public PlayerAI2(String id) {
        super(id);
        this.depth = 20;
        this.numberOfShuffles = 5;
        this.railCalculator = new LongestRailCalculatorWithoutStations();
        this.roadCalculator = new LongestRoadCalculatorWithoutStations();
        this.networksCalculator = new NetworksConnectedCalculator();
        this.falseRouteCalculator = new FalseRouteCalculator();
        this.centralCellsCalculator = new CentralCellsCalculator();
    }

    /**
     * Main method to execute the AI's turn
     */
    public void play() {
        try {
            placeAllFaces();
        } catch (CloneNotSupportedException e) {
        } catch (Exception e) {
        }
    }
    /**
     * Places all available faces on the board in an optimal sequence.
     *
     * @throws CloneNotSupportedException If cloning operations fail during the search process
     */
    private void placeAllFaces() throws CloneNotSupportedException {
        Board board = this.getBoard();

        while (!this.getFaces().isEmpty()) {
            Move bestMove = findBestShuffle(board, this.getFaces());

            if (bestMove == null || bestMove.face == null || bestMove.cell == null) {
                this.getFaces().remove(0);
                continue;
            }

            Tuple<Integer, Integer> point = board.getPointOfCell(bestMove.cell);
            if (point == null) {
                //this.getFaces().remove(0);
                continue;
            }

            bestMove.face.setImageSvg();
            bestMove.face.rotate(bestMove.rotation);
            board.placeFaceInTheCell(point.getType1(), point.getType2(), bestMove.face);
            this.getFaces().remove(bestMove.face);
        }
    }
    /**
     * Finds the best move by evaluating multiple shuffled orderings of available faces
     *
     * @param board The current board state
     * @param faces The list of available faces
     * @return The best move found
     */
    private Move findBestShuffle(Board board, List<AbstractFace> faces) {
        Move bestOverallMove = null;
        int bestOverallScore = Integer.MIN_VALUE;

        List<List<AbstractFace>> allShuffles = generateShuffles(faces);

        for (List<AbstractFace> shuffled : allShuffles) {
            int totalScore = 0;
            Move tempBestMove = null;

            for (AbstractFace face : shuffled) {
                Map<Tuple<AbstractFace, RotationStrategy>, List<Cell>> possibilities =
                        PossibilitiesForAllFaces.getAllPossibilities(Collections.singletonList(face), board);

                if (possibilities == null || possibilities.isEmpty()) {
                    continue;
                }

                Move move = minimax(board, possibilities, depth, true, Integer.MIN_VALUE, Integer.MAX_VALUE);

                if (move != null) {
                    totalScore += move.score;
                    tempBestMove = move;
                }
            }

            if (totalScore > bestOverallScore) {
                bestOverallScore = totalScore;
                bestOverallMove = tempBestMove;
            }
        }

        return bestOverallMove;
    }

    /**
     * Generates multiple shuffled orderings of the available faces
     *
     * @param faces The list of available faces to shuffle
     * @return A list containing multiple shuffled face sequences
     */
    private List<List<AbstractFace>> generateShuffles(List<AbstractFace> faces) {
        List<List<AbstractFace>> shuffles = new ArrayList<>();
        for (int i = 0; i < numberOfShuffles; i++) {
            List<AbstractFace> shuffledFaces = new ArrayList<>(faces);
            Collections.shuffle(shuffledFaces);
            shuffles.add(shuffledFaces);
        }
        return shuffles;
    }
    /**
     * Implements the Minimax algorithm with Alpha-Beta pruning for move evaluation.
     *
     * @param board The current board
     * @param possibilities Map of possible face placements with their locations
     * @param depth Current search depth remaining
     * @param maximizingPlayer Whether the current player is maximizing (true) or minimizing (false)
     * @param alpha Alpha value for pruning
     * @param beta Beta value for pruning
     * @return The best move found with its associated score
     */
    private Move minimax(Board board, Map<Tuple<AbstractFace, RotationStrategy>, List<Cell>> possibilities, int depth, boolean maximizingPlayer, int alpha, int beta) {
        if (depth == 0 || isGameOver(board)) {
            return new Move(null, null, null, evaluateBoard(board));
        }

        Move bestMove = null;
        int maxEval = Integer.MIN_VALUE;

        for (Map.Entry<Tuple<AbstractFace, RotationStrategy>, List<Cell>> entry : possibilities.entrySet()) {
            AbstractFace face = entry.getKey().getType1();
            RotationStrategy rotation = entry.getKey().getType2();

            for (Cell cell : entry.getValue()) {
                face.rotate(rotation);
                Tuple<Integer, Integer> point = board.getPointOfCell(cell);
                if (point == null) continue;

                board.placeFaceInTheCell(point.getType1(), point.getType2(), face);

                Map<Tuple<AbstractFace, RotationStrategy>, List<Cell>> clonedPossibilities = clonePossibilities(possibilities, board, face);
                Move currentMove = minimax(board, clonedPossibilities, depth - 1, true, alpha, beta);

                board.removeFaceFromCell(point.getType1(), point.getType2());
                face.rotate(rotation.getOppositeRotation());

                if (currentMove != null && currentMove.score > maxEval) {
                    maxEval = currentMove.score;
                    bestMove = new Move(face, rotation, cell, maxEval);
                }

                alpha = Math.max(alpha, maxEval);
                if (beta <= alpha) {
                    break;
                }
            }
        }

        return bestMove != null ? bestMove : new Move(null, null, null, evaluateBoard(board));
    }

    /**
     * Evaluates the current board state using multiple scoring metrics
     *
     * @param board The board evaluate
     * @return the score
     */
    private int evaluateBoard(Board board) {
        return (int) (
                railCalculator.calculateScore(board) +
                        roadCalculator.calculateScore(board) +
                        networksCalculator.calculateScore(board) * 15 +
                        centralCellsCalculator.calculateScore(board) -
                        falseRouteCalculator.calculateScore(board) * 15
        );
    }

    /**
     * Determines if the game is over by checking if there are no faces left.
     *
     * @param board The current board
     * @return True if the game is over, false otherwise
     */
    private boolean isGameOver(Board board) {
        return this.getFaces().isEmpty();
    }

    /**
     * Creates a clone of the possibilities map excluding the used face
     *
     * @param possibilities map of placement possibilities
     * @param board The current board
     * @param usedFace The face
     * @return A new map of placement possibilities without the used face
     */
    private Map<Tuple<AbstractFace, RotationStrategy>, List<Cell>> clonePossibilities(Map<Tuple<AbstractFace, RotationStrategy>, List<Cell>> possibilities, Board board, AbstractFace usedFace) {
        Map<Tuple<AbstractFace, RotationStrategy>, List<Cell>> clonedPossibilities = new HashMap<>();

        for (Map.Entry<Tuple<AbstractFace, RotationStrategy>, List<Cell>> entry : possibilities.entrySet()) {
            AbstractFace face = entry.getKey().getType1();
            if (face.equals(usedFace)) {
                continue;
            }
            RotationStrategy rotation = entry.getKey().getType2();
            List<Cell> availableCells = board.getAvailableCells(face);

            if (!availableCells.isEmpty()) {
                clonedPossibilities.put(new Tuple<>(face, rotation), availableCells);
            }
        }

        return clonedPossibilities;
    }

    /**
     * Utility class to represent a possible move.
     */
    private static class Move {
        private final AbstractFace face;
        private final RotationStrategy rotation;
        private final Cell cell;
        private final int score;

        /**
         * Constructs a new Move with the specified parameters.
         *
         * @param face The face to place
         * @param rotation The rotation to apply to the face
         * @param cell The cell where the face should be placed
         * @param score The evaluation score for this move
         */
        public Move(AbstractFace face, RotationStrategy rotation, Cell cell, int score) {
            this.face = face;
            this.rotation = rotation;
            this.cell = cell;
            this.score = score;
        }
    }
}
