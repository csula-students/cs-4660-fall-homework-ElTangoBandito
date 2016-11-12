package csula.cs4660.tron;

import java.util.*;
import java.io.*;
import java.math.*;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
class Player {
    private static long startTime;
    private static int[][] board = new int[30][20];
    private static int myCurX;
    private static int myCurY;
    private static boolean goLeft = true;
    private static boolean goUp = true;
    private static boolean goRight = true;
    private static boolean goDown = true;

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);

        for (int [] row: board){
            Arrays.fill(row, -1);
        }
        // game loop
        while (true) {
            startTime = System.currentTimeMillis();
            int N = in.nextInt(); // total number of players (2 to 4).
            int P = in.nextInt(); // your player number (0 to 3).
            for (int i = 0; i < N; i++) {
                int X0 = in.nextInt(); // starting X coordinate of lightcycle (or -1)
                int Y0 = in.nextInt(); // starting Y coordinate of lightcycle (or -1)
                int X1 = in.nextInt(); // starting X coordinate of lightcycle (can be the same as X0 if you play before this player)
                int Y1 = in.nextInt(); // starting Y coordinate of lightcycle (can be the same as Y0 if you play before this player)

                board[X1][Y1] = i;
                if(i == P){
                    myCurX = X1;
                    myCurY = Y1;
                    //System.err.println(X1);
                    //System.err.println(Y1);
                }
                ///System.err.println(X1);
                //System.err.println(Y1);
                //System.err.println(board[X1][Y1]);
            }

            // Write an action using System.out.println()
            // To debug: System.err.println("Debug messages...");
            //System.err.println(getMove());
            resetPossibleMoves();
            checkPossibleMoves();
            System.out.println(getMove()); // A single line with UP, DOWN, LEFT or RIGHT
            long processingTime = (System.currentTimeMillis() - startTime) % 1000;
            System.err.println(processingTime);
        }
    }

    public static void resetPossibleMoves(){
        goLeft = true;
        goRight = true;
        goUp = true;
        goDown = true;
    }

    public static void checkPossibleMoves(){
        if (myCurX == 0) {
            goLeft = false;
        }
        else if (board[myCurX - 1][myCurY] != -1){
            goLeft = false;
        }

        if (myCurY == 0) {
            goUp = false;
        }
        else if (board[myCurX][myCurY - 1] != -1){
            goUp = false;
        }

        if (myCurX == 29){
            goRight = false;
        }
        else if (board[myCurX + 1][myCurY] != -1){
            goRight = false;
        }

        if (myCurY == 19){
            goDown = false;
        }
        else if (board[myCurX][myCurY + 1] != -1){
            goDown = false;
        }
    }

    public static String getMove(){
        String result = "";
        List<String> moveList = new ArrayList<String>();
        if (goLeft){
            moveList.add("LEFT");
        }
        if(goUp){
            moveList.add("UP");
        }
        if(goRight){
            moveList.add("RIGHT");
        }
        if(goDown){
            moveList.add("DOWN");
        }
        if(moveList.size() > 0){
            Random rand = new Random();
            int randomMove = rand.nextInt(moveList.size());
            result = moveList.get(randomMove);
        }
        else{
            System.err.println("No possible moves, you lost.");
            return "LEFT";
        }
        return result;
    }
}

