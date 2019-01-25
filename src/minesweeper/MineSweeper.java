package minesweeper;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import minesweeper.modules.Coord;
import minesweeper.modules.Game;
import minesweeper.solver.Solver;

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
    private Label flagsBomb;
    private ListView<String> list;

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
        Solver solver = gameController.getSolver();

        Main.currentStage.setResizable(false);
        Main.currentStage.setScene(new Scene(root, size.x, size.y));

        double x = Main.currentStage.getX() + Main.currentStage.getWidth();
        double y = Main.currentStage.getY();

        openInfoWindow(game,solver, x, y);
        gameController.setName(name);
        gameController.setFlagsBom(flagsBomb);
        gameController.setLogger(list);

        Main.currentStage.showingProperty().addListener((observable, oldValue, newValue) -> { // ウィンドウが閉じた時のイベントハンドラ
            if (oldValue && !newValue) {
                flagsBomb.getScene().getWindow().hide();

            }
        });
        if (autoMode) {
            gameController.startAuto();
        }
    }

    private void openInfoWindow(Game g, Solver s, double x, double y) throws IOException {
        Stage infoWindow = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/info.fxml"));
        Parent root = fxmlLoader.load();

        InfoController infoController = fxmlLoader.getController();
        infoController.init(g, s, name);

        Scene scene = new Scene(root);
        infoWindow.setScene(scene);
        infoWindow.setResizable(false);
        infoWindow.setX(x);
        infoWindow.setY(y);
        infoWindow.show();

        flagsBomb = infoController.getFlagsBomb();
        list = infoController.getLogList();
    }
}
