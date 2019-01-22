package minesweeper.modules;

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

}
