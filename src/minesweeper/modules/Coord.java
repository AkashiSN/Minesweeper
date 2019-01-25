package minesweeper.modules;

/**
 * class Coord
 * 座標を保持する
 */
public class Coord {
    public int x; // x座標
    public int y; // y座標

    /**
     * Coord()
     * 座標をセットして初期化
     * @param x x座標
     * @param y y座標
     */
    public Coord(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * equals()
     * 仮引数が座標の場合、一致しているかどうかを返す
     * @param o Object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if(o instanceof  Coord){
            Coord coord = (Coord) o;
            return this.x == coord.x && this.y == coord.y;
        }
        return super.equals(o);
    }

    /**
     * show()
     * 座標を表示した
     * @return 座標
     */
    public String show(){
        return "(" + x + "," + y + ")";
    }

}
