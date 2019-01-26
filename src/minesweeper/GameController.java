package minesweeper;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import minesweeper.modules.*;
import minesweeper.solver.Solver;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * class GameController
 * ゲーム画面のコントローラー
 */
public class GameController implements TransitListener {

    private Game game; // ゲームを保持
    private Solver solver; // ソルバー
    private Logger logger; // 履歴表示
    private boolean autoMode;
    private final int IMAGE_SIZE = 50; // マスのサイズ
    @FXML private GridPane gridPane; // 盤面

    /**
     * getGame()
     * ゲームインスタンスを返す
     * @return game
     */
    Game getGame(){
        return game;
    }

    /**
     * getSolver()
     * ソルバーのインスタンスを返す
     * @return solver
     */
    Solver getSolver(){
        return solver;
    }

    /**
     * setFlagsBomb()
     * ラベルをセットする
     * @param l フラグの数と地雷の数のラベル
     */
    void setFlagsBomb(Label l){
        // フラグの数と地雷の数
        game.setFlagsBomb(l);
        game.setCountOfRemainFlags();
    }

    /**
     * setLogger()
     * 履歴表示のリストをセットする
     * @param l 履歴のリスト
     */
    void setLogger(ListView<String> l){
        logger = new Logger(l);
        game.setLogger(logger);
        solver.setLogger(logger);
    }

    /**
     * GameController()
     * ゲームを初期化
     */
    public GameController() throws IOException {
        game = new Game(MineSweeper.cols, MineSweeper.rows, MineSweeper.boms);
        game.start();
        setImages();
        TransitController.setTransitListener(this);
    }

    /**
     * initPane()
     * 盤面を表示してイベントを登録する
     */
    Coord initPane(boolean a){
        autoMode = a;
        if (autoMode){
            solver = new Solver(game);
        }
        Coord size = initGrid();
        gridPane.setGridLinesVisible(true);

        gridPane.setOnMouseClicked(event -> { // マス目をクリックした時のイベントハンドラ
            if (!autoMode && game.getState() == GameState.PLAYED) { // 自動プレイではなくプレイ中の時
                int x = (int) event.getX() / IMAGE_SIZE;
                int y = (int) event.getY() / IMAGE_SIZE;
                Coord coord = new Coord(x, y);
                mouseClickedEvent(event.getButton(), event.isShiftDown(), coord);
            }
        });
        return size;
    }

    /**
     * startAuto()
     * ソルバーを起動する
     */
    void startAuto() {
        for (Coord coord : Ranges.getAllCoords()){ // 自動プレイのときは漢字はすべて不正解とする
            game.setKanjiStateToBox(coord, Box.KINCORRECTED);
        }

        solver.start();
    }

