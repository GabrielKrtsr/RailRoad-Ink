package scene.detection;

import board.Board;
import cell.Cell;
import javafx.scene.control.CheckBox;
import main.Player;
import main.Round;
import org.apache.batik.anim.dom.SAXSVGDocumentFactory;
import org.apache.batik.util.XMLResourceDescriptor;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import scene.game_load.BoardViewScene;
import util.Tuple;
import util.util_for_nodes.Side;

import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Class CheckBoxController
 */
public class CheckBoxController {

    private final BoardViewScene boardViewScene;

    private String boardSvg;

    public CheckBoxController(BoardViewScene boardViewScene){
        this.boardViewScene = boardViewScene;
        this.boardSvg = "";
    }

    public void handleCheckBoxAction(DetectionRegion detectionRegion, Round round, Player player, Board board , String boardSvg) {
        CheckBox falseRoutesCheckBox = detectionRegion.getFalseRouteCheckBox();
        CheckBox longestRoadCheckBox = detectionRegion.getLongestRoadCheckBox();
        CheckBox longestRailCheckBox = detectionRegion.getLongestRailCheckBox();
        CheckBox centerCellsCheckBox = detectionRegion.getCenterCellsCheckBox();
        CheckBox networksCheckBox = detectionRegion.getNetworksCheckBox();

        this.boardSvg = boardSvg;

        falseRoutesCheckBox.setOnAction(event -> {
            try {
                if (falseRoutesCheckBox.isSelected()) {
                    addFalseRoutes(round, player, board);
                } else {
                    deleteFalseRoutes(round, player, board);
                }
                updateBoardDisplay();
            } catch (IOException | TransformerException ex) {
                ex.printStackTrace();
            }
        });

        longestRoadCheckBox.setOnAction(event -> {
            try {
                if (longestRoadCheckBox.isSelected()) {
                    addLongestRoad(round, player, board);
                } else {
                    deleteLongestRoad(round, player, board);
                }
                updateBoardDisplay();
            } catch (IOException | TransformerException ex) {
                ex.printStackTrace();
            }
        });
        longestRailCheckBox.setOnAction(event -> {
            try {
                if (longestRailCheckBox.isSelected()) {
                    addLongestRail(round, player, board);
                } else {
                    deleteLongestRail(round, player, board);
                }
                updateBoardDisplay();
            } catch (IOException | TransformerException ex) {
                ex.printStackTrace();
            }
        });
        centerCellsCheckBox.setOnAction(event -> {
            try {
                if (centerCellsCheckBox.isSelected()) {
                    addCenterCells(round, player, board);
                } else {
                    deleteCenterCells(round, player, board);
                }
                updateBoardDisplay();
            } catch (IOException | TransformerException ex) {
                ex.printStackTrace();
            }
        });
        networksCheckBox.setOnAction(event -> {
            try {
                if (networksCheckBox.isSelected()) {
                    addNetworks(round, player, board);
                } else {
                    deleteNetworks(round, player, board);
                }
                updateBoardDisplay();
            } catch (IOException | TransformerException ex) {
                ex.printStackTrace();
            }
        });
    }

