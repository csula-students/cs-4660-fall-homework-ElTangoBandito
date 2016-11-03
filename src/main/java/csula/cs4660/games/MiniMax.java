package csula.cs4660.games;

import csula.cs4660.games.models.MiniMaxState;
import csula.cs4660.graphs.Graph;
import csula.cs4660.graphs.Node;

public class MiniMax {
    public static Node getBestMove(Graph graph, Node root, Integer depth, Boolean max) {
        // TODO: implement minimax to retrieve best move
        // NOTE: you should mutate graph and node as you traverse and update value
        /*
        Node node = graph.getNode(root).get();
        node.setNode(new MiniMaxState(0, 100));
        MiniMaxState mini= (MiniMaxState)node.getData();
        MiniMaxState test = (MiniMaxState)graph.getNode(new Node<>(new MiniMaxState(0, 100))).get().getData();
        System.out.println(test.getValue());
        */
        minMax(graph, root, depth, max);
        return getNextMove(graph, root, max);
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
