package minesweeper.modules;

/**
 * class TransitController
 * ゲームコントローラーの描画更新関数を呼び出すため
 */
public class TransitController {
    private static TransitListener transitListener; // 検知変数

    /**
     * setTransitListener()
     * 監視対象をセットする
     * @param transitListener TransitListener
     */
    public static void setTransitListener(TransitListener transitListener){
        TransitController.transitListener = transitListener;
    }

    /**
     * transit()
     * 上位の関数を呼び出す
     * @param coord 座標
     */
    static void transit(Coord coord){
        transitListener.transitAndNotify(coord);
    }
}