    private void updateBoardDisplay() {
        try {
            boardViewScene.updateBoardDisplay(boardSvg);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    private void updateElementStyle(NodeList elements, String styleModifier, boolean append) {
        for (int i = 0; i < elements.getLength(); i++) {
            Element element = (Element) elements.item(i);
            String style = element.getAttribute("style");
            if (append) {
                style += styleModifier;
            } else {
                style = style.replace(styleModifier, "");
            }
            element.setAttribute("style", style);
        }
    }

    private String getCellId(int x, int y) {
        return String.format("%c%d", (char) ('A' + y), x + 1);
    }

    private String serializeSVGDocument(Document svgDocument) throws TransformerException {
        StringWriter writer = new StringWriter();
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.transform(new DOMSource(svgDocument), new StreamResult(writer));
        return writer.toString();
    }

    private Document loadSVGDocument(String svgContent) throws IOException {
        String parser = XMLResourceDescriptor.getXMLParserClassName();
        SAXSVGDocumentFactory factory = new SAXSVGDocumentFactory(parser);
        return factory.createDocument(null, new java.io.StringReader(svgContent));
    }

    private void updateRoadStyle(String cellId, boolean isAdd) throws IOException, TransformerException {
        Document svgDocument = loadSVGDocument(boardSvg);

        NodeList cells = svgDocument.getElementsByTagName("g");
        for (int i = 0; i < cells.getLength(); i++) {
            Element cellElement = (Element) cells.item(i);
            if (cellElement.getAttribute("id").equals(cellId)) {
                NodeList childNodes = cellElement.getElementsByTagName("g");
                for (int j = 0; j < childNodes.getLength(); j++) {
                    Element tileElement = (Element) childNodes.item(j);
                    if ("road".equals(tileElement.getAttribute("class"))) {
                        NodeList paths = tileElement.getElementsByTagName("path");
                        NodeList lines = tileElement.getElementsByTagName("line");

                        updateElementStyle(paths, "; stroke:red;", isAdd);
                        updateElementStyle(lines, "; stroke:red;", isAdd);
                    }
                }
            }
        }

        boardSvg = serializeSVGDocument(svgDocument);
    }

    private void addRemoveCross(String cellId, Side side, boolean add) throws IOException, TransformerException {
        Document svgDocument = loadSVGDocument(boardSvg);

        NodeList cells = svgDocument.getElementsByTagName("g");
        for (int i = 0; i < cells.getLength(); i++) {
            Element cellElement = (Element) cells.item(i);
            if (cellElement.getAttribute("id").equals(cellId)) {
                NodeList childNodes = cellElement.getElementsByTagName("g");
                for (int j = 0; j < childNodes.getLength(); j++) {
                    Element tileElement = (Element) childNodes.item(j);
                    if ("tile".equals(tileElement.getAttribute("class"))) {
                        if (add) {
                            addCrossElement(svgDocument, side, tileElement);
                        } else {
                            removeCrossElement(side, tileElement);
                        }
                    }
                }
            }
        }

        boardSvg = serializeSVGDocument(svgDocument);
    }

    private void addCrossElement(Document svgDocument, Side side, Element tileElement) {
        int crossWidth = 100;
        int crossHeight = 100;
        String imagePath = getClass().getClassLoader().getResource("images/cross2.png").toExternalForm();

        Element cross = svgDocument.createElementNS("http://www.w3.org/2000/svg", "image");
        cross.setAttributeNS("http://www.w3.org/1999/xlink", "xlink:href", imagePath);
        cross.setAttribute("width", String.valueOf(crossWidth));
        cross.setAttribute("height", String.valueOf(crossHeight));

        switch (side) {
            case RIGHT:
                cross.setAttribute("x", String.valueOf(256 - crossHeight / 2));
                cross.setAttribute("y", String.valueOf(128 - crossHeight / 2));
                break;
            case TOP:
                cross.setAttribute("x", String.valueOf(128 - crossWidth / 2));
                cross.setAttribute("y", "-50");
                break;
            case BOTTOM:
                cross.setAttribute("x", String.valueOf(128 - crossWidth / 2));
                cross.setAttribute("y", String.valueOf(256 - crossHeight / 2));
                break;
            case LEFT:
                cross.setAttribute("x", "-50");
                cross.setAttribute("y", String.valueOf(128 - crossHeight / 2));
                break;
        }

        tileElement.appendChild(cross);
    }

    private void removeCrossElement(Side side, Element tileElement) {
        NodeList images = tileElement.getElementsByTagName("image");
        for (int k = 0; k < images.getLength(); k++) {
            Element imageElement = (Element) images.item(k);
            if (isMatchingCross(imageElement, side)) {
                tileElement.removeChild(imageElement);
                break;
            }
        }
    }

    private boolean isMatchingCross(Element element, Side side) {
        switch (side) {
            case RIGHT:
                return element.getAttribute("x").equals(String.valueOf(256 - 50)) &&
                        element.getAttribute("y").equals(String.valueOf(128 - 50));
            case TOP:
                return element.getAttribute("x").equals(String.valueOf(128 - 50)) &&
                        element.getAttribute("y").equals("-50");
            case BOTTOM:
                return element.getAttribute("x").equals(String.valueOf(128 - 50)) &&
                        element.getAttribute("y").equals(String.valueOf(256 - 50));
            case LEFT:
                return element.getAttribute("x").equals("-50") &&
                        element.getAttribute("y").equals(String.valueOf(128 - 50));
        }
        return false;
    }

    private void processRoads(Round round, Player player, Board board, boolean isAdd) throws IOException, TransformerException {
        List<Cell> cellList = round.getRoads().get(player);
        for (Cell cell : cellList) {
            Tuple<Integer, Integer> point = board.getPointOfCell(cell);
            String cellId = getCellId(point.getType1(), point.getType2());
            updateRoadStyle(cellId, isAdd);
        }
    }

    private void addLongestRoad(Round round, Player player, Board board) throws IOException, TransformerException {
        processRoads(round, player, board, true);
    }

    private void deleteLongestRoad(Round round, Player player, Board board) throws IOException, TransformerException {
        processRoads(round, player, board, false);
    }


    private void addLongestRail(Round round, Player player, Board board) throws IOException, TransformerException {
        processRails(round, player, board, true);
    }

    private void deleteLongestRail(Round round, Player player, Board board) throws IOException, TransformerException {
        processRails(round, player, board, false);
    }

    
    private void processRails(Round round, Player player, Board board, boolean isAdd) throws IOException, TransformerException {
        List<Cell> cellList = round.getRails().get(player);
        for (Cell cell : cellList) {
            Tuple<Integer, Integer> point = board.getPointOfCell(cell);
            String cellId = getCellId(point.getType1(), point.getType2());
            updateRailStyle(cellId, isAdd);
        }
    }

    private void processCenterCells(Round round, Player player, Board board, boolean isAdd) throws IOException, TransformerException {
        List<Cell> cellList = round.getCenterCells().get(player);
        for (Cell cell : cellList) {
            Tuple<Integer, Integer> point = board.getPointOfCell(cell);
            String cellId = getCellId(point.getType1(), point.getType2());
            updateCenterCellsStyle(cellId, isAdd);
        }
    }

    private void processNetworks(Round round, Player player, Board board, boolean isAdd) throws IOException, TransformerException {
        List<List<Cell>> networks = round.getNetworks().get(player);
        List<String> colors = new ArrayList<>(List.of("#FFD1DC", "#FFB347", "#FFDAC1", "#FF9980", "#FF6961", "#F49AC2", "#B39EB5", "#75E6DA", "#C5D5C5", "#FDFD96"));
        int colorIndex = 0;
        for (List<Cell> cellList : networks) {
            for (Cell cell : cellList) {
                Tuple<Integer, Integer> point = board.getPointOfCell(cell);
                String cellId = getCellId(point.getType1(), point.getType2());
                updateNetworksStyle(cellId, isAdd, colors.get(colorIndex));
            }
            colorIndex = (colorIndex + 1) % colors.size();
        }
    }


    private void addNetworks(Round round, Player player, Board board) throws IOException, TransformerException {
        processNetworks(round, player, board, true);
    }

    private void deleteNetworks(Round round, Player player, Board board) throws IOException, TransformerException {
        processNetworks(round, player, board, false);
    }
    
    

    private void addCenterCells(Round round, Player player, Board board) throws IOException, TransformerException {
        processCenterCells(round, player, board, true);
    }

    private void deleteCenterCells(Round round, Player player, Board board) throws IOException, TransformerException {
        processCenterCells(round, player, board, false);
    }

    private void updateCenterCellsStyle(String cellId, boolean isAdd) throws IOException, TransformerException {
        Document svgDocument = loadSVGDocument(boardSvg);

        NodeList cells = svgDocument.getElementsByTagName("g");
        for (int i = 0; i < cells.getLength(); i++) {
            Element cellElement = (Element) cells.item(i);
            if (cellElement.getAttribute("id").equals(cellId)) {
                NodeList childNodes = cellElement.getElementsByTagName("g");
                for (int j = 0; j < childNodes.getLength(); j++) {
                    Element tileElement = (Element) childNodes.item(j);
                    if ("tile".equals(tileElement.getAttribute("class"))) {
                        NodeList rects = tileElement.getElementsByTagName("rect");
                        for (int k = 0; k < rects.getLength(); k++) {
                            Element rectElement = (Element) rects.item(k);
                            String currentStyle = rectElement.getAttribute("style");
                            if (isAdd) {
                                if (currentStyle != null && !currentStyle.isEmpty()) {
                                    rectElement.setAttribute("style", currentStyle + " stroke: #5d16a2; stroke-width:10;");
                                } else {
                                    rectElement.setAttribute("style", "stroke: #5d16a2; stroke-width:10;");
                                }
                            } else {
                                if (currentStyle != null && !currentStyle.isEmpty()) {
                                    currentStyle = currentStyle.replace("stroke: #5d16a2;", "").replace("stroke-width:10;", "").trim();
                                    rectElement.setAttribute("style", currentStyle);
                                } else {
                                    rectElement.setAttribute("style", "stroke:none; stroke-width:0;");
                                }
                            }
                        }
                    }
                }
            }
        }

        boardSvg = serializeSVGDocument(svgDocument);
    }

    private void updateNetworksStyle(String cellId, boolean isAdd, String color) throws IOException, TransformerException {
        Document svgDocument = loadSVGDocument(boardSvg);

        NodeList cells = svgDocument.getElementsByTagName("g");
        for (int i = 0; i < cells.getLength(); i++) {
            Element cellElement = (Element) cells.item(i);
            if (cellElement.getAttribute("id").equals(cellId)) {
                NodeList childNodes = cellElement.getElementsByTagName("g");
                for (int j = 0; j < childNodes.getLength(); j++) {
                    Element tileElement = (Element) childNodes.item(j);
                    if ("tile".equals(tileElement.getAttribute("class"))) {
                        NodeList rects = tileElement.getElementsByTagName("rect");
                        for (int k = 0; k < rects.getLength(); k++) {
                            Element rectElement = (Element) rects.item(k);
                            String currentStyle = rectElement.getAttribute("style");
                            if (isAdd) {
                                if (currentStyle != null && !currentStyle.isEmpty()) {
                                    rectElement.setAttribute("style", currentStyle + " fill:" + color + ";");
                                } else {
                                    rectElement.setAttribute("style", "fill:" + color + ";");
                                }
                            } else {
                                if (currentStyle != null && !currentStyle.isEmpty()) {
                                    currentStyle = currentStyle.replaceAll("fill:[^;]*;", "").trim();
                                    rectElement.setAttribute("style", currentStyle);
                                } else {
                                    rectElement.setAttribute("style", "fill:white;");
                                }
                            }
                        }
                    }
                }
            }
        }

        boardSvg = serializeSVGDocument(svgDocument);
    }
    
    private void updateRailStyle(String cellId, boolean isAdd) throws IOException, TransformerException {
        Document svgDocument = loadSVGDocument(boardSvg);

        NodeList cells = svgDocument.getElementsByTagName("g");
        for (int i = 0; i < cells.getLength(); i++) {
            Element cellElement = (Element) cells.item(i);
            if (cellElement.getAttribute("id").equals(cellId)) {
                NodeList childNodes = cellElement.getElementsByTagName("g");
                for (int j = 0; j < childNodes.getLength(); j++) {
                    Element tileElement = (Element) childNodes.item(j);
                    if ("rail".equals(tileElement.getAttribute("class"))) {
                        NodeList paths = tileElement.getElementsByTagName("path");
                        NodeList lines = tileElement.getElementsByTagName("line");

                        updateElementStyle(paths, "; stroke:blue;", isAdd);
                        updateElementStyle(lines, "; stroke:blue;", isAdd);
                    }
                }
            }
        }

        boardSvg = serializeSVGDocument(svgDocument);
    }

    private void addFalseRoutes(Round round, Player player, Board board) throws IOException, TransformerException {
        Map<Cell, List<Side>> falseCellSides = round.getFalseRoutes().get(player);
        for (Cell cell : falseCellSides.keySet()) {
            Tuple<Integer, Integer> point = board.getPointOfCell(cell);
            String cellId = getCellId(point.getType1(), point.getType2());
            for (Side side : falseCellSides.get(cell)) {
                addRemoveCross(cellId, side, true);
            }
        }
    }

    private void deleteFalseRoutes(Round round, Player player, Board board) throws IOException, TransformerException {
        Map<Cell, List<Side>> falseCellSides = round.getFalseRoutes().get(player);
        for (Cell cell : falseCellSides.keySet()) {
            Tuple<Integer, Integer> point = board.getPointOfCell(cell);
            String cellId = getCellId(point.getType1(), point.getType2());
            for (Side side : falseCellSides.get(cell)) {
                addRemoveCross(cellId, side, false);
            }
        }
    }



}
