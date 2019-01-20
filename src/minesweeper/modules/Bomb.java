package minesweeper.modules;

/**
 * class Bomb
 * 地雷に関する操作
 */
class Bomb {
    private Matrix bombMap; // 地雷の盤面
    private int totalBombs; // 地雷の数
    private boolean isWiredWithFXMLObjects; //
    private Coord firstOpenedCoord;

    /**
     * Bomb()
     * 渡された数の地雷を初期化する
     * @param totalBombs int
     */
    Bomb(int totalBombs) {
        this.totalBombs = totalBombs;
        fixBombsCount();
    }

    /**
     * start()
     * 地雷を配置する
     */
    void start(Coord coord) {
        firstOpenedCoord = coord;
        bombMap = new Matrix(Box.ZERO);
        for (int j = 0; j < totalBombs; j++)
            placeBomb();

        if(isWiredWithFXMLObjects)
            TransitController.restart();
        else isWiredWithFXMLObjects = true;
    }

    /**
     * get()
     * 渡された座標のマスを返す
     * @param coord 座標
     * @return box
     */
    Box get(Coord coord) {
        return bombMap.get(coord);
    }

    /**
     * fixBombsCount()
     * 指定された地雷の数がマス目の数の半分より大きい時は、地雷の数をマス目の数の半分に修正する
     */
    private void fixBombsCount() {
        int maxBombs = Ranges.getSize().x * Ranges.getSize().y /2;
        if (totalBombs > maxBombs)
            totalBombs = maxBombs;
    }

    /**
     * placeBomb()
     * ランダムな座標に地雷を配置する
     */
    private void placeBomb() {
        while (true) {
            Coord coord = Ranges.getRandomCoord();
            if(Box.BOMB == bombMap.get(coord)) // すでに置かれてたら
                continue;
            if(firstOpenedCoord.equals(coord)) // 最初に開けるマスなら
                continue;
            bombMap.set(coord, Box.BOMB);
            incNumbersAroundBomb(coord);
            break;
        }
    }

    /**
     * incNumbersAroundBomb()
     * 渡された座標の周囲で地雷がないマスの値をインクリメントする
     * @param coord Coord
     */
    private void incNumbersAroundBomb(Coord coord) {
        for(Coord around : Ranges.getCoordsAround(coord))
            if(Box.BOMB != bombMap.get(around)) // 周囲が地雷ではない時
                bombMap.set(around, bombMap.get(around).getNextNumberBox());
    }

    /**
     * getTotalBombs()
     * 地雷の数を返す
     * @return totalBombs
     */
    int getTotalBombs() {
        return totalBombs;
    }
}
