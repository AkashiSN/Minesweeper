package minesweeper;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import minesweeper.modules.Difficulty;

class StartController {
    private boolean singleMode = true;
    private Difficulty difficultMode = Difficulty.EASY;
    @FXML private TextField name;
    @FXML private Button start;

    @FXML void singleMode() {
        singleMode = true;
        start.setText("ゲーム開始");
    }

    @FXML void multiMode() {
        singleMode = false;
        start.setText("マッチング開始");
    }

    @FXML void easyMode() {
        difficultMode = Difficulty.EASY;
    }

    @FXML void normalMode() {
        difficultMode = Difficulty.NORMAL;
    }

    @FXML void difficultMode() {
        difficultMode = Difficulty.DIFFICULT;
    }

    @FXML void startButton() throws Exception {
        if(singleMode){
            int c,r,b;
            switch (difficultMode){
                case NORMAL:
                    c = 15;
                    r = 15;
                    b = c * r / 5;
                    break;
                case DIFFICULT:
                    c = 20;
                    r = 10;
                    b = c * r / 5;
                    break;
                default:
                    c = 6;
                    r = 6;
                    b = c * r / 5;
            }
            MineSweeper mine = new MineSweeper(c,r,b,name.getText());
            mine.start();
        }
    }

}
