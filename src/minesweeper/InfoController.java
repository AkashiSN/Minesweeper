package minesweeper;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import minesweeper.modules.Game;
import minesweeper.modules.Timer;
import minesweeper.solver.Solver;

public class InfoController {
    private Game game;
    private Solver solver;
    @FXML private Label userName;
    @FXML private Label flagsBomb;
    @FXML private Label life;
    @FXML private Label timer;
    @FXML private ListView<String> log;

    /**
     * backToFront()
     * スタート画面に戻る
     */
    @FXML private void backToFront() {
        game.reset();
        game = null;
        solver = null;
        userName.getScene().getWindow().hide();
        Main.currentStage.setScene(Main.primaryScene);
    }

    /**
     * exitProgram()
     * プログラムを終了する
     */
    @FXML private void exitProgram(){
        Platform.exit();
    }

    void init(Game g, Solver s, String name){
        game = g;
        solver = s;
        new Timer(timer);
        userName.setText(name);
    }

    Label getFlagsBomb(){
        return flagsBomb;
    }

    Label getLife(){
        return life;
    }

    ListView<String> getLogList(){
        return log;
    }
}
