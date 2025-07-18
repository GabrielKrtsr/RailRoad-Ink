package board;

import cell.BorderCell;
import cell.Cell;
import cell.CentralCell;
import cell.ClassicCell;
import face.AbstractFace;
import face.HighwayFace;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import graph.*;
import util.util_for_nodes.Node;
import util.util_for_nodes.Side;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.*;

import static board.Board.*;
import static org.junit.jupiter.api.Assertions.*;

public abstract class BoardTest {
    protected Board board;

    protected abstract Board createBoard();

    @BeforeEach
    public void setUp() {
        board = createBoard();
    }

    @Test
    void testPlaceFaceInTheCell_Success() {
        AbstractFace face = new HighwayFace();
        int x = 0;
        int y = 1;

        boolean result = board.placeFaceInTheCell(x, y, face);

        assertTrue(result);
        assertEquals(face, board.getCell(x, y).getFace());
    }

    @Test
    void testPlaceFaceInTheCell_Failure() {
        AbstractFace face = new HighwayFace();
        int x = 0;
        int y = 0;

        boolean result = board.placeFaceInTheCell(x, y, face);

        assertFalse(result);
    }

    @Test
    void testPlaceFaceInTheCell_OutOfBounds() {
        AbstractFace face = new HighwayFace();
        int x = board.getCells().length;
        int y = board.getCells()[0].length;

        assertThrows(IndexOutOfBoundsException.class, () -> board.placeFaceInTheCell(x, y, face));
    }

    @Test
    void testGetCell_Valid() {
        assertNotNull(board.getCell(0, 0));
    }

    @Test
    void testGetCell_OutOfBounds() {
        int x = board.getCells().length;
        int y = board.getCells()[0].length;

        assertThrows(ArrayIndexOutOfBoundsException.class, () -> board.getCell(x, y));
    }

    @Test
    void testCellTypeDistribution() {
        int size = board.getCells().length;
        int middle = size / 2;
        int blockSize = 3;
        int start = middle - blockSize / 2;
        int end = middle + blockSize / 2;

        assertInstanceOf(ClassicCell.class, board.getCell(0, 0));
        assertInstanceOf(CentralCell.class, board.getCell(middle, middle));

        for (int i = start; i <= end; i++) {
            for (int j = start; j <= end; j++) {
                assertInstanceOf(CentralCell.class, board.getCell(i, j));
            }
        }

        assertInstanceOf(ClassicCell.class, board.getCell(end, end + 1));
    }

    @Test
    void testGetNeighbour_Valid() {
        int middleX = board.getCells().length / 2;
        int middleY = board.getCells()[0].length / 2;

        assertNotNull(board.getNeighbour(middleX, middleY, Side.TOP));
        assertNotNull(board.getNeighbour(middleX, middleY, Side.BOTTOM));
        assertNotNull(board.getNeighbour(middleX, middleY, Side.LEFT));
        assertNotNull(board.getNeighbour(middleX, middleY, Side.RIGHT));
    }

    @Test
    void testGetNeighbour_OutOfBounds() {
        int size = board.getCells().length - 1;

        assertNull(board.getNeighbour(0, 0, Side.TOP));
        assertNull(board.getNeighbour(0, 0, Side.LEFT));
        assertNull(board.getNeighbour(size, size, Side.BOTTOM));
        assertNull(board.getNeighbour(size, size, Side.RIGHT));
    }

    @Test
    void testBoardInitialization() {
        Cell[][] cells = board.getCells();

        assertNotNull(cells);
        assertTrue(cells.length > 0);
        assertTrue(cells[0].length > 0);
        int size = board.getCells().length;
        int sizeMiddle = size / 2;
        int blockSize = 3;

        int start = sizeMiddle - blockSize / 2;
        int end = sizeMiddle + blockSize / 2;
        for (int i = 0; i < board.getCells().length; i++) {
            for (int j = 0; j < board.getCells().length; j++) {
                if (i >= start && i <= end && j >= start && j <= end) {
                    assertInstanceOf(CentralCell.class, board.getCell(i, j));

                } else {
                    assertTrue(board.getCell(i, j) instanceof ClassicCell || board.getCell(i, j) instanceof BorderCell);

                }
            }
        }
    }

    @Test
    void testGraphInitialization() {
        Graph<Set<Node>, Edge<Set<Node>>> graph = board.getGraph();
        assertNotNull(graph);
    }

