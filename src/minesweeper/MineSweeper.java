package minesweeper;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Screen;
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
    private static int lifeValue;
    private static String name; // ニックネーム
    private Label flagsBomb;
    private Label life;
    private ListView<String> list;

    /**
     * MineSweeper()
     * 変数セット
     * @param c 横幅
     * @param r 縦の長さ
     * @param b 地雷の数
     * @param n ニックネーム
     */
    MineSweeper(int c,int r,int b,int l, String n){
        cols = c;
        rows = r;
        boms = b;
        name = n;
        lifeValue = l;
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

        Main.currentStage.setTitle("難読マインスイーパー");
        Main.currentStage.setResizable(false);

        Main.currentStage.setScene(new Scene(root, size.x, size.y));

        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        Main.currentStage.setX((primScreenBounds.getWidth() - Main.currentStage.getWidth()) / 2);
        Main.currentStage.setY((primScreenBounds.getHeight() - Main.currentStage.getHeight()) / 2);


        double x = Main.currentStage.getX() + Main.currentStage.getWidth();
        double y = Main.currentStage.getY();

        openInfoWindow(game,solver, x, y);
        gameController.setFlagsBomb(flagsBomb);
        gameController.setLogger(list);
        game.setLife(life);
        game.setLifeValue(lifeValue);

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
        life = infoController.getLife();
    }
}
