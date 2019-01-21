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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("game.fxml"));
        Parent root = loader.load();
        GameController con = loader.getController();

        Timer timer = new Timer(con.timer);
        con.name.setText(name);

        Coord size = con.initPane();
        timer.start();

        Main.currentStage.setScene(new Scene(root,size.x,size.y));
    }

}
