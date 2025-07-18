package board;

import cell.BorderCell;
import cell.Cell;
import util.util_for_nodes.Node;
import util.util_for_nodes.Side;

import java.util.List;

/**
 * The NodeBoardInitializer class is responsible for initializing nodes within a game board.
 * It interacts with cells and their associated nodes to ensure that all necessary nodes,
 * including central, border, and exit nodes, are created and configured properly.
 *
 * The class operates on the provided board instance and applies specific logic to populate
 * the board with nodes, establish connections between neighboring nodes, and mark certain
 * nodes as exit points.
 */
public class NodeBoardInitializer {

    /**
     * The board on which the nodes are initialized and managed.
     */
    private final Board board;

    /**
     * Constructs a NodeBoardInitializer with the specified Board instance.
     *
     * @param board The Board object to initialize nodes on.
     */
    public NodeBoardInitializer(Board board) {
        this.board = board;
    }

    /**
     * Initializes the nodes on the board by adding them and marking the exit nodes.
     */
    public void initializeNodes() {
        addNodes();
        addExitNodes();
    }

    /**
     * Adds nodes to the cells of the game board and handles their connections.
     */
    private void addNodes() {
        int id = 0;
        Cell[][] cells = board.getCells();

        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[0].length; j++) {
                Cell cell = cells[i][j];

                Node centerNode = createNode(id++, true);
                cell.putNode(Side.CENTER, centerNode);

                addBorderNodes(i, j, id, cell, cells.length, cells[0].length);
                id = connectNeighborNodes(i, j, id, cell);
            }
        }
    }

    /**
     * Adds border nodes for the given cell if it lies on the board's edges.
     */
    private void addBorderNodes(int row, int col, int id, Cell cell, int totalRows, int totalCols) {
        if (row == 0) {
            cell.putNode(Side.TOP, createNode(id++, false));
        }
        if (col == 0) {
            cell.putNode(Side.LEFT, createNode(id++, false));
        }
        if (row == totalRows - 1) {
            cell.putNode(Side.BOTTOM, createNode(id++, false));
        }
        if (col == totalCols - 1) {
            cell.putNode(Side.RIGHT, createNode(id++, false));
        }
    }

    /**
     * Connects the cell's edge nodes with its neighbors.
     */
    private int connectNeighborNodes(int row, int col, int id, Cell cell) {
        Cell bottomNeighbor = board.getNeighbour(row, col, Side.BOTTOM);
        Cell rightNeighbor = board.getNeighbour(row, col, Side.RIGHT);

        if (bottomNeighbor != null) {
            Node bottomNode = createNode(id++, false);
            cell.putNode(Side.BOTTOM, bottomNode);
            bottomNeighbor.putNode(Side.BOTTOM.getOppositeSide(), bottomNode);
        }

        if (rightNeighbor != null) {
            Node rightNode = createNode(id++, false);
            cell.putNode(Side.RIGHT, rightNode);
            rightNeighbor.putNode(Side.RIGHT.getOppositeSide(), rightNode);
        }

        return id;
    }

    /**
     * Adds exit nodes to the border cells of the board.
     */
    private void addExitNodes() {
        Cell[][] cells = board.getCells();

        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[0].length; j++) {
                Cell cell = cells[i][j];

                markExitNode(cell, i == 0, Side.TOP);
                markExitNode(cell, j == 0, Side.LEFT);
                markExitNode(cell, i == cells.length - 1, Side.BOTTOM);
                markExitNode(cell, j == cells[0].length - 1, Side.RIGHT);
            }
        }
    }

    /**
     * Marks a node on the given side as an exit node if the condition is met.
     */
    private void markExitNode(Cell cell, boolean condition, Side side) {
        if (condition) {
            Node node = cell.getNode(side);
            if (node != null) {
                node.setBoardNode(true);
                if (cell.isBorderCell()) {
                    node.setExitNode(true);
                }
            }
        }
    }

    /**
     * Helper method to create a node and assign its basic properties.
     */
    private Node createNode(int id, boolean isCentral) {
        Node node = new Node();
        node.setId(id);
        node.setCentralNode(isCentral);
        return node;
    }
}
