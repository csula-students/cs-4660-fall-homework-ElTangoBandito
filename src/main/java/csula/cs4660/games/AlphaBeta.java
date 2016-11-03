package csula.cs4660.games;

import csula.cs4660.games.models.MiniMaxState;
import csula.cs4660.graphs.Graph;
import csula.cs4660.graphs.Node;

public class AlphaBeta {
    public static Node getBestMove(Graph graph, Node source, Integer depth, Integer alpha, Integer beta, Boolean max) {
        // TODO: implement your alpha beta pruning algorithm here
        minMax(graph, source, depth, max);
        return getNextMove(graph, source, max);
    }

    public static Node getNextMove(Graph graph, Node root, boolean max){
        Node result = new Node<>(new MiniMaxState(0, 0));
        if (max){
            int bestValue = Integer.MIN_VALUE;
            for (Node node : graph.neighbors(root)) {
                MiniMaxState currentState = (MiniMaxState)node.getData();
                bestValue = Math.max(currentState.getValue(), bestValue);
            }
            for (Node node : graph.neighbors(root)) {
                MiniMaxState currentState = (MiniMaxState)node.getData();
                if(bestValue == currentState.getValue()){
                    result = node;
                }
            }
        }
        else{
            int bestValue = Integer.MAX_VALUE;
            for (Node node : graph.neighbors(root)) {
                MiniMaxState currentState = (MiniMaxState)node.getData();
                bestValue = Math.min(currentState.getValue(), bestValue);
            }
            for (Node node : graph.neighbors(root)) {
                MiniMaxState currentState = (MiniMaxState)node.getData();
                if(bestValue == currentState.getValue()){
                    result = node;
                }
            }
        }
        return result;
    }

    public static int minMax(Graph graph, Node root, Integer depth, Boolean max){
        if(depth == 0){
            MiniMaxState miniMax = (MiniMaxState) root.getData();
            return miniMax.getValue();
        }
        else{
            if (max) {
                int bestValue = Integer.MIN_VALUE;
                for (Node node : graph.neighbors(root)) {
                    int currentDepth = depth - 1;
                    int nextValue = minMax(graph, node, currentDepth, false);
                    bestValue = Math.max(bestValue, nextValue);
                }
                MiniMaxState currentState = (MiniMaxState) root.getData();
                MiniMaxState newState = new MiniMaxState(currentState.getIndex(), bestValue);
                root.setNode(newState);
                return bestValue;
            }
            else{
                int bestValue = Integer.MAX_VALUE;
                for (Node node : graph.neighbors(root)) {
                    int currentDepth = depth - 1;
                    int nextValue = minMax(graph, node, currentDepth, false);
                    bestValue = Math.min(bestValue, nextValue);
                }
                MiniMaxState currentState = (MiniMaxState) root.getData();
                MiniMaxState newState = new MiniMaxState(currentState.getIndex(), bestValue);
                root.setNode(newState);
                return bestValue;
            }
        }
    }
}
