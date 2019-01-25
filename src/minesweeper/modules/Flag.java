package minesweeper.modules;

/**
 * class Flag
 * フラグに関する操作
 */
class Flag {
    private Matrix flagMap; // フラグの盤面
    private int countOfClosedBoxes; // 閉じられているマスの数
    private int countOfFlagedBoxes; // フラグが立てられているマスの数
    private Logger logger;

    /**
     * start()
     * フラグの盤面をCLOSEDで初期化
     */
    void start() {
        flagMap = new Matrix(Box.CLOSED);
        countOfClosedBoxes = Ranges.getSize().x * Ranges.getSize().y;
    }

    /**
     * get()
     * 渡された座標のマスを返す
     * @param coord 座標
     * @return box
     */
    Box get(Coord coord) {
        return flagMap.get(coord);
    }

    void setLogger(Logger l){
        logger = l;
    }

    /**
     * setOpenedToBox()
     * 渡された座標のマスを開ける
     * @param coord 座標
     */
    void setOpenedToBox(Coord coord) {
        flagMap.set(coord, Box.OPENED);
        countOfClosedBoxes--;
        transitToController(coord);
    }

    /**
     * toggleFlagedToBox()
     * 渡された座標のマスのフラグをとったりつけたりする
     * @param coord 座標
     */
    void toggleFlagedToBox(Coord coord) {
        switch (flagMap.get(coord)) {
            case FLAGED:
                setClosedToBox(coord);
                logger.addLog("Remove flag of " + coord.show() );
                break;
            case CLOSED:
                setFlagedToBox(coord);
                logger.addLog("Set flag in " + coord.show() );
                break;
        }
    }

    /**
     * setClosedToBox()
     * 渡された座標のマスを閉じる（フラグをとる）
     * @param coord 座標
     */
    private void setClosedToBox(Coord coord) {
        flagMap.set(coord, Box.CLOSED);
        countOfFlagedBoxes--;
        transitToController(coord);
    }

    /**
     * setFlagedToBox()
     * 渡された座標のマスにフラグをセットする
     * @param coord 座標
     */
    private void setFlagedToBox(Coord coord) {
        flagMap.set(coord, Box.FLAGED);
        countOfFlagedBoxes++;
        transitToController(coord);
    }

    /**
     * transitToController()
     * ゲームコントローラーの描画更新関数を実行する
     * @param coord 座標
     */
    private void transitToController(Coord coord){
        TransitController.transit(coord);
    }

    /**
     * getCountOfClosedBoxes()
     * 閉じているマスの数を返す
     * @return countOfClosedBoxes
     */
    int getCountOfClosedBoxes() {
        return countOfClosedBoxes;
    }

    /**
     * setBombedToBox()
     * 渡された座標のマスを地雷が爆発した
     * @param coord 座標
     */
    void setBombedToBox(Coord coord) {
        flagMap.set(coord, Box.BOMBED);
        logger.addLog("Bombed in " + coord.show() );
        transitToController(coord);
    }

    /**
     * setOpenedToCloseBombBox()
     * 渡された座標のマスを開ける
     * @param coord 座標
     */
    void setOpenedToCloseBombBox(Coord coord) {
        if (flagMap.get(coord) == Box.CLOSED) {
            flagMap.set(coord, Box.OPENED);
            transitToController(coord);
        }
    }

    /**
     * setNoBombToFlagedSafeBox()
     * 渡された座標のマスにフラグが立てられていたが実際は地雷がない時
     * @param coord 座標
     */
    void setNoBombToFlagedSafeBox(Coord coord) {
        if (flagMap.get(coord) == Box.FLAGED) {
            flagMap.set(coord, Box.NOBOMB);
            transitToController(coord);
        }
    }

    /**
     * getCountOfFlageBoxesAround()
     * 周辺でフラグが立っている数を返す
     * @param coord 座標
     * @return count 周辺のフラグが立ってるマスの数
     */
    int getCountOfFlageBoxesAround(Coord coord) {
        int count = 0;
        for (Coord around : Ranges.getCoordsAround(coord))
            if (flagMap.get(around) == Box.FLAGED)
                count++;
        return count;
    }

    /**
     * getCountOfFlagedBoxes()
     * フラグが立っているマス目の数を返す
     * @return countOfFlagedBoxes
     */
    int getCountOfFlagedBoxes() {
        return countOfFlagedBoxes;
    }
}
