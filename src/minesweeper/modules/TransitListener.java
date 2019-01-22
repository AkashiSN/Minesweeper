package minesweeper.modules;

/**
 * Interface TransitListener
 */
public interface TransitListener {
    /**
     * transitAndNotify()
     * 上位の関数を呼び出す
     * @param coord 座標
     */
    void transitAndNotify(Coord coord);
}
