package csula.cs4660.graphs.searches;

import csula.cs4660.graphs.Edge;
import csula.cs4660.graphs.Graph;
import csula.cs4660.graphs.Node;

import java.util.List;
import java.util.Queue;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Stack;

/**
 * Breadth first search
 */
public class BFS implements SearchStrategy {
    @Override
    public List<Edge> search(Graph graph, Node source, Node dist) {
        Queue<Node> nodeQueue = new LinkedList<Node>();
        List<Node> visited = new ArrayList<Node>();
        List<Edge> result = new ArrayList<Edge>();
        //Queue<Edge> accum = new LinkedList<Edge>();
        nodeQueue.add(source);
        while (!nodeQueue.isEmpty()){
            Node currentNode = nodeQueue.poll();
            if (currentNode.equals(dist)) {
                visited.add(currentNode);
                break;
            }
            if (!visited.contains(currentNode)){
                visited.add(currentNode);
                List<Node> childs = graph.neighbors(currentNode);
                for (Node node: childs){
                    if (!visited.contains(node)){
                        nodeQueue.add(node);
                    }
                }
            }
        }
        System.out.println(visited);


        //graph.recursiveBFS(source, dist, nodeQueue, visited, result, accum);
        return result;
    }
}
