package scene;

import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import main.GameLoaderApp;
import main.GameParser;
import scene.animations.BirdAnimation;
import scene.game_load.GameData;
import scene.setting.SettingData;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Class StarterScene
 */
public class StarterScene {

    private GameLoaderApp app;
    private Scene scene;
    private GameData gameData;
    private SettingData settingData;

    /**
     * Initializes a new instance of the starter screen.
     *
     * @param application The instance of the main application
     * @param gameData The game data to use
     * @param settingData The application settings to use
     */
    public StarterScene(GameLoaderApp application ,GameData gameData,SettingData settingData) {
        this.app = application;
        this.gameData = gameData;
        this.settingData = settingData;
        if (gameData.getBoardSvgTemplate() == null) {
            gameData.setBoardSvgTemplate(loadSvgTemplate());
        }
        this.scene = createScene();
    }

    /**
     * Creates and configures the starter scene with all its user interface elements
     *
     * @return The configured JavaFX scene
     */
    private Scene createScene() {

        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setPrefSize(800, 800);
        anchorPane.getStylesheets().add(getClass().getResource(settingData.getStyleStarter()).toExternalForm());
        anchorPane.setId("background");


        Accordion accordion = new Accordion();
        AnchorPane.setLeftAnchor(accordion, 137.0);
        AnchorPane.setTopAnchor(accordion, 135.0);
        anchorPane.getChildren().add(accordion);


        VBox vBox = new VBox(20);
        vBox.setPrefSize(516, 419);
        vBox.setAlignment(javafx.geometry.Pos.CENTER);
        AnchorPane.setLeftAnchor(vBox, 167.0);
        AnchorPane.setTopAnchor(vBox, 168.0);


        Button button1 = new Button("GAME VIEW");
        button1.setPrefSize(380, 88);

        button1.setOnAction(event -> loadFile());

        Button button2 = new Button("PLAYER");
        button2.setPrefSize(380, 78);
        button2.setOnAction(event -> app.showAnalyticsScene());

        Button button3 = new Button("SETTINGS");
        button3.setPrefSize(380, 79);
        button3.setOnAction(event -> {
            settingData.setOldScene("Starter");
            app.showSettingsScene();
        });

        vBox.getChildren().addAll(button1, button2, button3);


        anchorPane.getChildren().add(vBox);


        BirdAnimation birdAnimation = new BirdAnimation(anchorPane);
        birdAnimation.setupAnimation();
        birdAnimation.startAnimation();


        return new Scene(anchorPane);
    }

