package minesweeper.modules;

import javafx.scene.layout.StackPane;

public class KanjiMap {
    private static KanjiMatrix kanjiMatrix = new KanjiMatrix();


    KanjiMap(){
        for (Coord coord : Ranges.getAllCoords()){
            kanjiMatrix.setKanji(coord,new Kanji("漢字","かんじ"));
        }
    }

    public static Box get(Coord coord){
        return kanjiMatrix.get(coord);
    }

    public static Kanji getKanji(Coord coord){
        return kanjiMatrix.getKanji(coord);
    }

    public static boolean submitKanji(Coord coord,String yomi){
        return kanjiMatrix.getKanji(coord).yomi.equals(yomi);
    }

    public static void setKanjiStackPane(Coord coord, StackPane pane){
        kanjiMatrix.setKanjiStackPane(coord,pane);
    }

    public static StackPane getKanjiStackPane(Coord coord){
        return kanjiMatrix.getKanjiStackPane(coord);
    }

    public static void setAnsweredToBox(Coord coord){
        kanjiMatrix.set(coord,Box.KANSWERED);
    }
}
