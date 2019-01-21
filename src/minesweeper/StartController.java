package minesweeper;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import minesweeper.modules.Difficulty;

public class StartController {
    private boolean singleMode = true;
    private Difficulty difficultMode = Difficulty.EASY;

    @FXML
    private RadioButton single;

    @FXML
    private ToggleGroup mode;

    @FXML
    private ToggleGroup difficulty;

    @FXML
    private RadioButton normal;

    @FXML
    private TextField name;

    @FXML
    private Button start;

    @FXML
    private RadioButton difficult;

    @FXML
    private RadioButton easy;

    @FXML
    private RadioButton multi;

    @FXML
    void singleMode(ActionEvent event) {
        singleMode = true;
        start.setText("ゲーム開始");
    }

    @FXML
    void multiMode(ActionEvent event) {
        singleMode = false;
        start.setText("マッチング開始");
    }

    @FXML
    void easyMode(ActionEvent event) {
        difficultMode = Difficulty.EASY;
    }

    @FXML
    void normalMode(ActionEvent event) {
        difficultMode = Difficulty.NORMAL;
    }

    @FXML
    void difficultMode(ActionEvent event) {
        difficultMode = Difficulty.DIFFICULT;
    }

    @FXML
    void startButton(ActionEvent event) throws Exception {
        if(singleMode){
            int c,r,b;
            switch (difficultMode){
                case NORMAL:
                    c = 15;
                    r = 15;
                    b = 30;
                    break;
                case DIFFICULT:
                    c = 20;
                    r = 20;
                    b = 60;
                    break;
                default:
                    c = 10;
                    r = 10;
                    b = 20;
            }
            MineSweeper mine = new MineSweeper(c,r,b,name.getText());
            mine.start();
        }
    }

}
