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
     * Represents a list of all nodes managed during the initialization process of a board.
     * This collection contains nodes of type {@code Node}, which may include regular nodes,
     * exit nodes, and central nodes, depending on the board configuration.
     * The {@code allNodes} variable is used to store and organize these nodes for
     * further processing, such as connection establishment and pathfinding.
     */
    private List<Node> allNodes;
    /**
     * The board on which the nodes are initialized and managed.
     * Represents the core structure used by the NodeBoardInitializer class
     * for configuring and handling node-related operations.
     */
    private Board board;


    /**
     * Constructs a NodeBoardInitializer with the specified Board instance.
     *
     * @param board The Board object to initialize nodes on.
     */
    public NodeBoardInitializer(Board board){
        this.board = board;
    }

    /**
     * Initializes the nodes on the board by adding them and marking the exit nodes.
     *
     * This method performs the following operations:
     * - Invokes the private method addNodes to add all necessary nodes to the board's cells.
     * - Invokes the private method addExitNodes to identify and set the exit nodes based on the board's border.
     */
    public void initializeNodes(){
        this.addNodes();
        //this.changeBorders();
        this.addExitNodes();
    }

    /**
     * Adds nodes to the cells of the game board. Each cell is assigned a central node
     * and additional nodes are created along the edges and shared with neighboring cells
     * based on their position on the board.
     *
     * Each node is assigned a unique identifier and properties are configured to designate
     * central or shared status. The method accounts for boundary conditions on the board edges,
     * ensuring edge nodes are created for these scenarios.
     *
     * Connections between neighboring cells are established by linking their respective
     * shared edge nodes.
     */
    private void addNodes(){
        int id = 0;
        Cell [][] cells = this.board.getCells();
        for(int i = 0; i < cells.length; i++){
            for(int j = 0; j < cells[0].length; j++){
                Cell cell = cells[i][j];
                cell.putNode(Side.CENTER,new Node());
                cell.getNode(Side.CENTER).setId(id);
                cell.getNode(Side.CENTER).setCentralNode(true);
                id++;
                Cell bottomNeighbour = board.getNeighbour(i, j, Side.BOTTOM);
                Cell rightNeighbour = board.getNeighbour(i, j, Side.RIGHT);

                if(i == 0){
                    Node node = new Node();
                    cell.putNode(Side.TOP, node);
                    cell.getNode(Side.TOP).setId(id);
                    id++;
                }
                if(j == 0){
                    Node node = new Node();
                    cell.putNode(Side.LEFT, node);
                    cell.getNode(Side.LEFT).setId(id);
                    id++;
                }
                if(i == cells.length - 1){
                    Node node = new Node();
                    cell.putNode(Side.BOTTOM, node);
                    cell.getNode(Side.BOTTOM).setId(id);
                    id++;
                }
                if(j == cells[i].length - 1){
                    Node node = new Node();
                    cell.putNode(Side.RIGHT, node);
                    cell.getNode(Side.RIGHT).setId(id);
                    id++;
                }

                if (bottomNeighbour != null) {
                    Node node = new Node();
                    cell.putNode(Side.BOTTOM, node);
                    cell.getNode(Side.BOTTOM).setId(id);
                    bottomNeighbour.putNode(Side.BOTTOM.getOppositeSide(), node);
                    id++;
                }

                if (rightNeighbour != null) {
                    Node node = new Node();
                    cell.putNode(Side.RIGHT, node);
                    cell.getNode(Side.RIGHT).setId(id);
                    rightNeighbour.putNode(Side.RIGHT.getOppositeSide(), node);
                    id++;
                }
            }
        }
    }

    /**
     * Configures the nodes of the board's cells to be marked as exit nodes if they
     * belong to specific sides of BorderCells based on their position in the grid.
     * This method identifies and marks the nodes for designated border positions:
     * - Top side for cells in the first row.
     * - Left side for cells in the first column.
     * - Bottom side for cells in the last row.
     * - Right side for cells in the last column.
     *
     * For each identified border position, the corresponding node will be set as an exit node,
     * provided that the node exists on the cell's specified side.
     */
    private void addExitNodes() {
        Cell[][] cells = this.board.getCells();
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[0].length; j++) {
                Cell cell = cells[i][j];

                if (i == 0 ) {
                    Node topNode = cell.getNode(Side.TOP);
                    topNode.setBoardNode(true);
                    if(cell instanceof BorderCell){
                        if (topNode != null) {
                            topNode.setExitNode(true);
                        }
                    }
                }

                if (j == 0 ) {
                    Node leftNode = cell.getNode(Side.LEFT);
                    leftNode.setBoardNode(true);
                    if(cell instanceof BorderCell){

                        if (leftNode != null) {
                            leftNode.setExitNode(true);
                        }
                    }

                }

                if (i == cells.length - 1  ) {
                    Node bottomNode = cell.getNode(Side.BOTTOM);
                    bottomNode.setBoardNode(true);
                    if(cell instanceof BorderCell){

                        if (bottomNode != null) {
                            bottomNode.setExitNode(true);
                        }
                    }

                }

                if (j == cells[i].length - 1 ) {
                    Node rightNode = cell.getNode(Side.RIGHT);
                    rightNode.setBoardNode(true);
                    if(cell instanceof BorderCell){

                        if (rightNode != null) {
                            rightNode.setExitNode(true);
                        }
                    }

                }
            }
        }
    }


}
