package minesweeper.modules;

/**
 * class Matrix
 * 盤面を保持する
 */
class Matrix {
    private Box[][] matrix; // 盤面

    /**
     * Matrix()
     * 渡されたマスで全座標を初期化する
     * @param defaultBox Box
     */
     Matrix(Box defaultBox){
        matrix = new Box[Ranges.getSize().x][Ranges.getSize().y];
        for(Coord coord : Ranges.getAllCoords())
            matrix[coord.x][coord.y] =  defaultBox;
    }

    /**
     * get()
     * 渡された座標のBoxを返す
     * @param coord 座標
     * @return 範囲内にあったらBox、以外だったらnull
     */
    Box get(Coord coord){
        if(Ranges.inRange(coord))
            return matrix[coord.x][coord.y];
        return null;
    }

    /**
     * set()
     * 渡された座標が範囲内だったらBoxをセットする
     * @param coord 座標
     * @param box マス
     */
    void set(Coord coord, Box box){
        if(Ranges.inRange(coord))
            matrix[coord.x][coord.y] = box;
    }
}
