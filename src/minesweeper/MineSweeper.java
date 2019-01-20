package minesweeper;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

public class MineSweeper {
    public static int cols;
    public static int rows;
    public static int boms;

    MineSweeper(int c,int r,int b){
        cols = c;
        rows = r;
        boms = b;
    }

    public void start() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("game.fxml"));
        Parent root = loader.load();
        Controller con = loader.getController();
        con.initPane();
        Main.currentStage.setScene(new Scene(root));
    }

}
