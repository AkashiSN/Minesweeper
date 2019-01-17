package minesweeper;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;


public class Controller {

    @FXML // fx:id="Button1"
    private Button Button1; // Value injected by FXMLLoader

    @FXML // fx:id="textField1"
    private TextField textField1; // Value injected by FXMLLoader

    @FXML
    void onButton1Action(ActionEvent event) {
        GameController.getInstance().show();
    }

}
