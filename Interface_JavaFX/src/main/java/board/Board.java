package board;



import util.util_for_nodes.Node;
import util.util_for_nodes.Side;
import rules.*;

import java.util.*;

import util.*;
import cell.Cell;
import cell.CentralCell;
import cell.ClassicCell;
import face.AbstractFace;


import util.SimpleBoardGraph;
import graph.Edge;
import graph.Graph;


/**
 * Represents a generic abstract game board composed of a grid of cells.
 */
public abstract class Board implements Cloneable{

    /**
     * ANSI color code for white background.
     * */
    public static final String WHITE = "\u001B[47m";

    /**
     * ANSI reset color code.
     * */
    public static final String RESET = "\u001B[0m";

    /**
     *  ANSI color code for green background.
     * */
    public static final String GREEN = "\u001B[42m";

    /** Graph representation of the board. */
    private SimpleBoardGraph graph;
    /**
     * The grid of cells on the game board.
     * */
    protected Cell[][] cells;


    /**
     * List of paths on the board.
     * */
    protected List<Path> paths;

    public static int incrementId = 0;


    /**
     * Constructs a game board of specified size
     *
     * @param size  the size of the board
     * @param graph the graph
     */
    public Board(int size, SimpleBoardGraph graph) {
        this.cells = new Cell[size][size];
        this.paths = new ArrayList<>();
        this.init();
        this.initBorderCell();
        this.graph = graph;//

    }


    /**
     * Displays the board in the console with color representation.
     */
    public void display() {
        for (int i = 0; i < this.cells.length; i++) {
            for (int j = 0; j < this.cells.length; j++) {
                if(this.cells[i][j].getFace() == null) System.out.print(WHITE + "  " + RESET + " "); else System.out.print(GREEN + "  " + RESET + " ");
            }
            System.out.println();
            System.out.println();
        }
    }

    /**
     * Constructs a game board of specified size
     *
     * @param size the size of the board
     */
    public Board(int size) {
        this.cells = new Cell[size][size];
        this.init();
        this.initBorderCell();
        this.graph = new SimpleBoardGraph();
    }

