package minesweeper.solver;

import javafx.application.Platform;
import minesweeper.modules.Coord;
import minesweeper.modules.Game;
import minesweeper.modules.GameState;
import minesweeper.modules.Ranges;

public class Solver {
    private Game game;

    public Solver(Game g){
        game = g;
    }

    public void start() {
        new Thread(() -> {
            while (game.getState() == GameState.PLAYED) {
                //考えているふりをする。
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Platform.runLater(this::solve);
            }
        }).start();

    }

    private void solve(){
        Coord coord = Ranges.getRandomCoord();
        game.pressPrimaryButton(coord);
    }

}
