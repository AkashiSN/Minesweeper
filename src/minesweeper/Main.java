package minesweeper;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public static Stage presentStage;
    public static Scene primaryScene;

    @Override
    public void start(Stage stage) throws Exception{
        presentStage = stage;
        Parent root = FXMLLoader.load(getClass().getResource("minesweeper.fxml"));
        presentStage.setTitle("Hello World");
        Scene s = new Scene(root, 400, 400);
        primaryScene = s;
        presentStage.setScene(s);
        presentStage.show();
        presentStage = stage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
