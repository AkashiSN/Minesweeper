package minesweeper.modules;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.io.IOException;

/**
 * class Game
 */
public class Game {
    private Bomb bomb; // 地雷の盤面
    private Flag flag; // フラグの盤面
    private GameState state; // ゲームの状態
    private int size; // マス目の総数
    private KanjiMap kanjiMap; // 漢字の盤面
    private int lifeValue; // ライフの値
    private Label life; // ライフラベル
    private Logger logger;

    /**
     * getState()
     * ゲームの状態を返す
     * @return state
     */
    public GameState getState() {
        return state;
    }

    /**
     * Game()
     * ゲームを初期化する
     * @param cols 横幅
     * @param rows 縦の長さ
     * @param bombs 地雷の数
     */
    public Game(int cols, int rows, int bombs) throws IOException {
        Ranges.setSize(new Coord(cols, rows));
        size = cols * rows;
        bomb = new Bomb(bombs);
        flag = new Flag();
        kanjiMap = new KanjiMap();
    }

    /**
     * reset()
     * リセットする
     */
    public void reset(){
        kanjiMap = null;
        flag = null;
        bomb = null;
    }

    /**
     * start()
     * ゲームを開始する
     */
    public void start() {
        Timer.start();
        flag.start();
        state = GameState.PLAYED;
    }

    /**
     * getBox()
     * 渡された座標のマスを返す
     * @param coord 座標
     * @return box
     */
    public Box getBox(Coord coord) {
        if (kanjiMap.get(coord) == Box.KNOANSWERED){
            return Box.KNOANSWERED;
        }
        if (flag.get(coord) == Box.OPENED)
            return bomb.get(coord);
        return flag.get(coord);
    }

    /**
     * setLogger()
     * 履歴表示リストをセット
     * @param l 履歴表示リスト
     */
    public void setLogger(Logger l){
        logger = l;
        flag.setLogger(logger);
    }

    /**
     * getKanji()
     * 渡された座標の漢字を返す
     * @param coord 座標
     * @return kanji
     */
    public Kanji getKanji(Coord coord){
        return kanjiMap.getKanji(coord);
    }

    /**
     * submitKanji()
     * 漢字が合っているかどうか
     * @param coord 座標
     * @param yomi 読み仮名
     * @return 正解かどうか
     */
    public boolean submitKanji(Coord coord, String yomi){
        return kanjiMap.submitKanji(coord, yomi);
    }

    /**
     * setKanjiStateToBox()
     * 渡された座標の漢字にBoxをセットする
     * @param coord 座標
     */
    public void setKanjiStateToBox(Coord coord, Box box){
        kanjiMap.setKanjiStateToBox(coord, box);
    }

    /**
     * setKanjiStackPane()
     * 渡された座標に漢字のPaneをセットする
     * @param coord 座標
     * @param stackPane StackPane
     */
    public void setKanjiStackPane(Coord coord, StackPane stackPane){
        kanjiMap.setKanjiStackPane(coord, stackPane);
    }

    /**
     * getKanjiStackPane()
     * 渡された座標のPaneを返す
     * @param coord 座標
     * @return StackPane
     */
    public StackPane getKanjiStackPane(Coord coord){
        return kanjiMap.getKanjiStackPane(coord);
    }

    /**
     * setImageView()
     * 渡された座標にImageViewをセットする
     * @param coord 座標
     * @param iv ImageView
     */
    public void setImageView(Coord coord, ImageView iv){
        kanjiMap.setImageView(coord,iv);
    }

    /**
     * getImageView()
     * 渡された座標のImageViewを返す
     * @param coord 座標
     * @return ImageView
     */
    public ImageView getImageView(Coord coord){
        return kanjiMap.getImageView(coord);
    }


    /**
     * pressPrimaryButton()
     * 左クリックが押された時
     * @param coord 左クリックが押された座標
     */
    public void pressPrimaryButton(Coord coord) {
        if (gameOver()) {
            return;
        }
        if (flag.getCountOfClosedBoxes() == size) { // 初めて開ける時
            bomb.start(coord); // 地雷を配置する
        }
        if (flag.get(coord) == Box.OPENED){
            return;
        }
        logger.addLog("Opening " + coord.show() );
        openBox(coord);
        checkWinner();
    }


    /**
     * pressSecondaryButton()
     * 右クリックが押された時
     * @param coord 右クリックが押された座標
     */
    public void pressSecondaryButton(Coord coord) {
        if (gameOver()) {
            return;
        }
        flag.toggleFlagedToBox(coord);
        checkWinner();
    }

