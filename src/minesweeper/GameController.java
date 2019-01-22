package minesweeper;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import minesweeper.modules.*;

import java.io.InputStream;

public class GameController implements TransitListener {
    private Game game;
    private final int IMAGE_SIZE = 50;
    @FXML private GridPane gridPane;
    @FXML private Label label;
    @FXML private Label flags;
    @FXML public Label timer;
    @FXML public Label name;
    @FXML private Button back;

    /**
     * backFront()
     */
    @FXML void backFront() {
        game = null;
        Main.currentStage.setScene(Main.primaryScene);
    }

    /**
     * GameController()
     */
    public GameController(){
        game = new Game(MineSweeper.cols,MineSweeper.rows,MineSweeper.boms);
        game.start();
        setImages();
        TransitController.setTransitListener(this);
    }

    /**
     * initPane()
     */
    Coord initPane(){
        Coord size = restartPane();
        flags.setText(String.valueOf(game.getCountOfRemainFlags()));
        gridPane.setOnMouseClicked(event ->{
            int x = (int) event.getX()/IMAGE_SIZE;
            int y = (int) event.getY()/IMAGE_SIZE;
            Coord coord = new Coord(x, y);
            if(event.getButton() == MouseButton.PRIMARY) {
                if (event.isShiftDown()) {
                    game.pressSecondaryButton(coord);
                } else {
                    game.pressPrimaryButton(coord);
                }
            }
            label.setText(getMessage());
            flags.setText(String.valueOf(game.getCountOfRemainFlags()));
        });
        return size;
    }

    /**
     * restartPane()
     * グリッドを作成して空のイメージをセットする
     */
    private Coord restartPane(){
        for(Coord coord : Ranges.getAllCoords()){
            updatePane(coord);
        }
        Coord size = new Coord(Ranges.getSize().x * IMAGE_SIZE,Ranges.getSize().y * IMAGE_SIZE);
        gridPane.setPrefSize(size.x,size.y);
        return new Coord(size.x += 100, size.y += 120);
    }

    /**
     * getMessage()
     * @return message String
     */
    private String getMessage() {
        switch (game.getState()) {
            case PLAYED:
                return "Thinking";
            case BOMBED:
                back.setDisable(false);
                return "YOU LOSE!";
            case WINNER:
                back.setDisable(false);
                return "CONGRATULATION";
            default:
                return "Welcome";
        }

    }

    /**
     * setImages()
     * マス目に対して画像を配置する
     */
    private void setImages() {
        for (Box box : Box.values())
            if (box != Box.KNOANSWERED && box != Box.KANSWERED)
                box.image = getImage(box.name());
    }

    /**
     * getImage()
     * 渡された名前の画像を取得して返す
     * @param name 画像名
     * @return Image
     */
    private Image getImage(String name) {
        String filename = "img/" + name.toLowerCase() + ".png";
        InputStream inputStream = getClass().getResourceAsStream(filename);
        return new Image(inputStream);
    }

    /**
     * restart()
     */
    @Override
    public void restart(){
        restartPane();
    }

    /**
     * transitAndNotify()
     * @param coord
     */
    @Override
    public void transitAndNotify(Coord coord) {
        updatePane(coord);
    }

    /**
     * updatePane()
     * @param coord
     */
    private void updatePane(Coord coord) {
        Box box = game.getBox(coord);
        if (box == Box.KNOANSWERED) {
            Text text = new Text(game.getKanji(coord).kanji);
            StackPane stackPane = new StackPane();
            stackPane.setPrefSize(50,50);
            //stackPane.setStyle("-fx-background-color: #cccccc;");
            stackPane.getChildren().add(text);
            StackPane.setAlignment(text, Pos.CENTER);
            gridPane.setGridLinesVisible(true);
            gridPane.add(stackPane,coord.x,coord.y);
        } else if (box == Box.KANSWERED){
            ImageView iv = new ImageView((Image) box.image);
            gridPane.add(iv, coord.x, coord.y);
        }
    }
}