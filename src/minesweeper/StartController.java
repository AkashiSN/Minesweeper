package minesweeper;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;


public class StartController {
    private boolean singleMode = true;
    private int difficultMode = 0;

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
        difficultMode = 0;
    }

    @FXML
    void normalMode(ActionEvent event) {
        difficultMode = 1;
    }

    @FXML
    void difficultMode(ActionEvent event) {
        difficultMode = 2;
    }

    @FXML
    void startButton(ActionEvent event) throws Exception {
        if(singleMode){
            int c=10,r=10,b=10;
            switch (difficultMode){
                case 0:
                    break;
                case 1:
                    c = 15;
                    r = 15;
                    b = 30;
                    break;
                case 2:
                    c = 20;
                    r = 20;
                    b = 50;
            }
            MineSweeper mine = new MineSweeper(c,r,b);
            mine.start();
        }
    }

}