    @Test
    void testUpdateEdge() {
        board.updateEdge();

        Graph<Set<Node>, Edge<Set<Node>>> graph = board.getGraph();
        assertNotNull(graph);
    }

    @Test
    void testUpdateVertex() {
        Set<Node> nodeSet1 = new HashSet<>(List.of(new Node(), new Node()));
        Set<Node> nodeSet2 = new HashSet<>(List.of(new Node(), new Node()));

        List<Set<Node>> innerConnections = List.of(nodeSet1, nodeSet2);
        board.updateVertex(innerConnections);

        Graph<Set<Node>, Edge<Set<Node>>> graph = board.getGraph();
        assertTrue(graph.containsVertex(nodeSet1));
        assertTrue(graph.containsVertex(nodeSet2));
    }


    @Test
    void testPlacingMultipleFaces() {
        AbstractFace face1 = new HighwayFace();
        AbstractFace face2 = new HighwayFace();

        boolean result1 = board.placeFaceInTheCell(0, 1, face1);
        boolean result2 = board.placeFaceInTheCell(1, 1, face2);

        assertTrue(result1);
        assertTrue(result2);
        assertEquals(face1, board.getCell(0, 1).getFace());
        assertEquals(face2, board.getCell(1, 1).getFace());
    }

    @Test
    void testGetCellByNode() {
        Node node = new Node();
        Cell cell = board.getCell(1, 1);

        cell.putNode(Side.TOP, node);

        Cell foundCell = board.getCellByNode(node);
        assertNotNull(foundCell);
        assertEquals(cell, foundCell);

        // Test with a node that doesn't exist
        Node nonExistentNode = new Node();
        assertNull(board.getCellByNode(nonExistentNode));
    }

    @Test
    void testGetCellByNodeWithInnerConnections() {
        Node node = new Node();
        Cell cell = board.getCell(1, 1);

        Set<Node> nodeSet = new HashSet<>(List.of(node));
        cell.getInnerConnections().add(nodeSet);

        Cell foundCell = board.getCellByNodeWithInnerConnections(node);
        assertNotNull(foundCell);
        assertEquals(cell, foundCell);

        Node nonExistentNode = new Node();
        assertNull(board.getCellByNodeWithInnerConnections(nonExistentNode));
    }

    @Test
    void testDisplay() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));
        board.placeFaceInTheCell(0,1,new HighwayFace());
        board.display();
        String output = outputStream.toString();
        assertTrue(output.contains(WHITE + "  " + RESET + " "+GREEN+ "  " + RESET ));
        assertTrue(true);
    }

    @Test
    void testGetNeighbour_InvalidSide() {
        assertNull(board.getNeighbour(0, 0, null));
    }


    @Test
    void testUpdateVertexWithSameSet() {
        Set<Node> nodeSet = new HashSet<>(List.of(new Node(), new Node()));
        List<Set<Node>> innerConnections = List.of(nodeSet);
        board.updateVertex(innerConnections);

        board.updateVertex(innerConnections);

        Graph<Set<Node>, Edge<Set<Node>>> graph = board.getGraph();
        assertTrue(graph.containsVertex(nodeSet));
    }

    @Test
    void testGetCellWithNullFace() {
        Cell cell = board.getCell(1, 1);
        assertNull(cell.getFace());
    }

    @Test
    void testPlacingFaceOnSameCell() {
        AbstractFace face1 = new HighwayFace();
        AbstractFace face2 = new HighwayFace();

        boolean result1 = board.placeFaceInTheCell(0, 1, face1);
        assertTrue(result1);

        boolean result2 = board.placeFaceInTheCell(0, 1, face2);
        assertFalse(result2);

        assertEquals(face1, board.getCell(0, 1).getFace());
    }

    @Test
    void testMultipleConnectionSetsWithSameNode() {
        Node sharedNode = new Node();

        Set<Node> set1 = new HashSet<>(Collections.singletonList(sharedNode));
        Set<Node> set2 = new HashSet<>(Arrays.asList(sharedNode, new Node()));

        List<Set<Node>> innerConnections = Arrays.asList(set1, set2);
        board.updateVertex(innerConnections);

        Graph<Set<Node>, Edge<Set<Node>>> graph = board.getGraph();
        assertTrue(graph.containsVertex(set1));
        assertTrue(graph.containsVertex(set2));
    }




}
