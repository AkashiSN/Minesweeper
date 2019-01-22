package minesweeper;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import minesweeper.modules.Coord;

import java.io.IOException;

/**
 * class MineSweeper
 * マインスイーパーの情報共有クラス
 */
class MineSweeper {
    static int cols; // 横幅
    static int rows; // 縦の長さ
    static int boms; // 地雷の数
    private static String name; // ニックネーム

    /**
     * MineSweeper()
     * 変数セット
     * @param c 横幅
     * @param r 縦の長さ
     * @param b 地雷の数
     * @param n ニックネーム
     */
    MineSweeper(int c,int r,int b,String n){
        cols = c;
        rows = r;
        boms = b;
        name = n;
    }

    /**
     * start()
     * ゲーム画面に遷移させる
     * @throws IOException fxmlのロードエラー
     */
    void start() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/game.fxml"));
        Parent root = fxmlLoader.load();
        GameController gameController = fxmlLoader.getController();
        Coord size = gameController.initPane(name);
        Main.currentStage.setScene(new Scene(root,size.x,size.y));
    }

}
