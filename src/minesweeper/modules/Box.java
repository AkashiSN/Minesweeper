package minesweeper.modules;

/**
 * Enum Box
 * 各マス目の定義
 */
public enum Box {
    ZERO, // 0 地雷がない
    NUM1, // 1
    NUM2, // 2
    NUM3, // 3
    NUM4, // 4
    NUM5, // 5
    NUM6, // 6
    NUM7, // 7
    NUM8, // 8
    BOMB, // 地雷のマス
    OPENED, // 開けられたマス
    CLOSED, // 閉じているマス
    FLAGED, // フラグが建てられたます
    BOMBED, // 地雷が爆発したマス
    NOBOMB, // フラグが立てられたが、地雷がなかったマス
    KNOANSWERED, // 漢字問題が解かれていない
    KCORRECTED, // 漢字問題を正解した
    KINCORRECTED; // 漢字問題は不正解

    public Object image; // 盤面に表示する画像

    /**
     * getNextNumberBox()
     * @return 次のBoxの数字
     */
    Box getNextNumberBox() {
        return Box.values()[this.ordinal()+1];
    }

    /**
     * getNumber()
     * @return 現在のBoxの順番
     */
    int getNumber() {
        return this.ordinal();
    }
}
