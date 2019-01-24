package minesweeper.solver;

import minesweeper.modules.Coord;
import minesweeper.modules.Game;
import minesweeper.modules.Ranges;

public class Solver {
    private Game game;

    public Solver(Game g){
        game = g;
    }

    public void solve() {
        Coord coord = Ranges.getRandomCoord();
        game.pressPrimaryButton(coord);
    }

}
