package minesweeper.modules;

class KanjiMap {
    private static KanjiMatrix kanjiMatrix = new KanjiMatrix();

    KanjiMap(){
        for (Coord coord : Ranges.getAllCoords()){
            kanjiMatrix.setKanji(coord,new Kanji("漢字","かんじ"));
        }
    }

    Box get(Coord coord){
        return kanjiMatrix.get(coord);
    }

    Kanji getKanji(Coord coord){
        return kanjiMatrix.getKanji(coord);
    }

    boolean submitKanji(Coord coord,String yomi){
        return kanjiMatrix.getKanji(coord).yomi.equals(yomi);
    }
}
