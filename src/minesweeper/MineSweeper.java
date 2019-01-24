package minesweeper;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import minesweeper.modules.Coord;
import minesweeper.modules.Game;

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
     * @throws Exception fxmlのロードエラー
     */
    void start(boolean autoMode) throws Exception {
        openMainWindow(autoMode);
    }

    private void openMainWindow(boolean autoMode) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/game.fxml"));
        Parent root = fxmlLoader.load();
        GameController gameController = fxmlLoader.getController();

        Coord size = gameController.initPane(autoMode);
        Game game = gameController.getGame();

        Main.currentStage.setResizable(false);
        Main.currentStage.setScene(new Scene(root,size.x,size.y));

        double x = Main.currentStage.getX() + Main.currentStage.getWidth();
        double y = Main.currentStage.getY();

        Label flagsBomb =  openInfoWindow(game, x, y);
        gameController.setFlagsBom(flagsBomb);
        //gameController.startAuto();
    }

    private Label openInfoWindow(Game game, double x, double y) throws IOException {
        Stage infoWindow = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/info.fxml"));
        Parent root = fxmlLoader.load();

        InfoController infoController = fxmlLoader.getController();
        infoController.init(game, name);

        Scene scene = new Scene(root);
        infoWindow.setScene(scene);
        infoWindow.setResizable(false);
        infoWindow.setX(x);
        infoWindow.setY(y);
        infoWindow.show();

        return infoController.getFlagsBomb();
    }
}
