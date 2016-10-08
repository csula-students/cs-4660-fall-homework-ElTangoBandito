package csula.cs4660.graphs.searches;

import csula.cs4660.graphs.Edge;
import csula.cs4660.graphs.Graph;
import csula.cs4660.graphs.Node;

import java.util.List;
import java.util.Stack;
import java.util.ArrayList;

/**
 * Depth first search
 */
public class DFS implements SearchStrategy {
    @Override
    public List<Edge> search(Graph graph, Node source, Node dist) {
        List<Edge> result = new ArrayList<Edge>();
        List<Node> visited = new ArrayList<Node>();
        Stack<Node> accum = new Stack();

        recursiveDFS(graph, source, dist, result, accum, visited);
        return result;
        /*
        accum.push(source);

        while(!accum.isEmpty()){
            Node currentNode = accum.pop();
            if (currentNode.equals(dist)){
                break;
            }
            if (!visited.contains(currentNode)){
                List<Node> childs = graph.neighbors(currentNode);
                for(Node node: childs){

                }
            }
        }

        return null;
        */
    }

    public void recursiveDFS(Graph graph, Node currentNode, Node dist, List<Edge> result, Stack<Node> accum, List<Node> visited){
        if(currentNode.equals(dist)){
            accum.add(currentNode);
            System.out.println(accum);
            for(int i = 1; i < accum.size(); i++){
                Edge e = new Edge(accum.get(i - 1), accum.get(i), graph.distance(accum.get(i-1), accum.get(i)));
                result.add(e);

            }
            System.out.println(result);
            //Why is it running twice when the results are correct???
            result.add(new Edge(currentNode, currentNode, 1));
            return;
        }
        if (!(result.size() > 0)) {
            if (!visited.contains(currentNode)) {
                visited.add(currentNode);
                accum.add(currentNode);

                List<Node> child = graph.neighbors(currentNode);
                for (Node node : child) {
                    if (visited.contains(child)) {
                        accum.pop();
                    } else {
                        recursiveDFS(graph, node, dist, result, accum, visited);
                    }
                }
                if(!(child.size() > 0) && !child.equals(dist)){
                    accum.pop();
                }
            } else {
                accum.pop();
            }
        }

    }
}
