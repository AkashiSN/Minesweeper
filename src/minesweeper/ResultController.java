package minesweeper;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

public class ResultController {
    @FXML private Label kanji;
    @FXML private Label yomi;
    @FXML private Label imi;
    @FXML private Label imiLabel;
    @FXML private Label infoLabel;

    /**
     * init()
     * 初期設定する
     * @param k 漢字
     */
    void init(String k, String y, String i,String r){
        kanji.setFont(Font.loadFont("file:resources/fonts/ipam.ttf",50));
        kanji.setText(k);
        yomi.setFont(Font.loadFont("file:resources/fonts/ipam.ttf",20));
        yomi.setText(y);
        imi.setFont(Font.loadFont("file:resources/fonts/ipam.ttf",15));
        imi.setText(i);
        imiLabel.setFont(Font.loadFont("file:resources/fonts/ipam.ttf",20));
        infoLabel.setText(r);
    }
}