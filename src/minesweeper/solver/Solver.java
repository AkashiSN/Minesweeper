package minesweeper.solver;

import javafx.application.Platform;
import minesweeper.modules.*;

public class Solver {
    private static final double EPSILON = 1e-10;
    private static final double INF = 1e10;
    private static final double UNKNOWN = 100;

    private Game game;
    private Coord size;
    private Logger logger;
    private Matrix solveMap;

    public Solver(Game g){
        game = g;
        size = Ranges.getSize();
        solveMap = new Matrix(Box.CLOSED);
    }

    public void setLogger(Logger l){
        logger = l;
    }

    public void start() {
        Coord center = new Coord(size.x/2,size.y/2);
        game.pressPrimaryButton(center);
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
            logger.addLog(game.getState().toString());
        }).start();

    }

    private void solve(){
        double least = 10;

        Coord leastPoint = Ranges.getRandomCoord();
        while (game.isOpened(leastPoint)){
            leastPoint = Ranges.getRandomCoord();
        }

        for (Coord coord : Ranges.getAllCoords()){
            if (game.getBox(coord) == Box.CLOSED) { // 対象マス
                double eval = evaluationFunction(coord);
                if (eval == INF){
                    game.pressSecondaryButton(coord);
                    return;
                }else if (eval == 0){
                    game.pressPrimaryButton(coord);
                    return;
                }else {
                    if (least > eval){
                        least = eval;
                        leastPoint = coord;
                    }
                }
            }
        }
        logger.addLog("Randomly selected " + leastPoint.show());
        game.pressPrimaryButton(leastPoint);
    }

    private double evaluationFunction(Coord coord) {
        double sumOfProbability = 0;
        boolean flag = false;
        for (Coord point : Ranges.getCoordsAround(coord)) {
            if (game.isOpened(point)) {
                double probability = calcProbability(point);
                flag = true;
                if (probability > 1 - EPSILON) { // 1の時 爆弾あり
                    return INF;
                } else if (probability == 0) { // 0の時 爆弾なし
                    return 0;
                } else {
                    sumOfProbability += probability;
                }
            }
        }
        if (flag) {
            return sumOfProbability;
        } else {
            return UNKNOWN;
        }
    }

    private double calcProbability(Coord point) {
        int countOfFlags = 0;
        double countOfClose = 0;
        int numOfBomb = game.getBox(point).getNumber();

        for (Coord aroundPoint : Ranges.getCoordsAround(point)) {
            switch (game.getBox(aroundPoint)) {
                case CLOSED:
                    countOfClose++;
                    break;
                case FLAGED:
                    countOfFlags++;
                    break;
                default:
                    break;
            }
        }
        return (numOfBomb - countOfFlags) / countOfClose;
    }
}