    /**
     * Load a game file
     */
    private void loadFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Game File");
        File file = fileChooser.showOpenDialog(scene.getWindow());
        if (file != null) {
            try {
                gameData.clearSelections();
                GameParser parser = new GameParser(file.getAbsolutePath());// mod
                gameData.setAllGames(parser.getGames());
                app.showSelectionScene();
            } catch (FileNotFoundException e) {
                System.out.println("File not found");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Load the SVG
     * @return the SVG
     */
    private String loadSvgTemplate() {
        return "<svg width=\"2006\" height=\"2006\" viewBox=\"0 0 2006 2006\" xmlns=\"http://www.w3.org/2000/svg\" xmlns:svg=\"http://www.w3.org/2000/svg\" id=\"svgD\">\n" +
                "            <style>* {\n" +
                "               fill: none;\n" +
                "               stroke-width: 6;\n" +
                "               stroke-linecap: round;\n" +
                "               background-color : #f8d5c4;\n"+
                "             }\n" +
                "             .legend text {\n" +
                "               stroke: none;\n" +
                "               fill: black;\n" +
                "               font-family: Open Sans;\n" +
                "               font-size: 51;\n" +
                "               font-weight: 400;\n" +
                "               text-anchor: middle;\n" +
                "             }\n" +
                "             .map {\n" +
                "               stroke: black;\n" +
                "             }\n" +
                "             .grid * {\n" +
                "               stroke: #666;\n" +
                "             }\n" +
                "             .thin * {\n" +
                "               stroke-width: 4;\n" +
                "               stroke-dasharray: 0,8;\n" +
                "             }\n" +
                "             .border {\n" +
                "               fill: white;\n" +
                "             }\n" +
                "             .exit .indicator {\n" +
                "               fill: white;\n" +
                "               stroke: none;\n" +
                "             }</style>\n" +
                "           \n" +
                "            <g class=\"layer\">\n" +
                "             <title>Plateau</title>\n" +
                "             <g class=\"map\" id=\"svg_1\">\n" +
                "              <rect class=\"border\" height=\"1744\" id=\"svg_2\" rx=\"6\" width=\"1744\" x=\"135\" y=\"131\"/>\n" +
                "              <g class=\"grid\" id=\"svg_3\">\n" +
                "               <g class=\"thin\" id=\"svg_4\">\n" +
                "                <line id=\"svg_5\" x1=\"382\" x2=\"382\" y1=\"134\" y2=\"1872\"/>\n" +
                "                <line id=\"svg_6\" x1=\"138\" x2=\"1876\" y1=\"378\" y2=\"378\"/>\n" +
                "                <line id=\"svg_7\" x1=\"632\" x2=\"632\" y1=\"134\" y2=\"1872\"/>\n" +
                "                <line id=\"svg_8\" x1=\"138\" x2=\"1876\" y1=\"628\" y2=\"628\"/>\n" +
                "                <line id=\"svg_9\" x1=\"882\" x2=\"882\" y1=\"134\" y2=\"1872\"/>\n" +
                "                <line id=\"svg_10\" x1=\"138\" x2=\"1876\" y1=\"878\" y2=\"878\"/>\n" +
                "                <line id=\"svg_11\" x1=\"1132\" x2=\"1132\" y1=\"134\" y2=\"1872\"/>\n" +
                "                <line id=\"svg_12\" x1=\"138\" x2=\"1876\" y1=\"1128\" y2=\"1128\"/>\n" +
                "                <line id=\"svg_13\" x1=\"1382\" x2=\"1382\" y1=\"134\" y2=\"1872\"/>\n" +
                "                <line id=\"svg_14\" x1=\"138\" x2=\"1876\" y1=\"1378\" y2=\"1378\"/>\n" +
                "                <line id=\"svg_15\" x1=\"1632\" x2=\"1632\" y1=\"134\" y2=\"1872\"/>\n" +
                "                <line id=\"svg_16\" x1=\"138\" x2=\"1876\" y1=\"1628\" y2=\"1628\"/>\n" +
                "               </g>\n" +
                "\n" +
                "               <!-- TILES -->\n" +
                "               \n" +
                "              <g class=\"cell\" id=\"A1\" transform=\"translate(138,134)\"></g>\n" +
                "              <g class=\"cell\" id=\"B1\" transform=\"translate(382,134)\"></g>\n" +
                "              <g class=\"cell\" id=\"C1\" transform=\"translate(632,134)\"></g>\n" +
                "              <g class=\"cell\" id=\"D1\" transform=\"translate(882,134)\"></g>\n" +
                "              <g class=\"cell\" id=\"E1\" transform=\"translate(1132,134)\"></g>\n" +
                "              <g class=\"cell\" id=\"F1\" transform=\"translate(1382,134)\"></g>\n" +
                "              <g class=\"cell\" id=\"G1\" transform=\"translate(1632,134)\"></g>\n" +
                "\n" +
                "              <g class=\"cell\" id=\"A2\" transform=\"translate(138,378)\"></g>\n" +
                "              <g class=\"cell\" id=\"B2\" transform=\"translate(382,378)\"></g>\n" +
                "              <g class=\"cell\" id=\"C2\" transform=\"translate(632,378)\"></g>\n" +
                "              <g class=\"cell\" id=\"D2\" transform=\"translate(882,378)\"></g>\n" +
                "              <g class=\"cell\" id=\"E2\" transform=\"translate(1132,378)\"></g>\n" +
                "              <g class=\"cell\" id=\"F2\" transform=\"translate(1382,378)\"></g>\n" +
                "              <g class=\"cell\" id=\"G2\" transform=\"translate(1632,378)\"></g>\n" +
                "\n" +
                "              <g class=\"cell\" id=\"A3\" transform=\"translate(138,628)\"></g>\n" +
                "              <g class=\"cell\" id=\"B3\" transform=\"translate(382,628)\"></g>\n" +
                "              <g class=\"cell\" id=\"C3\" transform=\"translate(632,628)\"></g>\n" +
                "              <g class=\"cell\" id=\"D3\" transform=\"translate(882,628)\"></g>\n" +
                "              <g class=\"cell\" id=\"E3\" transform=\"translate(1132,628)\"></g>\n" +
                "              <g class=\"cell\" id=\"F3\" transform=\"translate(1382,628)\"></g>\n" +
                "              <g class=\"cell\" id=\"G3\" transform=\"translate(1632,628)\"></g>\n" +
                "\n" +
                "              <g class=\"cell\" id=\"A4\" transform=\"translate(138,878)\"></g>\n" +
                "              <g class=\"cell\" id=\"B4\" transform=\"translate(382,878)\"></g>\n" +
                "              <g class=\"cell\" id=\"C4\" transform=\"translate(632,878)\"></g>\n" +
                "              <g class=\"cell\" id=\"D4\" transform=\"translate(882,878)\"></g>\n" +
                "              <g class=\"cell\" id=\"E4\" transform=\"translate(1132,878)\"></g>\n" +
                "              <g class=\"cell\" id=\"F4\" transform=\"translate(1382,878)\"></g>\n" +
                "              <g class=\"cell\" id=\"G4\" transform=\"translate(1632,878)\"></g>\n" +
                "\n" +
                "              <g class=\"cell\" id=\"A5\" transform=\"translate(138,1128)\"></g>\n" +
                "              <g class=\"cell\" id=\"B5\" transform=\"translate(382,1128)\"></g>\n" +
                "              <g class=\"cell\" id=\"C5\" transform=\"translate(632,1128)\"></g>\n" +
                "              <g class=\"cell\" id=\"D5\" transform=\"translate(882,1128)\"></g>\n" +
                "              <g class=\"cell\" id=\"E5\" transform=\"translate(1132,1128)\"></g>\n" +
                "              <g class=\"cell\" id=\"F5\" transform=\"translate(1382,1128)\"></g>\n" +
                "              <g class=\"cell\" id=\"G5\" transform=\"translate(1632,1128)\"></g>\n" +
                "\n" +
                "              <g class=\"cell\" id=\"A6\" transform=\"translate(138,1378)\"></g>\n" +
                "              <g class=\"cell\" id=\"B6\" transform=\"translate(382,1378)\"></g>\n" +
                "              <g class=\"cell\" id=\"C6\" transform=\"translate(632,1378)\"></g>\n" +
                "              <g class=\"cell\" id=\"D6\" transform=\"translate(882,1378)\"></g>\n" +
                "              <g class=\"cell\" id=\"E6\" transform=\"translate(1132,1378)\"></g>\n" +
                "              <g class=\"cell\" id=\"F6\" transform=\"translate(1382,1378)\"></g>\n" +
                "              <g class=\"cell\" id=\"G6\" transform=\"translate(1632,1378)\"></g>\n" +
                "\n" +
                "              <g class=\"cell\" id=\"A7\" transform=\"translate(138,1628)\"></g>\n" +
                "              <g class=\"cell\" id=\"B7\" transform=\"translate(382,1628)\"></g>\n" +
                "              <g class=\"cell\" id=\"C7\" transform=\"translate(632,1628)\"></g>\n" +
                "              <g class=\"cell\" id=\"D7\" transform=\"translate(882,1628)\"></g>\n" +
                "              <g class=\"cell\" id=\"E7\" transform=\"translate(1132,1628)\"></g>\n" +
                "              <g class=\"cell\" id=\"F7\" transform=\"translate(1382,1628)\"></g>\n" +
                "              <g class=\"cell\" id=\"G7\" transform=\"translate(1632,1628)\"></g>\n" +
                "\n" +
                "               <!-- TILES -->\n" +
                "\n" +
                "              <rect height=\"750\" id=\"svg_17\" width=\"750\" x=\"632\" y=\"628\"/>\n" +
                "              </g>\n" +
                "              <g class=\"exits\" id=\"svg_18\">\n" +
                "               <g class=\"exit\" id=\"svg_19\">\n" +
                "                <path class=\"indicator\" d=\"m490,134l0,-6l-25,0l42,-42l42,42l-25,0l0,6l-34,0z\" id=\"svg_20\"/>\n" +
                "                <g class=\"road\" id=\"svg_21\">\n" +
                "                 <path d=\"m507,72l0,62\" id=\"svg_22\" stroke-dasharray=\"19,22\" stroke-dashoffset=\"-11\"/>\n" +
                "                 <line id=\"svg_23\" x1=\"490\" x2=\"490\" y1=\"72\" y2=\"134\"/>\n" +
                "                 <line id=\"svg_24\" x1=\"524\" x2=\"524\" y1=\"72\" y2=\"134\"/>\n" +
                "                </g>\n" +
                "               </g>\n" +
                "               <g class=\"exit\" id=\"svg_25\">\n" +
                "                <path class=\"indicator\" d=\"m990,134l0,-6l-25,0l42,-42l42,42l-25,0l0,6l-34,0z\" id=\"svg_26\"/>\n" +
                "                <g class=\"rail\" id=\"svg_27\">\n" +
                "                 <line id=\"svg_28\" x1=\"1007\" x2=\"1007\" y1=\"134\" y2=\"72\"/>\n" +
                "                 <line id=\"svg_29\" x1=\"990\" x2=\"1024\" y1=\"116.5\" y2=\"116.5\"/>\n" +
                "                 <line id=\"svg_30\" x1=\"990\" x2=\"1024\" y1=\"81.5\" y2=\"81.5\"/>\n" +
                "                </g>\n" +
                "               </g>\n" +
                "               <g class=\"exit\" id=\"svg_31\">\n" +
                "                <path class=\"indicator\" d=\"m1490,134l0,-6l-25,0l42,-42l42,42l-25,0l0,6l-34,0z\" id=\"svg_32\"/>\n" +
                "                <g class=\"road\" id=\"svg_33\">\n" +
                "                 <path d=\"m1507,72l0,62\" id=\"svg_34\" stroke-dasharray=\"19,22\" stroke-dashoffset=\"-11\"/>\n" +
                "                 <line id=\"svg_35\" x1=\"1490\" x2=\"1490\" y1=\"72\" y2=\"134\"/>\n" +
                "                 <line id=\"svg_36\" x1=\"1524\" x2=\"1524\" y1=\"72\" y2=\"134\"/>\n" +
                "                </g>\n" +
                "               </g>\n" +
                "               <g class=\"exit\" id=\"svg_37\" transform=\"rotate(-90, 138, 503)\">\n" +
                "                <path class=\"indicator\" d=\"m121,503l0,-6l-25,0l42,-42l42,42l-25,0l0,6l-34,0z\" id=\"svg_38\"/>\n" +
                "                <g class=\"rail\" id=\"svg_39\">\n" +
                "                 <line id=\"svg_40\" x1=\"138\" x2=\"138\" y1=\"503\" y2=\"441\"/>\n" +
                "                 <line id=\"svg_41\" x1=\"121\" x2=\"155\" y1=\"485.5\" y2=\"485.5\"/>\n" +
                "                 <line id=\"svg_42\" x1=\"121\" x2=\"155\" y1=\"450.5\" y2=\"450.5\"/>\n" +
                "                </g>\n" +
                "               </g>\n" +
                "               <g class=\"exit\" id=\"svg_43\" transform=\"rotate(-90, 138, 1003)\">\n" +
                "                <path class=\"indicator\" d=\"m121,1003l0,-6l-25,0l42,-42l42,42l-25,0l0,6l-34,0z\" id=\"svg_44\"/>\n" +
                "                <g class=\"road\" id=\"svg_45\">\n" +
                "                 <path d=\"m138,941l0,62\" id=\"svg_46\" stroke-dasharray=\"19,22\" stroke-dashoffset=\"-11\"/>\n" +
                "                 <line id=\"svg_47\" x1=\"121\" x2=\"121\" y1=\"941\" y2=\"1003\"/>\n" +
                "                 <line id=\"svg_48\" x1=\"155\" x2=\"155\" y1=\"941\" y2=\"1003\"/>\n" +
                "                </g>\n" +
                "               </g>\n" +
                "               <g class=\"exit\" id=\"svg_49\" transform=\"rotate(-90, 138, 1503)\">\n" +
                "                <path class=\"indicator\" d=\"m121,1503l0,-6l-25,0l42,-42l42,42l-25,0l0,6l-34,0z\" id=\"svg_50\"/>\n" +
                "                <g class=\"rail\" id=\"svg_51\">\n" +
                "                 <line id=\"svg_52\" x1=\"138\" x2=\"138\" y1=\"1503\" y2=\"1441\"/>\n" +
                "                 <line id=\"svg_53\" x1=\"121\" x2=\"155\" y1=\"1485.5\" y2=\"1485.5\"/>\n" +
                "                 <line id=\"svg_54\" x1=\"121\" x2=\"155\" y1=\"1450.5\" y2=\"1450.5\"/>\n" +
                "                </g>\n" +
                "               </g>\n" +
                "               <g class=\"exit\" id=\"svg_55\" transform=\"rotate(180, 507, 1872)\">\n" +
                "                <path class=\"indicator\" d=\"m490,1872l0,-6l-25,0l42,-42l42,42l-25,0l0,6l-34,0z\" id=\"svg_56\"/>\n" +
                "                <g class=\"road\" id=\"svg_57\">\n" +
                "                 <path d=\"m507,1810l0,62\" id=\"svg_58\" stroke-dasharray=\"19,22\" stroke-dashoffset=\"-11\"/>\n" +
                "                 <line id=\"svg_59\" x1=\"490\" x2=\"490\" y1=\"1810\" y2=\"1872\"/>\n" +
                "                 <line id=\"svg_60\" x1=\"524\" x2=\"524\" y1=\"1810\" y2=\"1872\"/>\n" +
                "                </g>\n" +
                "               </g>\n" +
                "               <g class=\"exit\" id=\"svg_61\" transform=\"rotate(180, 1007, 1872)\">\n" +
                "                <path class=\"indicator\" d=\"m990,1872l0,-6l-25,0l42,-42l42,42l-25,0l0,6l-34,0z\" id=\"svg_62\"/>\n" +
                "                <g class=\"rail\" id=\"svg_63\">\n" +
                "                 <line id=\"svg_64\" x1=\"1007\" x2=\"1007\" y1=\"1872\" y2=\"1810\"/>\n" +
                "                 <line id=\"svg_65\" x1=\"990\" x2=\"1024\" y1=\"1854.5\" y2=\"1854.5\"/>\n" +
                "                 <line id=\"svg_66\" x1=\"990\" x2=\"1024\" y1=\"1819.5\" y2=\"1819.5\"/>\n" +
                "                </g>\n" +
                "               </g>\n" +
                "               <g class=\"exit\" id=\"svg_67\" transform=\"rotate(180, 1507, 1872)\">\n" +
                "                <path class=\"indicator\" d=\"m1490,1872l0,-6l-25,0l42,-42l42,42l-25,0l0,6l-34,0z\" id=\"svg_68\"/>\n" +
                "                <g class=\"road\" id=\"svg_69\">\n" +
                "                 <path d=\"m1507,1810l0,62\" id=\"svg_70\" stroke-dasharray=\"19,22\" stroke-dashoffset=\"-11\"/>\n" +
                "                 <line id=\"svg_71\" x1=\"1490\" x2=\"1490\" y1=\"1810\" y2=\"1872\"/>\n" +
                "                 <line id=\"svg_72\" x1=\"1524\" x2=\"1524\" y1=\"1810\" y2=\"1872\"/>\n" +
                "                </g>\n" +
                "               </g>\n" +
                "               <g class=\"exit\" id=\"svg_73\" transform=\"rotate(90, 1876, 503)\">\n" +
                "                <path class=\"indicator\" d=\"m1859,503l0,-6l-25,0l42,-42l42,42l-25,0l0,6l-34,0z\" id=\"svg_74\"/>\n" +
                "                <g class=\"rail\" id=\"svg_75\">\n" +
                "                 <line id=\"svg_76\" x1=\"1876\" x2=\"1876\" y1=\"503\" y2=\"441\"/>\n" +
                "                 <line id=\"svg_77\" x1=\"1859\" x2=\"1893\" y1=\"485.5\" y2=\"485.5\"/>\n" +
                "                 <line id=\"svg_78\" x1=\"1859\" x2=\"1893\" y1=\"450.5\" y2=\"450.5\"/>\n" +
                "                </g>\n" +
                "               </g>\n" +
                "               <g class=\"exit\" id=\"svg_79\" transform=\"rotate(90, 1876, 1003)\">\n" +
                "                <path class=\"indicator\" d=\"m1859,1003l0,-6l-25,0l42,-42l42,42l-25,0l0,6l-34,0z\" id=\"svg_80\"/>\n" +
                "                <g class=\"road\" id=\"svg_81\">\n" +
                "                 <path d=\"m1876,941l0,62\" id=\"svg_82\" stroke-dasharray=\"19,22\" stroke-dashoffset=\"-11\"/>\n" +
                "                 <line id=\"svg_83\" x1=\"1859\" x2=\"1859\" y1=\"941\" y2=\"1003\"/>\n" +
                "                 <line id=\"svg_84\" x1=\"1893\" x2=\"1893\" y1=\"941\" y2=\"1003\"/>\n" +
                "                </g>\n" +
                "               </g>\n" +
                "               <g class=\"exit\" id=\"svg_85\" transform=\"rotate(90, 1876, 1503)\">\n" +
                "                <path class=\"indicator\" d=\"m1859,1503l0,-6l-25,0l42,-42l42,42l-25,0l0,6l-34,0z\" id=\"svg_86\"/>\n" +
                "                <g class=\"rail\" id=\"svg_87\">\n" +
                "                 <line id=\"svg_88\" x1=\"1876\" x2=\"1876\" y1=\"1503\" y2=\"1441\"/>\n" +
                "                 <line id=\"svg_89\" x1=\"1859\" x2=\"1893\" y1=\"1485.5\" y2=\"1485.5\"/>\n" +
                "                 <line id=\"svg_90\" x1=\"1859\" x2=\"1893\" y1=\"1450.5\" y2=\"1450.5\"/>\n" +
                "                </g>\n" +
                "               </g>\n" +
                "              </g>\n" +
                "              <g class=\"legend\" id=\"svg_91\">\n" +
                "               <text id=\"svg_92\" x=\"257\" y=\"1978\">A</text>\n" +
                "               <text id=\"svg_93\" x=\"257\" y=\"28\">A</text>\n" +
                "               <text id=\"svg_94\" x=\"1982\" y=\"1753\">7</text>\n" +
                "               <text id=\"svg_95\" x=\"32\" y=\"1753\">7</text>\n" +
                "               <text id=\"svg_96\" x=\"507\" y=\"1978\">B</text>\n" +
                "               <text id=\"svg_97\" x=\"507\" y=\"28\">B</text>\n" +
                "               <text id=\"svg_98\" x=\"1982\" y=\"1503\">6</text>\n" +
                "               <text id=\"svg_99\" x=\"32\" y=\"1503\">6</text>\n" +
                "               <text id=\"svg_100\" x=\"757\" y=\"1978\">C</text>\n" +
                "               <text id=\"svg_101\" x=\"757\" y=\"28\">C</text>\n" +
                "               <text id=\"svg_102\" x=\"1982\" y=\"1253\">5</text>\n" +
                "               <text id=\"svg_103\" x=\"32\" y=\"1253\">5</text>\n" +
                "               <text id=\"svg_104\" x=\"1007\" y=\"1978\">D</text>\n" +
                "               <text id=\"svg_105\" x=\"1007\" y=\"28\">D</text>\n" +
                "               <text id=\"svg_106\" x=\"1982\" y=\"1003\">4</text>\n" +
                "               <text id=\"svg_107\" x=\"32\" y=\"1003\">4</text>\n" +
                "               <text id=\"svg_108\" x=\"1257\" y=\"1978\">E</text>\n" +
                "               <text id=\"svg_109\" x=\"1257\" y=\"28\">E</text>\n" +
                "               <text id=\"svg_110\" x=\"1982\" y=\"753\">3</text>\n" +
                "               <text id=\"svg_111\" x=\"32\" y=\"753\">3</text>\n" +
                "               <text id=\"svg_112\" x=\"1507\" y=\"1978\">F</text>\n" +
                "               <text id=\"svg_113\" x=\"1507\" y=\"28\">F</text>\n" +
                "               <text id=\"svg_114\" x=\"1982\" y=\"503\">2</text>\n" +
                "               <text id=\"svg_115\" x=\"32\" y=\"503\">2</text>\n" +
                "               <text id=\"svg_116\" x=\"1757\" y=\"1978\">G</text>\n" +
                "               <text id=\"svg_117\" x=\"1757\" y=\"28\">G</text>\n" +
                "               <text id=\"svg_118\" x=\"1982\" y=\"253\">1</text>\n" +
                "               <text id=\"svg_119\" x=\"32\" y=\"253\">1</text>\n" +
                "              </g>\n" +
                "             </g>\n" +
                "            </g>\n" +
                "           </svg>\n" +
                "\n";
    }


    public Scene getScene() {
        return scene;
    }
}