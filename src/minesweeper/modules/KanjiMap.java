package minesweeper.modules;

import javafx.scene.layout.StackPane;

/**
 * class KanjiMap
 * 漢字の盤面
 */
public class KanjiMap {
    private static KanjiMatrix kanjiMatrix = new KanjiMatrix();

    /**
     * KanjiMap()
     * 漢字をセットする
     */
    KanjiMap(){
        for (Coord coord : Ranges.getAllCoords()){
            kanjiMatrix.setKanji(coord,new Kanji("漢字","かんじ"));
        }
    }

    /**
     * get()
     * 渡された座標のBoxを返す
     * @param coord 座標
     * @return Box
     */
    public static Box get(Coord coord){
        return kanjiMatrix.get(coord);
    }

    /**
     * getKanji()
     * 渡された座標の漢字を返す
     * @param coord
     * @return kanji Kanji
     */
    public static Kanji getKanji(Coord coord){
        return kanjiMatrix.getKanji(coord);
    }

    /**
     * submitKanji()
     * 読み仮名が正しいかどうかを返す
     * @param coord 座標
     * @param yomi 読み仮名
     * @return true or false
     */
    public static boolean submitKanji(Coord coord, String yomi){
        return kanjiMatrix.getKanji(coord).yomi.equals(yomi);
    }

    /**
     * setKanjiStackPane()
     * 渡された座標のKanjiにStackPaneをセットする
     * @param coord 座標
     * @param pane StackPane
     */
    public static void setKanjiStackPane(Coord coord, StackPane pane){
        kanjiMatrix.setKanjiStackPane(coord,pane);
    }

    /**
     * getKanjiStackPane()
     * 渡された座標のStackPaneを返す
     * @param coord 座標
     * @return pane StackPane
     */
    public static StackPane getKanjiStackPane(Coord coord){
        return kanjiMatrix.getKanjiStackPane(coord);
    }

    /**
     * setAnsweredToBox()
     * 渡された座標を正解済みにする
     * @param coord 座標
     */
    public static void setAnsweredToBox(Coord coord){
        kanjiMatrix.set(coord,Box.KANSWERED);
    }
}
