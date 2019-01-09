package minesweeper;

import java.util.*;
import java.io.*;

public class MineSweeper{
    private String field[][];//表示するマス
    private int hiddenField[][];//地雷諸々を記録
    private boolean fieldFlag[][];//旗の有無を記録
    private int fieldWidth;
    private int fieldHeight;
    private int mineNum;//設置する地雷の数
    private int mineRemain;//旗を立てるたびに減らす
    private boolean flagFlag;//flagモードとopenモードの切り替え
    private String message;//画面更新後に表示する
    private boolean endFlag;//終わる時に上げる

    public MineSweeper(){
        fieldWidth = 10;
        fieldHeight = 10;
        mineNum = fieldWidth*fieldHeight/4;//マス目の1/4に地雷設置
        mineRemain = 0;
        flagFlag = false;
        field = new String[fieldWidth][fieldHeight];
        hiddenField = new int[fieldWidth][fieldHeight];
        fieldFlag = new boolean[fieldWidth][fieldHeight];
        for(int i = 0; i < fieldHeight; i++){//盤面の初期化
            for(int j = 0; j < fieldWidth; j++){
                field[j][i] = "-";
                hiddenField[j][i] = 0;
                fieldFlag[j][i] = false;
            }
        }
        showField();
        System.out.println("open:o flag:f retire:r,e,q coordinate(x_y)");
        System.out.println("\nOpen(x_y):");//第一手を読む
        Scanner sc0 = new Scanner(System.in);
        int x = sc0.nextInt(), y = sc0.nextInt();
        spreadMine(x, y);//地雷を撒く
        open(x, y);//第一手を開ける
        endFlag = false;
        while(!endFlag){
            showField();//盤面を端末に表示
            System.out.print(message);
            message = "";
            openField();//座標を入力して開けていく
            if(!endFlag)
                ifSweeped();
        }
    }

    public static void main(String[] args){
        MineSweeper ms = new MineSweeper();
    }

