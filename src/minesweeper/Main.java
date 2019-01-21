package minesweeper;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    static Stage currentStage;
    static Scene primaryScene;

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("start.fxml"));
        Parent root = loader.load();

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