    /**
     * Initializes the grid of cells for the board. The grid is divided into sections
     * where specific cells are placed based on their position within the grid.
     */
    private void init() {
        int size = this.cells.length;
        int sizeMiddle = size / 2;
        int blockSize = 3;

        int start = sizeMiddle - blockSize / 2;
        int end = sizeMiddle + blockSize / 2;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i >= start && i <= end && j >= start && j <= end) {
                    this.cells[i][j] = new CentralCell();

                } else {
                    this.cells[i][j] = new ClassicCell();
                }
            }
        }

    }

    //for graph
    /**
     * Adds a graph to the board.
     *
     * @param graph the graph to be added
     */
    public void addGraph(SimpleBoardGraph graph) {
        this.graph = graph;
    }



    /**
     * Gives the grid of Cells in the board
     *
     * @return the grid of cells on the game board
     */
    public Cell[][] getCells() {
        return this.cells;
    }

    /**
     * Retrieves the Cell at the specified position on the board.
     *
     * @param x the X-coordinate of the cell position within the grid
     * @param y the Y-coordinate of the cell position within the grid
     * @return the Cell located at the given (x, y) position
     */
    public Cell getCell(int x, int y) {
        return this.cells[x][y];
    }


    /**
     * Places a specified face in a cell on the board.
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @param f the face object to be placed
     * @return true if placement was successful, false otherwise
     */
    public boolean placeFaceInTheCell(int x, int y, AbstractFace f) {
        if (verifIndex(x, y)) {
            throw new IndexOutOfBoundsException();
        }
        if (!PlacementRulesGame.getInstance().canPlaceFace(x, y, f, this)) {
            return false;
        }
        this.cells[x][y].setFace(f);




        return true;
    }



    /**
     * Checks whether the given coordinates (x, y) are out of bounds
     * of the cells array.
     *
     * @param x the x-coordinate of the cell.
     * @param y the y-coordinate of the cell.
     * @return true if the coordinates are out of bounds, {@code false} otherwise
     */
    protected boolean verifIndex(int x, int y) {
        return x < 0 || x >= this.cells.length || y < 0 || y >= this.cells[0].length;
    }


    /**
     * Initializes the grid cells that are part of the border of the board.
     * This method is responsible for defining specific behaviors or configurations
     * applicable to the border cells distinct from other regions of the grid.
     * The implementation of this method must be provided by a concrete subclass.
     */
    protected abstract void initBorderCell();


    /**
     * Retrieves the neighboring cell of a specific cell in the direction of the provided side.
     *
     * @param i    the row index of the current cell
     * @param j    the column index of the current cell
     * @param side the side indicating the direction of the neighbor
     * @return the neighboring cell, or null if no neighbor exists
     */
    public Cell getNeighbour(int i, int j, Side side) {
        int ni = i, nj = j;
        switch (side) {
            case TOP:
                ni = i - 1;
                break;
            case BOTTOM:
                ni = i + 1;
                break;
            case LEFT:
                nj = j - 1;
                break;
            case RIGHT:
                nj = j + 1;
                break;
            default:
                return null;
        }
        if (verifIndex(ni, nj)) {
            return null;
        }
        return this.cells[ni][nj];
    }

    /**
     * Displays the grid in the console
     */
    public void displayConsole() {
        for (int i = 0; i < this.cells.length; i++) {
            for (int j = 0; j < this.cells[i].length; j++) {
                System.out.print("+-----");
            }
            System.out.println("+");

            for (int j = 0; j < this.cells[i].length; j++) {
                String cellName = this.cells[i][j].toString();
                System.out.print("| " + (cellName.length() > 3 ? cellName.substring(0, 3) : cellName) + " ");
            }
            System.out.println("|");
        }

        for (int j = 0; j < this.cells[0].length; j++) {
            System.out.print("+-----");
        }
        System.out.println("+");
    }

    /**
     * Retrieves the graph representation of nodes and edges.
     *
     * @return A Graph object representing a structure of nodes and edges.
     */
    public Graph<Set<Node>, Edge<Set<Node>>> getGraph() {
        return this.graph.getGraph();
    }

    /**
     * Updates the edges of the graph based on connected nodes.
     */
    public void updateEdge() {
        Set<Set<Node>> vertexSets = this.graph.getSetOfConnections();
        for (Set<Node> set1 : vertexSets) {
            for (Set<Node> set2 : vertexSets) {
                if (set1.equals(set2)) continue;

                boolean edgeExists = this.graph.containsConnection(set1,set2);
                boolean nodesConnected = false;

                for (Node node1 : set1) {
                    for (Node node2 : set2) {
                        if (node1.isConnected(node2)) {
                            nodesConnected = true;
                            break;
                        }
                    }
                    if (nodesConnected) break;
                }
                if (nodesConnected && !edgeExists) {
                    this.graph.connectNodeSets(set1, set2);
                }
            }
        }
    }




    /**
     * Updates the vertex set in the graph with inner connections.
     *
     * @param innerConnections list of node sets representing connections
     */
    public void updateVertex(List<Set<Node>> innerConnections) {
        for(Set<Node> nodeSet: innerConnections){
            this.graph.addNodeSet(nodeSet);
        }
        updateEdge();
    }


