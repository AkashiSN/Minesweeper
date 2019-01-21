package minesweeper;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import minesweeper.modules.*;

import java.io.InputStream;

public class GameController implements TransitListener {
    private Game game;
    private final int IMAGE_SIZE = 50;
    @FXML private GridPane gridPane;
    @FXML private Label label;
    @FXML public Label timer;
    @FXML public Label name;

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
        gridPane.setOnMouseClicked(event ->{
            int x = (int) event.getX()/IMAGE_SIZE;
            int y = (int) event.getY()/IMAGE_SIZE;
            Coord coord = new Coord(x, y);
            if(event.getButton() == MouseButton.PRIMARY)
                game.pressPrimaryButton(coord);
            if(event.getButton() == MouseButton.SECONDARY)
                game.pressSecondaryButton(coord);
            if(event.getButton() == MouseButton.MIDDLE)
                game.start();
            label.setText(getMessage());
        });
        return size;
    }

    /**
     * restartPane()
     * グリッドを作成して空のイメージをセットする
     */
    private Coord restartPane(){
        for(Coord coord : Ranges.getAllCoords()){
            ImageView iv = new ImageView((Image) game.getBox(coord).image);
            gridPane.add(iv, coord.x, coord.y);
        }
        Coord size = new Coord(Ranges.getSize().x * IMAGE_SIZE,Ranges.getSize().y * IMAGE_SIZE);
        gridPane.setPrefSize(size.x,size.y);
        size = new Coord(size.x += 120, size.y += 120);
        return size;
    }

    /**
     * getMessage()
     * @return message String
     */
    private String getMessage() {
        switch (game.getState()) {
            case PLAYED:
                return "Think twice";
            case BOMBED:
                return "YOU LOSE!";
            case WINNER:
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
    private void updatePane(Coord coord){
        ImageView iv = new ImageView((Image) game.getBox(coord).image);
        gridPane.add(iv, coord.x, coord.y);
    }

}