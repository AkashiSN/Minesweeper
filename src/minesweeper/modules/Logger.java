package minesweeper.modules;

import javafx.scene.control.ListView;

public class Logger {
    private ListView<String> list;

    public Logger(ListView<String> l){
        list = l;
    }

    public void addLog(String l){
        if (!l.isBlank()){
            list.getItems().add(l);
        }
    }
}
