package minesweeper;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import minesweeper.modules.Game;
import minesweeper.modules.Timer;

public class InfoController {
    private Game game;
    @FXML private Label userName;
    @FXML private Label flagsBomb;
    @FXML private Label life;
    @FXML private Label timer;

    /**
     * backToFront()
     * スタート画面に戻る
     */
    @FXML private void backToFront() {
        game.reset();
        game = null;
        Main.currentStage.setScene(Main.primaryScene);
    }

    /**
     * exitProgram()
     * プログラムを終了する
     */
    @FXML private void exitProgram(){
        Platform.exit();
    }

    void init(Game g, String name){
        game = g;
        new Timer(timer);
        userName.setText(name);
    }

    Label getFlagsBomb(){
        return flagsBomb;
    }
}
