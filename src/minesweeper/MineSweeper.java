package minesweeper;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import minesweeper.modules.Coord;
import minesweeper.modules.Timer;

import java.io.IOException;

public class MineSweeper {
    static int cols;
    static int rows;
    static int boms;
    private static String name;

    MineSweeper(int c,int r,int b,String n){
        cols = c;
        rows = r;
        boms = b;
        name = n;
    }

    void start() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/game.fxml"));
        Parent root = fxmlLoader.load();
        GameController gameController = fxmlLoader.getController();

        new Timer(gameController.timer);
        gameController.name.setText(name);

        Coord size = gameController.initPane();

        Main.currentStage.setScene(new Scene(root,size.x,size.y));
    }

}