//    /**
//     * Removes the vertex sets and their connections from the graph.
//     * Also removes all edges connected to each vertex set in the provided list.
//     *
//     * @param innerConnections list of node sets to be removed.
//     */
//    public void removeVertex(List<Set<Node>> innerConnections) {
//        for (Set<Node> nodeSet : innerConnections) {
//            this.graph.getSetOfConnections().remove(nodeSet);
//
//
//            // Remove all edges associated with the node set
//            for (Set<Node> otherNodeSet : new HashSet<>(this.graph.getSetOfConnections())) {
//                if (this.graph.containsConnection(nodeSet, otherNodeSet)) {
//                    this.graph.getGraph().removeEdge(nodeSet, otherNodeSet);
//
//                }
//            }
//            this.graph.getGraph().removeVertex(nodeSet);
//        }
//
//    }

    /**
     * Removes edges between the sets of nodes if they are no longer connected.
     */
    public void removeEdges() {
        Set<Set<Node>> vertexSets = this.graph.getSetOfConnections();
        for (Set<Node> set1 : vertexSets) {
            for (Set<Node> set2 : vertexSets) {
                if (set1.equals(set2)) continue;

                boolean edgeExists = this.graph.containsConnection(set1, set2);
                boolean nodesConnected = false;

                for (Node node1 : set1) {
                    for (Node node2 : set2) {
                        if (node1.isConnected(node2)) {
                            nodesConnected = true;
                            break;
                        }
                    }
                    if (nodesConnected) break;
                }

                if (!nodesConnected && edgeExists) {
                    this.graph.disconnectNodeSets(set1, set2);
                }
            }
        }
    }

    /**
     * Removes vertex sets from the graph and updates edges accordingly.
     *
     * @param nodeSetsToRemove
     */
    public void removeVertex(List<Set<Node>> nodeSetsToRemove) {
        for (Set<Node> nodeSet : nodeSetsToRemove) {
            this.graph.removeNodeSet(nodeSet);
        }
        removeEdges();
    }




    @Override
    public Board clone() {
        try {
            Board clonedBoard = (Board) super.clone();

            // Deep cloning cells array
            Cell[][] clonedCells = new Cell[this.cells.length][this.cells[0].length];
            for (int i = 0; i < this.cells.length; i++) {
                for (int j = 0; j < this.cells[i].length; j++) {
                    clonedCells[i][j] = this.cells[i][j].clone();
                }
            }
            clonedBoard.cells = clonedCells;

            // Cloning the graph
            clonedBoard.graph = new SimpleBoardGraph();
            for (Set<Node> nodeSet : this.graph.getSetOfConnections()) {
                clonedBoard.graph.addNodeSet(new HashSet<>(nodeSet));
            }
            for (Set<Node> nodeSet1 : this.graph.getSetOfConnections()) {
                for (Set<Node> nodeSet2 : this.graph.getSetOfConnections()) {
                    if (this.graph.containsConnection(nodeSet1, nodeSet2)) {
                        clonedBoard.graph.connectNodeSets(new HashSet<>(nodeSet1), new HashSet<>(nodeSet2));
                    }
                }
            }

            return clonedBoard;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
    /**
     * Retrieves a cell corresponding to a given node in the graph.
     *
     * @param node the node for which the cell needs to be retrieved.
     * @return the corresponding Cell object, or null if the node is not associated with any cell.
     */
    public Cell getCellByNode(Node node) {
        for (int i = 0; i < this.cells.length; i++) {
            for (int j = 0; j < this.cells[i].length; j++) {
                if (this.cells[i][j].containsNode(node)) {
                    return this.cells[i][j];
                }
            }
        }
        return null;
    }

    /**
     * Retrieves a cell on the board that contains the specified node within its inner connections.
     * This method searches the grid of cells to find the one that has the node in its inner connections.
     *
     * @param node the node for which the corresponding cell with inner connections needs to be found
     * @return the Cell object that contains the specified node in its inner connections, or null if no such cell is found
     */
    public Cell getCellByNodeWithInnerConnections(Node node) {
        for (int i = 0; i < this.cells.length; i++) {
            for (int j = 0; j < this.cells[i].length; j++) {
                if (this.cells[i][j].containsNodeInInnerConnections(node)) {
                    return this.cells[i][j];
                }
            }
        }
        return null;
    }

    public Tuple<Integer, Integer> getPointOfCell(Cell cell) {
        for (int i = 0; i < this.cells.length; i++) {
            for (int j = 0; j < this.cells[i].length; j++) {
                if (this.cells[i][j].equals(cell)) {
                    return new Tuple<>(i, j);
                }
            }
        }
        return null;
    }


    /**
     * Retrieves all cells on the board where a face can be placed.
     * A cell is considered available if it does not already contain a face
     * and the given face can be placed in it.
     *
     * @param face the face object to check for placement.
     * @return a list of available cells where the face can be placed.
     */
    public List<Cell> getAvailableCells(AbstractFace face) {
        List<Cell> availableCells = new ArrayList<>();

        for (int i = 0; i < this.cells.length; i++) {
            for (int j = 0; j < this.cells[i].length; j++) {
                Cell cell = this.cells[i][j];
                if (PlacementRulesGame.getInstance().canPlaceFace(i, j, face, this)) {
                    availableCells.add(cell);

                }
            }
        }
        return availableCells;
    }
    

    /**
     * Removes a face from a specified cell on the board.
     *
     * @param x the x-coordinate of the cell.
     * @param y the y-coordinate of the cell.
     * @return true if the face was successfully removed, false if the cell is empty or the coordinates are out of bounds.
     */
    public boolean removeFaceFromCell(int x, int y) {
        if (verifIndex(x, y)) {
            return false; // Out of bounds
        }

        Cell cell = this.cells[x][y];
        if (cell.getFace() != null) {
            cell.removeFace(); // Assuming removeFace method exists in Cell
            return true;
        }

        return false; // No face to remove
    }

    public int getOccupiedCells() {
        int counter = 0;
        for(int i =0 ; i<this.cells.length;i++){
            for(int j =0 ; j<this.cells.length;j++){
                if(this.cells[i][j].getFace() == null)
                    counter++;
            }
        }
        return counter;
    }
}
