package minesweeper.modules;

/**
 * class Game
 */
public class Game {
    private Bomb bomb; // 地雷の盤面
    private Flag flag; // フラグの盤面
    private GameState state; // ゲームの状態
    private int size; // マス目の総数

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
    public Game(int cols, int rows, int bombs) {
        Ranges.setSize(new Coord(cols, rows));
        size = cols * rows;
        bomb = new Bomb(bombs);
        flag = new Flag();
    }

    /**
     * start()
     */
    public void start() {
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
        if (flag.get(coord) == Box.OPENED)
            return bomb.get(coord);
        return flag.get(coord);
    }

    /**
     * pressPrimaryButton()
     * 左クリックが押された時
     * @param coord 左クリックが押された座標
     */
    public void pressPrimaryButton(Coord coord) {
        if (gameOver()) return;
        if (flag.getCountOfClosedBoxes() == size) { // 初めて開ける時
            bomb.start(coord);
        }
        openBox(coord);
        checkWinner();
    }


    /**
     * pressSecondaryButton()
     * 右クリックが押された時
     * @param coord 右クリックが押された座標
     */
    public void pressSecondaryButton(Coord coord) {
        if (gameOver()) return;
        flag.toggleFlagedToBox(coord);
    }

    /**
     * checkWinner()
     * 閉じているマス目と地雷の総数が一致したらゲームクリア
     */
    private void checkWinner() {
        if (state == GameState.PLAYED)
            if (flag.getCountOfClosedBoxes() == bomb.getTotalBombs())
                state = GameState.WINNER;
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
        flag.setBombedToBox(bombed);
        for (Coord coord : Ranges.getAllCoords())
            if (bomb.get(coord) == Box.BOMB)
                flag.setOpenedToCloseBombBox(coord);
            else
                flag.setNoBombToFlagedSafeBox(coord);
    }

    /**
     * openBoxesAround()
     * 周辺のマスを開けていく
     * @param coord 座標
     */
    private void openBoxesAround(Coord coord) {
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

}