    /**
     * checkWinner()
     * 閉じているマス目と地雷の総数が一致したらゲームクリア
     */
    private void checkWinner() {
        if (state == GameState.PLAYED){
            if (flag.getCountOfClosedBoxes() == bomb.getTotalBombs()) { // 地雷以外のマスを開けきった時
                state = GameState.WINNER;
                Timer.stop();
            }
            if (flag.getCountOfFlagedBoxes() == bomb.getTotalBombs()){ // 地雷の数だけフラグを立てた時
                int count = 0;
                for (Coord coord : Ranges.getAllCoords()){
                    if (bomb.get(coord) == Box.BOMB){
                        if (flag.get(coord) == Box.FLAGED) {
                            count++;
                        }
                    }
                }
                if (count == bomb.getTotalBombs()){ // フラグが全て地雷に立てられていた時
                    state = GameState.WINNER;
                    Timer.stop();
                }
            }
        }
    }

    /**
     * openBox()
     * 渡された座標のマスを開ける
     * @param coord 座標
     */
    private void openBox(Coord coord) {
        switch (flag.get(coord)) {
            case OPENED:
                setOpenedToClosedBoxesAroundNumber(coord);
                return;
            case FLAGED:
                return;
            case CLOSED:
                switch (bomb.get(coord)) {
                    case ZERO:
                        openBoxesAround(coord);
                        return;
                    case BOMB:
                        openBombs(coord);
                        return;
                    default:
                        kanjiMap.setKanjiStateToBox(coord, Box.KINCORRECTED);
                        flag.setOpenedToBox(coord);
                }
        }
    }

    /**
     * setOpenedToClosedBoxesAroundNumber()
     * 周辺のマスでフラグが立っていない所を開けていく
     * @param coord 座標
     */
    private void setOpenedToClosedBoxesAroundNumber(Coord coord) {
        if (bomb.get(coord) != Box.BOMB)
            if (flag.getCountOfFlageBoxesAround(coord) == bomb.get(coord).getNumber()) // 周辺のマスの地雷に全てフラグが立てられている時
                for (Coord around : Ranges.getCoordsAround(coord))
                    if (flag.get(around) == Box.CLOSED) // 残りのマスを開ける
                        openBox(around);
    }

    /**
     * openBombs()
     * 地雷を開けてしまった時
     * @param bombed 地雷の場所
     */
    private void openBombs(Coord bombed) {
        state = GameState.BOMBED;
        Timer.stop();
        flag.setBombedToBox(bombed);
        for (Coord coord : Ranges.getAllCoords()) {
            kanjiMap.setKanjiStateToBox(coord, Box.KINCORRECTED);
            if (bomb.get(coord) == Box.BOMB)
                flag.setOpenedToCloseBombBox(coord);
            else
                flag.setNoBombToFlagedSafeBox(coord);
        }
    }

    /**
     * openBoxesAround()
     * 周辺のマスを開けていく
     * @param coord 座標
     */
    private void openBoxesAround(Coord coord) {
        kanjiMap.setKanjiStateToBox(coord, Box.KINCORRECTED);
        flag.setOpenedToBox(coord);
        for (Coord around : Ranges.getCoordsAround(coord))
            openBox(around);
    }

    /**
     * gameOver()
     * ゲームオーバーかどうか
     * @return boolean
     */
    private boolean gameOver() {
        return state != GameState.PLAYED;
    }

    /**
     * getTotalBombs()
     * 地雷の数を返す
     * @return TotalBombs
     */
    public int getTotalBombs(){
        return bomb.getTotalBombs();
    }

    /**
     * getCountOfFlagedBoxes()
     * フラグの数を返す
     * @return CountOfFlagedBoxes
     */
    public int getCountOfFlagedBoxes(){
        return flag.getCountOfFlagedBoxes();
    }

    /**
     * setLifeValue()
     * ライフの値をセットする
     * @param l lifeの値
     */
    public void setLifeValue(int l){
        lifeValue = l;
        life.setText(String.valueOf(lifeValue));
    }

    /**
     * setLife()
     * ライフのラベルをセットする
     * @param l ライフのラベル
     */
    public void setLife(Label l){
        life = l;
    }

    /**
     * decrementLife()
     * ライフを１減らす
     */
    public void decrementLife(){
        lifeValue --;
        if(lifeValue <= 0){ // ライフがゼロになったら
            state = GameState.DIED;
            Timer.stop();
        }
        life.setText(String.valueOf(lifeValue));
    }
}
