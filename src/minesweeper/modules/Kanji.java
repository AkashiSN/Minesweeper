package minesweeper.modules;

import javafx.scene.layout.StackPane;

import java.util.List;

/**
 * class Kanji
 * 漢字の構造体
 */
public class Kanji {
    public String kanji; // 漢字
    public List<String> yomi; // 読み仮名
    public String imi; // 意味
    StackPane kanjiStackPane; // StackPane

    /**
     * Kanji()
     * 初期化
     * @param k 漢字
     * @param y 読み仮名
     */
    Kanji(String k,List<String> y,String i) {
        kanji = k;
        yomi = y;
        imi = i;
    }
}
