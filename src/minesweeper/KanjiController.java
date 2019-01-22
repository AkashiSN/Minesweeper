package minesweeper;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import minesweeper.modules.Coord;
import minesweeper.modules.KanjiMap;


public class KanjiController{
    private Coord coord;
    @FXML private Label kanji;
    @FXML private TextField kaitouran;

    @FXML
    void submitKanji() {
        boolean result = KanjiMap.submitKanji(coord,kaitouran.getText());
        if(result){
            KanjiMap.setAnsweredToBox(coord);
            kanji.getScene().getWindow().hide();
            System.out.println("正解");
        }
    }

    void setKanji(String k){
        kanji.setText(k);
    }

    void setCoord(Coord c){
        coord = c;
    }

}
