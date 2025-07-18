package scene.boardNavigation;

import board.Board;
import face.AbstractFace;
import javafx.scene.web.WebView;
import main.Game;
import main.Player;
import main.Round;
import scene.game_load.BoardViewScene;
import scene.game_load.GameData;
import scene.detection.CheckBoxController;
import scene.detection.DetectionRegion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class BoardDisplayController
 */
public class BoardDisplayController {
    private GameData gameData;
    private WebView boardWebView;
    private CheckBoxController checkBoxController;
    private DetectionRegion detectionRegion;


    /**
     * Constructs a new board display controller
     *
     * @param gameData the game data
     * @param b the board
     * @param d the detection region
     */
    public BoardDisplayController(GameData gameData, BoardViewScene b, DetectionRegion d) {
        this.boardWebView = new WebView();
        boardWebView.setPrefSize(800, 600);
        boardWebView.setId("boardContainer");
        this.gameData = gameData;
        this.checkBoxController = new CheckBoxController(b);
        this.detectionRegion = d;
    }

    /**
     * Displays the current board state in the WebView
     */
    public void displayBoard() {
        try {
            String boardWithFaces = generateBoardSvg();
            updateBoardDisplay(boardWithFaces);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Generates an SVG representation of the board with faces placed according to the current game state
     *
     * @return an SVG string representation of the board with faces
     */
    private String generateBoardSvg() {
        String playerId = gameData.getCurrentPlayerId();
        String gameId = gameData.getCurrentGameId();
        int roundIndex = gameData.getCurrentRound();
        if (playerId == null || gameId == null || roundIndex < 0) {
            return gameData.getBoardSvgTemplate();
        }
        try {
            String boardSvg = gameData.getBoardSvgTemplate();
            Game game = gameData.getAllGames().get(gameId);
            if (game == null) {
                return gameData.getBoardSvgTemplate();
            }
            List<Round> rounds = game.getRounds();
            if (rounds == null || roundIndex >= rounds.size()) {
                return gameData.getBoardSvgTemplate();
            }
            Round round = rounds.get(roundIndex);
            if (round == null) {
                return gameData.getBoardSvgTemplate();
            }
            List<Player> players = new ArrayList<>(round.getPlayerBoardMap().keySet());
            Map<String, Player> clonedPlayers = new HashMap<>();
            for (Player player : players) {
                clonedPlayers.put(player.getId(), player);
            }
            if (clonedPlayers == null || !clonedPlayers.containsKey(playerId)) {
                return gameData.getBoardSvgTemplate();
            }
            Player player = clonedPlayers.get(playerId);
            if (player == null) {
                return gameData.getBoardSvgTemplate();
            }
            Board board = round.getPlayerBoardMap().get(player);
            if (board == null) {
                return gameData.getBoardSvgTemplate();
            }
            for (int x = 0; x < 7; x++) {
                for (int y = 0; y < 7; y++) {
                    if (board.getCell(x, y) != null && board.getCell(x, y).getFace() != null) {
                        AbstractFace face = board.getCell(x, y).getFace();
                        String cellId = String.format("%c%d", (char)('A' + y), x + 1);
                        boardSvg = placeFaceInSvg(boardSvg, face, cellId);
                    }
                }
            }
            checkBoxController.handleCheckBoxAction(detectionRegion,round,player,board,boardSvg);
            return boardSvg;
        } catch (Exception e) {
            e.printStackTrace();
            return gameData.getBoardSvgTemplate();
        }
    }

    /**
     * Places a face SVG into the specified cell of the board SVG.
     *
     * @param svgContent the current SVG content of the board
     * @param face the face to place in the cell
     * @param cellId the ID of the cell
     * @return the SVG
     */
    private String placeFaceInSvg(String svgContent, AbstractFace face, String cellId) {
        try {
            String faceSvg = face.getImageSvg();
            if (faceSvg == null || faceSvg.isEmpty()) {
                return svgContent;
            }
            String cellStart = "<g class=\"cell\" id=\"" + cellId + "\"";
            int cellIndex = svgContent.indexOf(cellStart);

            if (cellIndex == -1) {
                return svgContent;
            }
            int cellEndIndex = svgContent.indexOf("</g>", cellIndex) + 4;
            String cellContent = svgContent.substring(cellIndex, cellEndIndex);
            String newCellContent = cellContent.substring(0, cellContent.length() - 4) + faceSvg + "</g>";
            return svgContent.substring(0, cellIndex) + newCellContent + svgContent.substring(cellEndIndex);
        } catch (Exception e) {
            e.printStackTrace();
            return svgContent;
        }
    }

    /**
     * Updates the WebView display with the provided SVG content
     *
     * @param svgContent the SVG content
     */
    public void updateBoardDisplay(String svgContent) {
        if (svgContent == null) {
            svgContent = gameData.getBoardSvgTemplate();
        }
        String styledSvgContent = svgContent.replaceFirst("<svg", "<svg style='width: 100%; height: 100%;'");
        boardWebView.getEngine().loadContent(styledSvgContent, "image/svg+xml");
    }

    /**
     * Gets the WebView component that displays the board
     *
     * @return the WebView
     */
    public WebView getBoardWebView() {
        return boardWebView;
    }
}