package minesweeper;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import minesweeper.modules.Difficulty;

public class StartController {
    private boolean singleMode = true;
    private boolean autoMode = false;
    private Difficulty difficultMode = Difficulty.EASY;
    @FXML private TextField name;
    @FXML private Button start;

    @FXML private void singleMode() {
        singleMode = true;
        autoMode = false;
        if(name.isDisable()){
            name.setText("");
            name.setDisable(false);
        }
        start.setText("ゲーム開始");
    }

    @FXML private void multiMode() {
        singleMode = false;
        autoMode = false;
        if(name.isDisable()){
            name.setText("");
            name.setDisable(false);
        }
        start.setText("マッチング開始");
    }

    @FXML void autoMode(){
        autoMode = true;
        name.setText("CPU");
        name.setDisable(true);
    }

    @FXML private void easyMode() {
        difficultMode = Difficulty.EASY;
    }

    @FXML private void normalMode() {
        difficultMode = Difficulty.NORMAL;
    }

    @FXML private void difficultMode() {
        difficultMode = Difficulty.DIFFICULT;
    }

    @FXML private void startButton() throws Exception {
        if(singleMode){
            int c,r,b,l;
            switch (difficultMode){
                case NORMAL:
                    c = 9;
                    r = 9;
                    b = c * r / 6;
                    l = 5;
                    break;
                case DIFFICULT:
                    c = 20;
                    r = 15;
                    b = c * r / 6;
                    l = 10;
                    break;
                default:
                    c = 7;
                    r = 7;
                    b = c * r / 7;
                    l = 3;
            }
            MineSweeper mine = new MineSweeper(c,r,b,l,name.getText());
            mine.start(autoMode);
        }
    }

}
