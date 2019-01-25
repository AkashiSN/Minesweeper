package minesweeper;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
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
    @FXML private Label imi;
    @FXML private Label imiLabel;
    @FXML private Label infoLabel;
    @FXML private TextField answerText; // 回答欄

    /**
     * submitKanji()
     * 回答ボタンが押された時
     * @throws IOException
     */
    @FXML private void onSubmitButtonAction() throws IOException {
        String answer = answerText.getText();
        if (answer.isBlank()){ // 空欄の時
            return;
        }
        submitKanji();
    }

    /**
     * submitKanji()
     * 漢字を回答する
     * @throws IOException
     */
    private void submitKanji() throws IOException {
        boolean result = game.submitKanji(coord, answerText.getText()); // 回答が合っているか
        if(result){ // 正解の時
            game.setAnsweredToBox(coord);
            Kanji kanji = game.getKanji(coord);
            Iterator<String> it = kanji.yomi.iterator();
            StringBuilder yomi = new StringBuilder();
            yomi.append(it.next());
            int size = kanji.yomi.size() - 1;
            if ( size > 0){
                yomi.append('（');
                for (int i = 0; i < size; i++){
                    yomi.append(it.next());
                    if (i != size - 1)
                        yomi.append('・');
                }
                yomi.append('）');
            }
            openCorrectWindow(kanji.kanji,yomi.toString(),kanji.imi);
        }else{
            openInCorrectWindow();
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
        answerText.setOnKeyPressed(ke -> {
            if (ke.getCode().equals(KeyCode.ENTER))
            {
                try {
                    submitKanji();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        imiLabel.setFont(Font.loadFont("file:resources/fonts/ipam.ttf",20));
        imi.setFont(Font.loadFont("file:resources/fonts/ipam.ttf",15));
        kanji.setFont(Font.loadFont("file:resources/fonts/ipam.ttf",50));
        answerText.setFont(Font.loadFont("file:resources/fonts/ipam.ttf",15));
        String info;
        if (shift){
            info = "フラグを立てる";
        }else{
            info = "マスを開ける";
        }
        infoLabel.setText(info);
        imi.setText(i);
        kanji.setText(k);
        coord = c;
        kanjiStage = s;
    }

    private void openInCorrectWindow() {
        Stage inCorrectWindow = new Stage();
        inCorrectWindow.initModality(Modality.APPLICATION_MODAL); // モーダルウインドウに設定
        inCorrectWindow.initOwner(kanjiStage); // オーナーを設定
        inCorrectWindow.setTitle("不正解");
        StackPane stackPane = new StackPane();
        Text text = new Text("不正解");
        stackPane.getChildren().add(text);
        StackPane.setAlignment(text, Pos.CENTER);

        Scene scene = new Scene(stackPane,200,100);
        setupWindow(inCorrectWindow, scene);
    }

    private void setupWindow(Stage Window, Scene scene) {
        scene.setOnKeyPressed((KeyEvent e) -> scene.getWindow().hide());
        Window.setScene(scene);
        Window.show();
        Window.showingProperty().addListener((observable, oldValue, newValue) -> { // 漢字の回答ウィンドウが閉じた時のイベントハンドラ
            answerText.getScene().getWindow().hide();
        });
    }

    private void openCorrectWindow(String k,String y, String i) throws IOException {
        Stage correctWindow = new Stage();
        correctWindow.initModality(Modality.APPLICATION_MODAL); // モーダルウインドウに設定
        correctWindow.initOwner(kanjiStage); // オーナーを設定
        correctWindow.setTitle("正解！");

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/correct.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        setupWindow(correctWindow, scene);
        final CorrectController correctController = fxmlLoader.getController(); // 回答ウィンドウのコントローラー
        correctController.init(k, y, i);
    }
}
