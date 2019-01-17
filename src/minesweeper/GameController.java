/**
 * Sample Skeleton for 'game.fxml' Controller Class
 */


package minesweeper;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import java.io.IOException;

public class GameController {
    private static final GameController instance;
    private static final Scene SCENE;

    static {
        FXMLLoader fxmlLoader = new FXMLLoader(MineSweeper.class.getResource(("game.fxml")));
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Parent parent = fxmlLoader.getRoot();
        Scene s = new Scene(parent);
        s.setFill(Color.TRANSPARENT);
        SCENE = s;
        instance = fxmlLoader.getController();

    }

    public static GameController getInstance(){
        return instance;
    }

    public void show() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(50);
        grid.setVgap(50);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Scene scene = new Scene(grid, 300, 275);
        Button button1 = new Button();
        Button button2 = new Button();
        grid.add(button1, 0, 0, 2, 1);
        grid.add(button2, 2, 0, 2, 1);

        Main.presentStage.setScene(scene);
    }

    @FXML // fx:id="AnchorPanel"
    private AnchorPane AnchorPanel; // Value injected by FXMLLoader

    @FXML
    private Button panel;

    @FXML
    void back(ActionEvent event) {
        Main.presentStage.setScene(Main.primaryScene);
    }

}