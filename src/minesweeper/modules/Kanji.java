package minesweeper.modules;

import javafx.scene.layout.StackPane;

/**
 * class Kanji
 * 漢字の構造体
 */
public class Kanji {
    public String kanji; // 漢字
    String yomi; // 読み仮名
    StackPane kanjiStackPane; // StackPane

    /**
     * Kanji()
     * 初期化
     * @param k 漢字
     * @param y 読み仮名
     */
    Kanji(String k, String y) {
        kanji = k;
        yomi = y;
    }
}