    /**
     * mouseClickedEvent()
     * マウスがクリックされたときのイベントハンドラー
     * @param mb マウスボタン
     * @param isShift シフトが押されているか
     * @param coord 座標
     */
    private void mouseClickedEvent(MouseButton mb, boolean isShift, Coord coord) {
        if (mb == MouseButton.PRIMARY ) { // 左クリックの時
            if (game.getBox(coord) == Box.KNOANSWERED) { // 漢字がまだ回答されていない時
                Kanji kanji = game.getKanji(coord);
                try {
                    openKanjiWindow(coord, kanji.kanji, kanji.imi, isShift); // 漢字の回答ウィンドウを表示する
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else { // 漢字が回答済み
                if (isShift) { // シフトが押されていたか
                    game.pressSecondaryButton(coord);
                } else {
                    if (game.getBox(coord) == Box.CLOSED) {
                        game.pressPrimaryButton(coord);
                    }
                }
                logger.addLog(getMessage());
            }
        }
    }

    /**
     * openKanjiWindow()
     * 漢字の回答ウィンドウを開く
     * @param kanji String 漢字
     * @throws IOException fxmlのロードエラー
     */
    private void openKanjiWindow(Coord coord, String kanji,String imi, boolean isShift) throws IOException {
        Stage kanjiWindow = new Stage();
        kanjiWindow.initModality(Modality.APPLICATION_MODAL); // モーダルウインドウに設定
        kanjiWindow.initOwner(Main.currentStage); // オーナーを設定
        kanjiWindow.setTitle("読み仮名を答えてね");

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/kanji.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        scene.setOnKeyPressed((KeyEvent e) -> {
            if (e.getCode() == KeyCode.ESCAPE) {
                scene.getWindow().hide();
            }
        });
        kanjiWindow.setScene(scene);
        kanjiWindow.show();
        kanjiWindow.showingProperty().addListener((observable, oldValue, newValue) -> { // 漢字の回答ウィンドウが閉じた時のイベントハンドラ
            if (oldValue && !newValue) {
                if (game.getBox(coord) != Box.KNOANSWERED){ // 回答が正解だった時
                    if (isShift) { // シフトが押されていたか
                        game.pressSecondaryButton(coord);
                    } else {
                        game.pressPrimaryButton(coord);
                    }
                }
                logger.addLog(getMessage());
            }
        });
        final KanjiController kanjiController = fxmlLoader.getController(); // 回答ウィンドウのコントローラー
        kanjiController.init(kanji, imi, coord, kanjiWindow,game, isShift);
    }

    /**
     * initGrid()
     * グリッドを作成して漢字をセットする
     * @return size Coord ウィンドウサイズ
     */
    private Coord initGrid(){
        for(Coord coord : Ranges.getAllCoords()){
            Text text = new Text(game.getKanji(coord).kanji);
            text.setFont(Font.loadFont("file:resources/fonts/ipam.ttf",20));

            StackPane stackPane = new StackPane();
            stackPane.setPrefSize(IMAGE_SIZE,IMAGE_SIZE);
            stackPane.getChildren().add(text);
            StackPane.setAlignment(text, Pos.CENTER);

            game.setKanjiStackPane(coord,stackPane);

            gridPane.add(stackPane,coord.x,coord.y);
        }
        Coord size = new Coord(Ranges.getSize().x * IMAGE_SIZE,Ranges.getSize().y * IMAGE_SIZE);
        gridPane.setPrefSize(size.x,size.y);
        return new Coord(size.x += 20, size.y += 20);
    }

    /**
     * getMessage()
     * 表示するメッセージを選択する
     * @return message String
     */
    private String getMessage() {
        if (game.getState() != GameState.PLAYED) {
            switch (game.getState()) {
                case BOMBED:
                    openClearWindow("YOU LOSE !");
                    return "YOU LOSE !";
                case DIED:
                    openClearWindow("YOU DIED !");
                    return "YOU DIED";
                case WINNER:
                    openClearWindow("CONGRATULATION !");
                    return "CONGRATULATION !!";
            }
        }
        return "";
    }

    /**
     * openClearWindow()
     * クリアウィンドウを開く
     * @param s 表示する文字
     */
    private void openClearWindow(String s){
        Stage clearWindow = new Stage();
        clearWindow.initModality(Modality.APPLICATION_MODAL); // モーダルウインドウに設定
        clearWindow.initOwner(Main.currentStage); // オーナーを設定
        Main.currentStage.setTitle(s);
        clearWindow.setTitle(s);

        Text text = new Text(s);

        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(text);
        StackPane.setAlignment(text, Pos.CENTER);

        Scene scene = new Scene(stackPane,200,100);

        scene.setOnKeyPressed((KeyEvent e) -> scene.getWindow().hide());
        clearWindow.setScene(scene);
        clearWindow.show();
    }

    /**
     * setImages()
     * Boxに対して画像をセットする
     */
    private void setImages() throws FileNotFoundException {
        for (Box box : Box.values()) {
            if (box != Box.KNOANSWERED && box != Box.KINCORRECTED && box != Box.KCORRECTED) {
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
    private Image getImage(String name) throws FileNotFoundException {
        String filename = "resources/images/" + name.toLowerCase() + ".png";
        InputStream inputStream = new FileInputStream(filename);
        return new Image(inputStream);
    }

    /**
     * updatePane()
     * 画面の描画を更新する
     * @param coord 座標
     */
    private void updatePane(Coord coord) {
        if (game.getBox(coord) != Box.KNOANSWERED) { // 漢字が解かれた時
            Box box = game.getBox(coord);
            gridPane.getChildren().remove(game.getImageView(coord));
            gridPane.getChildren().remove(game.getKanjiStackPane(coord)); // 漢字を消す
            ImageView iv = new ImageView((Image) box.image); // 画像をセット
            game.setImageView(coord,iv);
            gridPane.add(iv, coord.x, coord.y);
        }
    }

    /**
     * transitAndNotify()
     * moduleの中から呼び出す用
     * @param coord 座標
     */
    @Override
    public void transitAndNotify(Coord coord) {
        updatePane(coord);
    }
}