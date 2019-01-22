package minesweeper;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import minesweeper.modules.Coord;
import minesweeper.modules.KanjiMap;

/**
 * class KanjiController
 * 漢字の回答ウィンドウのコントローラー
 */
class KanjiController{
    private Coord coord; // 現在の座標
    @FXML private Label kanji; // 問題の漢字
    @FXML private TextField answerText; // 回答欄

    /**
     * submitKanji()
     * 回答ボタンが押された時
     */
    @FXML private void submitKanji() {
        String answer = answerText.getText();
        if (answer.isBlank()){ // 空欄の時
            return;
        }
        boolean result = KanjiMap.submitKanji(coord, answerText.getText()); // 回答が合っているか
        if(result){ // 正解の時
            KanjiMap.setAnsweredToBox(coord);
            kanji.getScene().getWindow().hide();
            System.out.println("正解");
        }
    }

    /**
     * setKanji()
     * 漢字を表示する
     * @param k 漢字
     */
    void setKanji(String k){
        kanji.setText(k);
    }

    /**
     * setCoord()
     * 座標をセットする
     * @param c 座標
     */
    void setCoord(Coord c){
        coord = c;
    }

}
