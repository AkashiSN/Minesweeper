package minesweeper.modules;

public class TransitController {
    private static TransitListener transitListener;

    public static void setTransitListener(TransitListener transitListener){
        TransitController.transitListener = transitListener;
    }

    static void transit(Coord coord){
        transitListener.transitAndNotify(coord);
    }
    static void restart(){
        transitListener.restart();
    }
}
