package minesweeper.modules;

import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;


/**
 * class KanjiMap
 * 漢字の盤面
 */
public class KanjiMap {
    private KanjiMatrix kanjiMatrix = new KanjiMatrix();

    /**
     * KanjiMap()
     * 漢字をセットする
     */
    KanjiMap() throws IOException {
        KanjiJson kanjiJson = new KanjiJson();
        List<Kanji> kanjiList = kanjiJson.getKanjiList();
        Iterator<Kanji> it = kanjiList.iterator();
        for (Coord coord : Ranges.getAllCoords()){
            Kanji kanji = it.next();
            if (kanji.kanji.isBlank()){
                System.out.println(kanji);
            }
            kanjiMatrix.setKanji(coord,kanji);
        }
    }

    /**
     * get()
     * 渡された座標のBoxを返す
     * @param coord 座標
     * @return Box
     */
    Box get(Coord coord){
        return kanjiMatrix.get(coord);
    }

    /**
     * getKanji()
     * 渡された座標の漢字を返す
     * @param coord 座標
     * @return kanji Kanji
     */
    public Kanji getKanji(Coord coord){
        return kanjiMatrix.getKanji(coord);
    }

    /**
     * submitKanji()
     * 読み仮名が正しいかどうかを返す
     * @param coord 座標
     * @param yomi 読み仮名
     * @return true or false
     */
    boolean submitKanji(Coord coord, String yomi){
        return kanjiMatrix.getKanji(coord).yomi.contains(yomi);
    }

    /**
     * setKanjiStackPane()
     * 渡された座標のKanjiにStackPaneをセットする
     * @param coord 座標
     * @param pane StackPane
     */
    void setKanjiStackPane(Coord coord, StackPane pane){
        kanjiMatrix.setKanjiStackPane(coord,pane);
    }

    /**
     * getKanjiStackPane()
     * 渡された座標のStackPaneを返す
     * @param coord 座標
     * @return pane StackPane
     */
    StackPane getKanjiStackPane(Coord coord){
        return kanjiMatrix.getKanjiStackPane(coord);
    }

    /**
     * setAnsweredToBox()
     * 渡された座標を正解済みにする
     * @param coord 座標
     */
    void setAnsweredToBox(Coord coord){
        kanjiMatrix.set(coord,Box.KANSWERED);
    }
}