    public void showField(){//盤面を表示
        try{
            clearScreen();//盤面を初期化
        }catch(IOException | InterruptedException e){
            System.out.println("no clear");
        }
        String s;
        System.out.print("       ");
        for(int i = 0; i < fieldWidth; i++){
            System.out.print(i+"   ");
            if(i < 10)
                System.out.print(" ");
        }
        System.out.println("\n\n");
        for(int i = 0; i < fieldHeight; i++){
            System.out.print(i+"     ");
            if(i < 10)
                System.out.print(" ");
            for(int j = 0; j < fieldWidth; j++){//地雷の周辺情報を示すが、0ならば空白とし、旗が立っていればF、地雷ならMとして表示
                s = (fieldFlag[j][i]
                        ? "F"
                        : (field[j][i].equals("0")
                        ? " "
                        : (field[j][i].equals("-1")
                        ? "M"
                        : field[j][i])));
                System.out.print(s+"    ");
            }
            System.out.println("    "+i+"\n");
        }
        System.out.print("\n       ");
        for(int i = 0; i < fieldWidth; i++){
            System.out.print(i+"   ");
            if(i < 10)
                System.out.print(" ");
        }
        System.out.println();
    }
    public void clearScreen() throws IOException, InterruptedException {
        new ProcessBuilder("clear").inheritIO().start().waitFor();
    }
    public void spreadMine(int x0, int y0){//地雷の設置
        Random rd = new Random();
        int x, y;
        for(int i = 1; i <= mineNum; i++){
            x = rd.nextInt(fieldWidth);
            y = rd.nextInt(fieldHeight);
            if(hiddenField[x][y] == -1 || (x == x0 && y == y0) || ((x0-1 <= x && x <= x0+1) && (y0-1 <= y && y <= y0+1) && (fieldWidth*fieldHeight-9 >= mineNum))){
                //二重に地雷を置こうとした時or初期値と周囲8マスに置こうとした時置き直し
                i--;
                continue;
            }
            hiddenField[x][y] = -1;
            for(int j = (x > 0 ? x-1 : 0); (j <= x+1 && j < fieldWidth); j++){//周囲のインクリメント
                for(int k = (y > 0 ? y-1 : 0); (k <= y+1 && k < fieldHeight); k++){
                    if(hiddenField[j][k] != -1)
                        hiddenField[j][k]++;
                }
            }
            mineRemain++;
    		/*if(fieldWidth*fieldHeight-i > 0){
    			mineNum = i;
    			break;
    		}*/
        }
        message = "mineRemain: "+mineRemain+"/"+mineNum+"\n";
    }
    public void openField(){//座標を入力してopenかflag
        System.out.println("\nCommand or "+(flagFlag ? "Flag(x_y):" : "Open(x_y):"));
        Scanner sc = new Scanner(System.in);
        String s = sc.next();
        if(s.equals("f")){//fを読んだらflagモードに移行
            flagFlag = true;
            return;
        }else if(s.equals("o")){//oを読んだらopenモードに移行
            flagFlag = false;
            return;
        }else if(s.equals("q") || s.equals("e") || s.equals("r")){//quit,exit,retireなら途中終了
            System.out.println("Retire.");
            endFlag = true;
            return;
        }
        int x = 0, y = 0;
        try{
            x = Integer.parseInt(s);
        }catch(NumberFormatException e){}
        y = sc.nextInt();
        //sc.close();
        if(field[x][y].equals("-")){//探査済みならmessage
            if(flagFlag){//flagモードなら旗を立てる
                flag(x, y);
            }else if(fieldFlag[x][y]){//openモードで旗が立っていたらmessage
                message += "flag standing\n";
            }else{
                open(x, y);
            }
        }else{
            message += "already opened\n";
        }
        message += "mineRemain: "+mineRemain+"/"+mineNum+"\n";
    }
    public void open(int x, int y){//探査し、地雷の有無を可視化
        if(hiddenField[x][y] == -1){//地雷を踏んでいたらループを抜けて終了処理
            for(int i = 0; i < fieldHeight; i++){//旗を全て下ろして盤面を全て表示する
                for(int j = 0; j < fieldWidth; j++){
                    fieldFlag[j][i] = false;
                    field[j][i] = hiddenField[j][i]+"";
                }
            }
            showField();
            System.out.println("Bomb!");
            endFlag = true;
            return;
        }
        field[x][y] = hiddenField[x][y]+"";
        if(hiddenField[x][y] == 0){//開けようとするマスが0なら更に周囲8マスオープン
            for(int i = (x > 0 ? x-1 : 0); (i <= x+1 && i < fieldWidth); i++){
                for(int j = (y > 0 ? y-1 : 0); (j <= y+1 && j < fieldHeight); j++){
                    if(field[i][j].equals("-")/* || field[i][j].equals("F")*/)
                        open(i, j);
                }
            }
        }
    }
    public void flag(int x, int y){
        fieldFlag[x][y] = (fieldFlag[x][y] ? false : true);
        mineRemain = (fieldFlag[x][y] ? mineRemain-1 : mineRemain+1);
    }
    public void ifSweeped(){//ゲームクリアの如何を判定
        boolean flag1 = true, flag2 = true;
        for(int i = 0; i < fieldHeight; i++){
            for(int j = 0; j < fieldWidth; j++){
                if(/*(hiddenField[j][i] == -1 && fieldFlag[j][i] == false) || */(hiddenField[j][i] != -1 && fieldFlag[j][i]))
                    flag1 = false;//地雷以外に旗が立っていないか(地雷全てに旗が立っているか(条件式は下記))
                if(field[j][i].equals("-") && hiddenField[j][i] != -1)
                    flag2 = false;//地雷以外全てを探査したか
            }
        }
        if((flag1 && mineRemain == 0) || flag2){//上記2条件のいずれかを満たせばクリア
            showField();
            endFlag = true;
            System.out.println("Congratulation!!\nAll mine sweeped!");
        }
    }
//
//    public int sumRoundHidden(int row, int column){
//        int sumFound = 0;
//        for(int i = (column > 0 ? column-1 : 0); (i <= column+1 && i < fieldWidth); i++){
//            for(int j = (row > 0 ? row-1 : 0); (j <= row+1 && j < fieldHeight); j++){
//                if(field[j][i].equals("-") && !flagField[j][i])
//                    sumHidden++;
//            }
//        }
//        return sumHidden;
//    }
//    public int sumRoundFlagged(int row, int column){
//        int sumFlagged = 0;
//        for(int i = (column > 0 ? column-1 : 0); (i <= column+1 && i < fieldWidth); i++){
//            for(int j = (row > 0 ? row-1 : 0); (j <= row+1 && j < fieldHeight); j++){
//                if(flagField[j][i])
//                    sumFlagged++;
//            }
//        }
//        return sumFlagged;
//    }
//
//    public void autoSweep(){
//        for(int column = 0; column < fieldHeight; column++){
//            for(int row = 0; row < fieldWidth; row++){
//                for(int i = (column > 0 ? column-1 : 0); (i <= column+1 && i < fieldWidth); i++){
//                    for(int j = (row > 0 ? row-1 : 0); (j <= row+1 && j < fieldHeight); j++){
//                        if(!field[row][column].equals("-") && hiddenField[row][column] == sumRoundHidden(row,column) && field[j][i].equals("-") && !flagField[j][i]){
//                            flag(j, i);
//                        }
//                        if(){}//sumflagged
//                    }
//                }
//            }
//        }
//    }
}