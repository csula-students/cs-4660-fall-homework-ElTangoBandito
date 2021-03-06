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
    private static Map<Integer, Boolean> statusMap = new HashMap<Integer, Boolean>();

    private static boolean playerOneStart = true;
    private static boolean playerTwoStart = true;
    private static boolean playerThreeStart = true;
    private static boolean playerFourStart = true;

    private static boolean tooCloseForComfort = false;

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);

        startTime = System.currentTimeMillis();

        initializeBoardData();
        statusMap.put(0, true);
        statusMap.put(1, true);
        statusMap.put(2, true);
        statusMap.put(3, true);


        //Testing spaceFill method
        //I think it works!
        /*
        List<Node> testList = new ArrayList<Node>();
        testList.add(nodeBoard[5][13]);
        testList.add(nodeBoard[4][12]);
        testList.add(nodeBoard[6][12]);
        board[5][13] = 2;
        board[6][12] = 2;
        board[4][12] = 2;
        setFloodFillCosts(testList, nodeBoard[5][12], board);
        miniMax(nodeBoard[5][12], 2, Integer.MIN_VALUE, Integer.MAX_VALUE, true);
        checkPossibleMoves();
        performMovesBasedOnCost(nodeBoard[5][12]);

        /*
        if(goLeft || goRight || goDown || goUp){
            performMovesBasedOnCost(nodeBoard[4][13]);
        }



        //spaceFill(testList, nodeBoard[5][12]);

        long processingTime = (System.currentTimeMillis() - startTime) % 1000;
        System.err.println(processingTime);
        */
        // game loop



        while (true) {
            startTime = System.currentTimeMillis();

            List<Node> enemyCurrentLocation =  new ArrayList<Node>();
            Node playerCurrentNode = nodeBoard[0][0]; //intialization

            int N = in.nextInt(); // total number of players (2 to 4).
            int P = in.nextInt(); // your player number (0 to 3).
            for (int i = 0; i < N; i++) {
                int X0 = in.nextInt(); // starting X coordinate of lightcycle (or -1)
                int Y0 = in.nextInt(); // starting Y coordinate of lightcycle (or -1)
                int X1 = in.nextInt(); // starting X coordinate of lightcycle (can be the same as X0 if you play before this player)
                int Y1 = in.nextInt(); // starting Y coordinate of lightcycle (can be the same as Y0 if you play before this player)

                if(i == 0 && playerOneStart){
                    playerOneStart = false;
                    board[X0][Y0] = i;
                }
                if(i == 1 && playerTwoStart){
                    playerTwoStart = false;
                    board[X0][Y0] = i;
                }
                if(i == 2 && playerThreeStart){
                    playerThreeStart = false;
                    board[X0][Y0] = i;
                }
                if(i == 3 && playerFourStart){
                    playerFourStart = false;
                    board[X0][Y0] = i;
                }

                if(X0 == -1){
                    if(statusMap.get(i)){
                        clearDeadSpaces(i);
                        statusMap.put(P, false);
                    }
                }
                else {
                    board[X1][Y1] = i;
                    if (i == P) {
                        myCurX = X1;
                        myCurY = Y1;
                        playerCurrentNode = nodeBoard[X1][Y1];
                    } else {
                        enemyCurrentLocation.add(nodeBoard[X1][Y1]);
                    }
                }
            }
            //findFloodFillMoves(enemyCurrentLocation, playerCurrentNode, board);
            //System.err.println(isWalledOff(enemyCurrentLocation, playerCurrentNode));
            System.err.print("Player current location: " + myCurX + " " + myCurY);
            clearNodeCosts();
            resetPossibleMoves();
            checkPossibleMoves();
            if(!isWalledOff(enemyCurrentLocation, playerCurrentNode)) {
                if (goLeft || goRight || goDown || goUp) {
                    int numberOfMoves = 0;
                    if (goLeft) {
                        numberOfMoves++;
                    }
                    if (goRight) {
                        numberOfMoves++;
                    }
                    if (goUp) {
                        numberOfMoves++;
                    }
                    if (goDown) {
                        numberOfMoves++;
                    }
                    if (numberOfMoves > 1) {
                        checkIfClose(enemyCurrentLocation, playerCurrentNode);
                        if(tooCloseForComfort){
                            System.err.println("Minimax!!");
                            setFloodFillCosts(enemyCurrentLocation, playerCurrentNode, board);
                            miniMax(playerCurrentNode, 2, Integer.MIN_VALUE, Integer.MAX_VALUE, true);
                            performMovesBasedOnCost(playerCurrentNode);
                            tooCloseForComfort = false;
                        }
                        else{
                            System.err.println("Aggressive mode!");
                            findFloodFillMoves(enemyCurrentLocation, playerCurrentNode, board);
                            performMovesBasedOnCost(playerCurrentNode);

                            //TODO; AGGRESSIVE ALGORITHM
                        }
                    } else {
                        System.err.println("Only one move available.");
                        System.out.println(getMove());
                        //findFloodFillMoves(enemyCurrentLocation, playerCurrentNode, board);
                    }
                } else {
                    System.err.println("Using random move");
                    System.out.println(getMove());
                }
            }
            else{
                System.err.println("Survival mode!");
                findFloodFillMoves(enemyCurrentLocation, playerCurrentNode, board);
                performMovesBasedOnCost(playerCurrentNode);
            }
            long processingTime = (System.currentTimeMillis() - startTime) % 1000;
            System.err.println(processingTime);
            // Write an action using System.out.println()
            // To debug: System.err.println("Debug messages...");
            //System.err.println(getMove());
            //resetPossibleMoves();
            //checkPossibleMoves();
            //System.out.println(getMove()); // A single line with UP, DOWN, LEFT or RIGHT

        }



    }

    public static void checkIfClose(List<Node> enemyList, Node playerPos){
        for(Node node : enemyList){
            if(node.getX() >= playerPos.getX() - 3 && node.getX() <= playerPos.getX() + 3){
                if(node.getY() >= playerPos.getY() - 3 && node.getY() <= playerPos.getY() + 3){
                    tooCloseForComfort = true;
                }
            }
        }
    }

    public static void clearDeadSpaces(int playerNumber){
        for (int row = 0; row < 30; row++){
            for (int col = 0; col < 20; col++){
                if(board[row][col] == playerNumber){
                    board[row][col] = -1;
                }
            }
        }
    }

    public static void performMovesBasedOnCost(Node playerNode){
        List<Node> neighbors = playerNode.getNeighbors();
        Node maxNode = nodeBoard[neighbors.get(0).getX()][neighbors.get(0).getY()];
        for(Node node: neighbors){
            System.err.print(node.getX() + ", " + node.getY() + ": " + nodeBoard[node.getX()][node.getY()].getCost());
            System.err.println();
            System.err.println("Current max cost: " + maxNode.getCost());
            if(nodeBoard[node.getX()][node.getY()].getCost() > maxNode.getCost()){
                maxNode = node;
            }
        }

        int maxX = maxNode.getX();
        int maxY = maxNode.getY();

        if(maxX > playerNode.getX()){
            System.out.println("RIGHT");
        }
        if(maxX < playerNode.getX()){
            System.out.println("LEFT");
        }
        if(maxY > playerNode.getY()){
            System.out.println("DOWN");
        }
        if(maxY < playerNode.getY()){
            System.out.println("UP");
        }

    }

    public static void clearNodeCosts(){
        for (int row = 0; row < 30; row++){
            for (int col = 0; col < 20; col++){
                nodeBoard[row][col].setCost(0);
            }
        }
    }

    public static int[][] copyBoard(int[][] boardIn){
        int[][] newBoard = new int[30][20];
        for (int row = 0; row < 30; row++){
            for (int col = 0; col < 20; col++){
                newBoard[row][col] = boardIn[row][col];
            }
        }
        return newBoard;
    }

    public static int miniMax(Node root, Integer depth, Integer alpha, Integer beta, Boolean max){
        if(depth == 0){
            //System.err.println(nodeBoard[root.getX()][root.getY()].getCost());
            return nodeBoard[root.getX()][root.getY()].getCost();
        }
        else{
            if(max){
                List<Node> validChildrens = new ArrayList<Node>();
                for(Node node : root.getNeighbors()){
                    if (board[node.getX()][node.getY()] == -1 && depth == 2){
                        validChildrens.add(node);
                    }
                }
                int bestValue = Integer.MIN_VALUE;
                if(validChildrens.isEmpty()){
                    bestValue = 1;
                }
                for (Node node : validChildrens) {
                    int currentDepth = depth - 1;
                    int nextValue = miniMax(nodeBoard[node.getX()][node.getY()], currentDepth, alpha, beta, false);
                    bestValue = Math.max(bestValue, nextValue);
                    alpha = Math.max(alpha, bestValue);
                    if (beta <= alpha){
                        break;
                    }
                }
                root.setCost(bestValue);
                return bestValue;
            }else{
                List<Node> validChildrens = new ArrayList<Node>();
                for(Node node : root.getNeighbors()){
                    if (nodeBoard[node.getX()][node.getY()].getCost() != 0 && depth == 1 && board[node.getX()][node.getY()] == -1){
                        validChildrens.add(node);
                    }
                }
                int bestValue = Integer.MAX_VALUE;
                if(validChildrens.isEmpty()){
                    bestValue = 1;
                }
                for (Node node : validChildrens) {
                    int currentDepth = depth - 1;
                    int nextValue = miniMax(nodeBoard[node.getX()][node.getY()], currentDepth, alpha, beta, true);
                    bestValue = Math.min(bestValue, nextValue);
                    alpha = Math.min(alpha, bestValue);
                    if (beta <= alpha){
                        break;
                    }
                }
                //System.err.println("Minimum value: " + bestValue);
                root.setCost(bestValue);
                return bestValue;
            }
        }
    }

    public static void setFloodFillCosts(List<Node> nodeList, Node playersNode, int[][] boardIn){
        int[][] tempBoard = copyBoard(boardIn);
        int[][] futureChildrenBoard = copyBoard(boardIn);

        List<Queue<Node>> allQueues = new ArrayList<Queue<Node>>();
        if (!nodeList.isEmpty()) {
            for (Node n : nodeList) {
                Queue<Node> q = new LinkedList<Node>();
                tempBoard[n.getX()][n.getY()] = 10;
                for (Node childrenNode : n.getNeighbors()) {
                    if (tempBoard[childrenNode.getX()][childrenNode.getY()] == -1) {
                        q.add(childrenNode);
                        //put children nodes into queue if they are not already occupied
                    }
                }
                allQueues.add(q);
            }
        }

        Queue<Node> playersQueue = new LinkedList<Node>();
        for (Node childrenNode : playersNode.getNeighbors()){
            if(tempBoard[childrenNode.getX()][childrenNode.getY()] == -1){
                playersQueue.add(childrenNode);
            }
        }


        Queue<Node> playerNextNode = new LinkedList<Node>();
        while (!playersQueue.isEmpty()){
            List<Node> enemyNodes = new ArrayList<Node>();
            for (Queue<Node> queue : allQueues){
                if(!queue.isEmpty()){
                    Node currentNode = queue.poll();
                    while(futureChildrenBoard[currentNode.getX()][currentNode.getY()] != -1 && !queue.isEmpty()){
                        currentNode = queue.poll();
                    }
                    if(futureChildrenBoard[currentNode.getX()][currentNode.getY()] == -1) {
                        futureChildrenBoard[currentNode.getX()][currentNode.getY()] = 10;
                        enemyNodes.add(currentNode);
                    }
                }
            }
            Node currentNode = playersQueue.poll();
            while(futureChildrenBoard[currentNode.getX()][currentNode.getY()] != -1 && !playersQueue.isEmpty()){
                currentNode = playersQueue.poll();
            }
            if(futureChildrenBoard[currentNode.getX()][currentNode.getY()] == -1) {
                futureChildrenBoard[currentNode.getX()][currentNode.getY()] = 10;
                playerNextNode.add(currentNode);
            }

            if(!playerNextNode.isEmpty()){
                findFloodFillMoves(enemyNodes, playerNextNode.poll(), tempBoard);
            }

        }


    }

    public static void findFloodFillMoves(List<Node> nodeList, Node playersNode, int[][] boardIn){
        int[][] tempBoard = copyBoard(boardIn);
        int[][] futureChildrenBoard = copyBoard(boardIn);

        List<Queue<Node>> allQueues = new ArrayList<Queue<Node>>();
        if (!nodeList.isEmpty()) {
            for (Node n : nodeList) {
                Queue<Node> q = new LinkedList<Node>();
                tempBoard[n.getX()][n.getY()] = 10;
                for (Node childrenNode : n.getNeighbors()) {
                    if (tempBoard[childrenNode.getX()][childrenNode.getY()] == -1) {
                        q.add(childrenNode);
                        //put children nodes into queue if they are not already occupied
                    }
                }
                allQueues.add(q);
            }
        }

        Queue<Node> playersQueue = new LinkedList<Node>();
        for (Node childrenNode : playersNode.getNeighbors()){
            if(tempBoard[childrenNode.getX()][childrenNode.getY()] == -1){
                playersQueue.add(childrenNode);
            }
        }


        Queue<Node> playerNextNode = new LinkedList<Node>();
        while (!playersQueue.isEmpty()){
            List<Node> enemyNodes = new ArrayList<Node>();
            for (Queue<Node> queue : allQueues){
                if(!queue.isEmpty()){
                    Node currentNode = queue.poll();
                    while(futureChildrenBoard[currentNode.getX()][currentNode.getY()] != -1 && !queue.isEmpty()){
                        currentNode = queue.poll();
                    }
                    if(futureChildrenBoard[currentNode.getX()][currentNode.getY()] == -1) {
                        futureChildrenBoard[currentNode.getX()][currentNode.getY()] = 10;
                        enemyNodes.add(currentNode);
                    }
                }
            }
            Node currentNode = playersQueue.poll();
            while(futureChildrenBoard[currentNode.getX()][currentNode.getY()] != -1 && !playersQueue.isEmpty()){
                currentNode = playersQueue.poll();
            }
            if(futureChildrenBoard[currentNode.getX()][currentNode.getY()] == -1) {
                futureChildrenBoard[currentNode.getX()][currentNode.getY()] = 10;
                playerNextNode.add(currentNode);
            }

            if(!playerNextNode.isEmpty()){
                spaceFill(enemyNodes, playerNextNode.poll(), tempBoard);
            }

        }


    }

    public static int spaceFill(List<Node> nodeList, Node playersNode, int[][] boardIn) {
        int[][] tempBoard = copyBoard(boardIn);

        List<Queue<Node>> allQueues = new ArrayList<Queue<Node>>();
        if (!nodeList.isEmpty()) {
            for (Node n : nodeList) {
                Queue<Node> q = new LinkedList<Node>();
                q.add(n);
                allQueues.add(q);
            }
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

        //System.err.print(playersNode.getX() + " ");
        //System.err.print(playersNode.getY() + " ");
        //System.err.println(cost);
        nodeBoard [playersNode.getX()][playersNode.getY()].setCost(cost);
        return cost;
    }

    public static boolean isWalledOff(List<Node> nodeList, Node playersNode){
        int[][] tempBoard = copyBoard(board);

        List<Queue<Node>> allQueues = new ArrayList<Queue<Node>>();
        if (!nodeList.isEmpty()) {
            for (Node n : nodeList) {
                Queue<Node> q = new LinkedList<Node>();
                q.add(n);
                allQueues.add(q);
            }
        }

        Queue<Node> playersQueue = new LinkedList<Node>();
        playersQueue.add(playersNode);


        while (!playersQueue.isEmpty()){
            for (Queue<Node> queue : allQueues){
                if(!queue.isEmpty()){
                    Node currentNode = queue.poll();
                    if(tempBoard[currentNode.getX()][currentNode.getY()] != 20) {
                        tempBoard[currentNode.getX()][currentNode.getY()] = 20;
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
                if(tempBoard[currentNode.getX()][currentNode.getY()] == 20) {
                    return false;
                }
                    tempBoard[currentNode.getX()][currentNode.getY()] = 10;
                for (Node possibleMoves : currentNode.getNeighbors()) {
                    if (tempBoard[possibleMoves.getX()][possibleMoves.getY()] == -1) {
                        playersQueue.add(nodeBoard[possibleMoves.getX()][possibleMoves.getY()]);
                    }
                }
            }
        }
        return true;
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

