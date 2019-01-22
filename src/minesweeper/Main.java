package minesweeper;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    static Stage currentStage;
    static Scene primaryScene;

    /**
     * start()
     * スタート画面を表示する
     * @param primaryStage 初期画面
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader gameController = new FXMLLoader(getClass().getResource("fxml/start.fxml"));
        Parent root = gameController.load();

        primaryStage.setTitle("難読マインスイーパー");
        primaryScene = new Scene(root);

        primaryStage.setScene(primaryScene);
        primaryStage.show();

        currentStage = primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}