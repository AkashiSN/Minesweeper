package minesweeper.modules;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

import java.util.ArrayList;
import java.util.List;

public class Logger {
    private List<String> log;
    private ListView<String> list;

    public Logger(ListView<String> l){
        log = new ArrayList<>();
        list = l;
    }

    public void addLog(String l){
        log.add(l);
        update();
    }

    private void update(){
        ObservableList<String> lm = FXCollections.observableArrayList(log);
        list.getItems().clear();
        list.setItems(lm);
    }

}
