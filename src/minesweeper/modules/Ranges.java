package minesweeper.modules;

import java.util.ArrayList;
import java.util.Random;

/**
 * class Ranges
 * 盤面に関する操作
 */
public class Ranges {
    private static Coord size; // 盤面のサイズ
    private static ArrayList<Coord> allCoords; // 盤面の全座標リスト
    private static Random random = new Random(); // 乱数

    /**
     * setSize()
     * 盤面の全座標のリストを作成する
     * @param _size 盤面のサイズ
     */
    static void setSize(Coord _size) {
        size = _size;
        allCoords = new ArrayList<>();
        for (int y = 0; y < size.y; y++)
            for (int x = 0; x < size.x; x++)
                allCoords.add(new Coord(x, y));
    }

    /**
     * getSize()
     * 盤面のサイズを返す
     * @return size Coord
     */
    public static Coord getSize() {
        return size;
    }

    /**
     * getAllCoords()
     * 盤面の全座標のリストを返す
     * @return allCoords ArrayList<Coord>
     */
    public static ArrayList<Coord> getAllCoords() {
        return allCoords;
    }

    /**
     * inRange()
     * 渡された座標が範囲内にあるかどうか
     * @param coord 座標
     * @return boolean
     */
    static boolean inRange(Coord coord) {
        return coord.x >= 0 && coord.x < size.x &&
                coord.y >= 0 && coord.y < size.y;
    }

    /**
     * getRandomCoord()
     * 盤面のサイズ内のランダムな座標を返す
     * @return Coord
     */
    static Coord getRandomCoord() {
        return new Coord(random.nextInt(size.x),
                random.nextInt(size.y));
    }

    /**
     * getCoordsAround()
     * 渡された座標の周囲８マスを調べて、範囲内にあり自分自身ではない座標のリストを返す
     * @param coord 座標
     * @return list ArrayList<Coord>
     */
    static ArrayList<Coord> getCoordsAround(Coord coord) {
        Coord around;
        ArrayList<Coord> list = new ArrayList<>();
        for (int x = coord.x - 1; x <= coord.x + 1; x++)
            for (int y = coord.y - 1; y <= coord.y + 1; y++)
                if (inRange(around = new Coord(x, y))) // 範囲内かどうか
                    if (!around.equals(coord)) // 自分自身じゃない時
                        list.add(around);
        return list;
    }

}
