package minesweeper.modules;

import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

/**
 * class KanjiMatrix
 * 漢字+Matrix
 */
class KanjiMatrix extends Matrix {
    private Kanji[][] kanjiMatrix; // 漢字の配列

    /**
     * KanjiMatrix()
     * Boxを未回答で初期化する
     * 漢字の配列を初期化する
     */
    KanjiMatrix(){
        super(Box.KNOANSWERED);
        kanjiMatrix = new Kanji[Ranges.getSize().x][Ranges.getSize().y];
    }

    /**
     * getKanji()
     * 座標の漢字を返す
     * @param coord 座標
     * @return kanji 漢字
     */
    Kanji getKanji(Coord coord){
        if(Ranges.inRange(coord))
            return kanjiMatrix[coord.x][coord.y];
        return null;
    }

    /**
     * setKanji()
     * 座標に漢字をセットする
     * @param coord 座標
     * @param kanji 漢字
     */
    void setKanji(Coord coord,Kanji kanji){
        if(Ranges.inRange(coord))
            kanjiMatrix[coord.x][coord.y] = kanji;
    }

    /**
     * setKanjiStackPane()
     * 座標にStackPaneをセットする
     * @param coord 座標
     * @param pane StackPane
     */
    void setKanjiStackPane(Coord coord, StackPane pane){
        kanjiMatrix[coord.x][coord.y].kanjiStackPane = pane;
    }

    /**
     * getKanjiStackPane()
     * 座標のStackPaneを返す
     * @param coord 座標
     * @return pane StackPane
     */
    StackPane getKanjiStackPane(Coord coord){
        return kanjiMatrix[coord.x][coord.y].kanjiStackPane;
    }

    /**
     * setImageView()
     * 渡された座標にImageViewをセットする
     * @param coord 座標
     * @param iv ImageView
     */
    void setImageView(Coord coord, ImageView iv){
        kanjiMatrix[coord.x][coord.y].imageView = iv;
    }

    /**
     * getImageView()
     * 渡された座標のImageViewを返す
     * @param coord 座標
     * @return ImageView
     */
    ImageView getImageView(Coord coord){
        return kanjiMatrix[coord.x][coord.y].imageView;
    }

}
