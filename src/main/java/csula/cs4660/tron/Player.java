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
    private static Node[][] nodeBoard = new Node[30][20];
    private static int myCurX;
    private static int myCurY;
    private static boolean goLeft = true;
    private static boolean goUp = true;
    private static boolean goRight = true;
    private static boolean goDown = true;

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);

        startTime = System.currentTimeMillis();

        initializeBoardData();

        /*
        //Testing spaceFill method
        //I think it works!
        List<Node> testList = new ArrayList<Node>();
        testList.add(nodeBoard[5][13]);
        testList.add(nodeBoard[4][12]);
        testList.add(nodeBoard[6][12]);
        spaceFill(testList, nodeBoard[5][12]);

        long processingTime = (System.currentTimeMillis() - startTime) % 1000;
        System.err.println(processingTime);
        */
        // game loop
        /*
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
        */
    }

    public static int spaceFill(List<Node> nodeList, Node playersNode){
        int[][] tempBoard = board;

        List<Queue<Node>> allQueues = new ArrayList<Queue<Node>>();
        for (Node n: nodeList){
            Queue<Node> q = new LinkedList<Node>();
            q.add(n);
            allQueues.add(q);
        }

        Queue<Node> playersQueue = new LinkedList<Node>();
        playersQueue.add(playersNode);

        int cost = -1;

        while (!playersQueue.isEmpty()){
            for (Queue<Node> queue : allQueues){
                if(!queue.isEmpty()){
                    Node currentNode = queue.poll();
                    if(tempBoard[currentNode.getX()][currentNode.getY()] != 10) {
                        tempBoard[currentNode.getX()][currentNode.getY()] = 10;
                        List<Node> neighboringNode = currentNode.getNeighbors();
                        for (Node possibleMoves : neighboringNode) {
                            if (tempBoard[possibleMoves.getX()][possibleMoves.getY()] == -1) {
                                queue.add(nodeBoard[possibleMoves.getX()][possibleMoves.getY()]);
                            }
                        }
                    }
                }
            }
            Node currentNode = playersQueue.poll();
            if(tempBoard[currentNode.getX()][currentNode.getY()] != 10) {
                cost++;
                tempBoard[currentNode.getX()][currentNode.getY()] = 10;
                for (Node possibleMoves : currentNode.getNeighbors()) {
                    if (tempBoard[possibleMoves.getX()][possibleMoves.getY()] == -1) {
                        playersQueue.add(nodeBoard[possibleMoves.getX()][possibleMoves.getY()]);
                    }
                }
            }

        }
        System.out.println(cost);
        return cost;
    }

    public static void initializeBoardData(){
        for (int [] row: board){
            Arrays.fill(row, -1);
        }
        for (int row = 0; row < 30; row++){
            for (int col = 0; col < 20; col++){
                nodeBoard[row][col] = new Node(row, col);
            }
        }

        for (int row = 0; row < 30; row++){
            for (int col = 0; col < 20; col++){
                List<Node> neighbors = new ArrayList<Node>();
                if (row != 0){
                    //node above
                    neighbors.add(nodeBoard[row - 1][col]);
                }

                if (col != 19){
                    //node Right
                    neighbors.add(nodeBoard[row][col + 1]);
                }

                if (row != 29){
                    //node down
                    neighbors.add(nodeBoard[row + 1][col]);
                }
                if (col != 0){
                    //node left
                    neighbors.add(nodeBoard[row][col - 1]);
                }

                nodeBoard[row][col].setNeighbors(neighbors);
            }
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


    public static class Node{
        public int x;
        public int y;
        public int cost;
        public List<Node> neighbors;

        public Node(int xIn, int yIn){
            x = xIn;
            y = yIn;
            cost = 0;
        }

        public int getX(){
            return x;
        }
        public int getY(){
            return y;
        }
        public int getCost(){
            return cost;
        }
        public void setCost(int newCost){
            cost = newCost;
        }
        public void setNeighbors(List<Node> listIn){
            neighbors = listIn;
        }
        public List<Node> getNeighbors(){
            return neighbors;
        }
    }
}

