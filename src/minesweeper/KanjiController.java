package minesweeper;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import minesweeper.modules.Box;
import minesweeper.modules.Coord;
import minesweeper.modules.Game;
import minesweeper.modules.Kanji;

import java.io.IOException;
import java.util.Iterator;

/**
 * class KanjiController
 * 漢字の回答ウィンドウのコントローラー
 */
public class KanjiController{
    private Coord coord; // 現在の座標
    private Stage kanjiStage; // このウィンドウのStage
    private Game game;
    @FXML private Label kanji; // 問題の漢字
    @FXML private Label imi; // 漢字の意味
    @FXML private Label imiLabel; // 意味
    @FXML private Label infoLabel; // マスを開けるかフラグを立てるか
    @FXML private TextField answerText; // 回答欄

    /**
     * submitKanji()
     * 回答ボタンが押された時
     * @throws IOException fxmlのロードエラー
     */
    @FXML private void onSubmitButtonAction() throws IOException {
        submitKanji(answerText.getText());
    }

    /**
     * submitKanji()
     * 漢字を回答する
     * @throws IOException fxmlのロードエラー
     */
    private void submitKanji(String answer) throws IOException {
        boolean result = game.submitKanji(coord, answer); // 回答が合っているか
        game.setKanjiStateToBox(coord, Box.KCORRECTED); // 正解にする

        Kanji kanji = game.getKanji(coord);
        Iterator<String> it = kanji.yomi.iterator();
        StringBuilder yomi = new StringBuilder();
        yomi.append(it.next());
        int size = kanji.yomi.size() - 1;
        if (size > 0) {
            yomi.append('（');
            for (int i = 0; i < size; i++) {
                yomi.append(it.next());
                if (i != size - 1)
                    yomi.append('・');
            }
            yomi.append('）');
        }
        openResulttWindow(kanji.kanji, yomi.toString(), kanji.imi, result);
        if (!result) { // 不正解の時ライフを減らす
            game.decrementLife();
            game.setKanjiStateToBox(coord, Box.KINCORRECTED);
        }
    }

    /**
     * init()
     * 初期設定する
     * @param k 漢字
     * @param c 読み仮名
     * @param s ウィンドウ
     */
    void init(String k,String i, Coord c, Stage s, Game g, boolean shift){
        game = g;
        coord = c;
        kanjiStage = s;

        answerText.setOnKeyPressed(ke -> {
            if (ke.getCode().equals(KeyCode.ENTER))
            {
                try {
                    submitKanji(answerText.getText());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        String filePath = "file:src/minesweeper/resources/fonts/ipam.ttf";
        imiLabel.setFont(Font.loadFont(filePath,20));
        imi.setFont(Font.loadFont(filePath,15));
        kanji.setFont(Font.loadFont(filePath,50));
        answerText.setFont(Font.loadFont(filePath,15));

        if (shift){
            infoLabel.setText("フラグを立てる");
        }else{
            infoLabel.setText("マスを開ける");
        }
        imi.setText(i);
        kanji.setText(k);
    }

    /**
     * openResulttWindow()
     * 結果のウィンドウを開く
     * @param k 漢字
     * @param y 読み仮名
     * @param i 意味
     * @param r 結果
     * @throws IOException fxmlのロードエラー
     */
    private void openResulttWindow(String k,String y, String i, boolean r) throws IOException {
        Stage resultWindow = new Stage();
        resultWindow.initModality(Modality.APPLICATION_MODAL); // モーダルウインドウに設定
        resultWindow.initOwner(kanjiStage); // オーナーを設定

        String result;
        if (r){ // 正解の時
            result = "正解！";
        }else{
            result = "不正解";
        }
        resultWindow.setTitle(result);

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/result.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);

        scene.setOnKeyPressed((KeyEvent e) -> scene.getWindow().hide());

        resultWindow.setScene(scene);
        resultWindow.show();
        resultWindow.showingProperty().addListener((observable, oldValue, newValue) -> { // 漢字の回答ウィンドウが閉じた時のイベントハンドラ
            answerText.getScene().getWindow().hide();
        });

        final ResultController resultController = fxmlLoader.getController(); // 回答ウィンドウのコントローラー
        resultController.init(k, y, i, result);
    }
}
