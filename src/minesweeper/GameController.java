package minesweeper;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import minesweeper.modules.*;

import java.io.IOException;
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
     * backToFront()
     * スタート画面に戻る
     */
    @FXML void backToFront() {
        game = null;
        Main.currentStage.setScene(Main.primaryScene);
    }

    /**
     * GameController()
     * ゲームを初期化して画像を準備する
     */
    public GameController(){
        game = new Game(MineSweeper.cols,MineSweeper.rows,MineSweeper.boms);
        game.start();
        setImages();
        TransitController.setTransitListener(this);
    }

    /**
     * initPane()
     * 盤面を表示してイベントを登録する
     */
    Coord initPane(){
        Coord size = restartPane();
        gridPane.setGridLinesVisible(true);

        flags.setText(String.valueOf(game.getCountOfRemainFlags()));
        gridPane.setOnMouseClicked(event ->{
            int x = (int) event.getX()/IMAGE_SIZE;
            int y = (int) event.getY()/IMAGE_SIZE;
            Coord coord = new Coord(x, y);
            if(event.getButton() == MouseButton.PRIMARY) {
                if (KanjiMap.get(coord) == Box.KNOANSWERED) {
                    Kanji kanji = KanjiMap.getKanji(coord);
                    try {
                        openKanjiWindow(coord, kanji.kanji,event.isShiftDown());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        return size;
    }

    /**
     * openKanjiWindow()
     * 漢字回答ウィンドウを開く
     * @param kanji 漢字
     * @throws IOException ロードエラー
     */
    private void openKanjiWindow(Coord coord, String kanji,boolean isShift) throws IOException {
        Stage kanjiWindow = new Stage();
        kanjiWindow.initModality(Modality.APPLICATION_MODAL); // モーダルウインドウに設定
        kanjiWindow.initOwner(Main.currentStage); // オーナーを設定

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/kanji.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        kanjiWindow.setScene(scene);
        kanjiWindow.show();
        kanjiWindow.showingProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue && !newValue) {
                if (KanjiMap.get(coord) == Box.KANSWERED){
                    if (isShift) {
                        game.pressSecondaryButton(coord);
                    } else {
                        game.pressPrimaryButton(coord);
                    }
                }
                label.setText(getMessage());
                flags.setText(String.valueOf(game.getCountOfRemainFlags()));
            }
        });
        final KanjiController controller = fxmlLoader.getController();
        controller.setKanji(kanji);
        controller.setCoord(coord);
    }

    /**
     * restartPane()
     * グリッドを作成して空のイメージをセットする
     */
    private Coord restartPane(){
        for(Coord coord : Ranges.getAllCoords()){
            Text text = new Text(KanjiMap.getKanji(coord).kanji);

            StackPane stackPane = new StackPane();
            stackPane.setPrefSize(50,50);
            stackPane.getChildren().add(text);
            StackPane.setAlignment(text, Pos.CENTER);

            KanjiMap.setKanjiStackPane(coord,stackPane);

            gridPane.add(stackPane,coord.x,coord.y);
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
        for (Box box : Box.values()) {
            if (box != Box.KNOANSWERED && box != Box.KANSWERED) {
                box.image = getImage(box.name());
            }
        }
    }

    /**
     * getImage()
     * 渡された名前の画像を取得して返す
     * @param name 画像名
     * @return Image
     */
    private Image getImage(String name) {
        String filename = "images/" + name.toLowerCase() + ".png";
        InputStream inputStream = getClass().getResourceAsStream(filename);
        return new Image(inputStream);
    }


    /**
     * restart()
     * クラス外から呼び出す
     */
    @Override
    public void restart(){
        restartPane();
    }

    /**
     * transitAndNotify()
     * クラス外から呼び出す
     * @param coord 座標
     */
    @Override
    public void transitAndNotify(Coord coord) {
        updatePane(coord);
    }

    /**
     * updatePane()
     * 画面の描画を更新する
     * @param coord 座標
     */
    private void updatePane(Coord coord) {
        if (KanjiMap.get(coord) != Box.KNOANSWERED) { // 漢字が解かれた時
            Box box = game.getBox(coord);
            gridPane.getChildren().remove(KanjiMap.getKanjiStackPane(coord));
            ImageView iv = new ImageView((Image) box.image);
            gridPane.add(iv, coord.x, coord.y);
        }
    }
}