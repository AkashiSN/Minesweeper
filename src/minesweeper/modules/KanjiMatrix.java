package minesweeper.modules;

import javafx.scene.layout.StackPane;

class KanjiMatrix extends Matrix {
    private Kanji[][] kanjiMatrix;

    KanjiMatrix(){
        super(Box.KNOANSWERED);
        kanjiMatrix = new Kanji[Ranges.getSize().x][Ranges.getSize().y];
    }

    Kanji getKanji(Coord coord){
        if(Ranges.inRange(coord))
            return kanjiMatrix[coord.x][coord.y];
        return null;
    }

    void setKanji(Coord coord,Kanji kanji){
        if(Ranges.inRange(coord))
            kanjiMatrix[coord.x][coord.y] = kanji;
    }

    void setKanjiStackPane(Coord coord, StackPane pane){
        kanjiMatrix[coord.x][coord.y].kanjiStackPane = pane;
    }

    StackPane getKanjiStackPane(Coord coord){
        return kanjiMatrix[coord.x][coord.y].kanjiStackPane;
    }

}
