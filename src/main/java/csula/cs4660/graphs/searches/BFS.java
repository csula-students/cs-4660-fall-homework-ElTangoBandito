package csula.cs4660.graphs.searches;

import com.google.common.collect.Lists;
import csula.cs4660.graphs.Edge;
import csula.cs4660.graphs.Graph;
import csula.cs4660.graphs.Node;

import java.util.*;

/**
 * Breadth first search
 */
public class BFS implements SearchStrategy {
    @Override
    public List<Edge> search(Graph graph, Node source, Node dist) {
        System.out.println("running??");
        Map<Node, Node> nodeMap = new HashMap<Node, Node>();
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
                //System.out.println(currentNode);
                List<Node> childs = graph.neighbors(currentNode);
                for (Node node: childs){
                    nodeMap.put(node, currentNode);
                    if(node.equals(dist)){
                        nodeQueue.clear();
                        break;
                    }
                    nodeQueue.add(node);
                }
            }
        }

        //System.out.println(nodeMap);

        Node tracerNode = dist;
        while(!tracerNode.equals(source)){
            Node parentNode = nodeMap.get(tracerNode);
            System.out.println(graph.distance(parentNode, tracerNode));
            Edge e = new Edge(parentNode, tracerNode, graph.distance(parentNode, tracerNode));
            result.add(e);
            tracerNode = parentNode;
        }
        System.out.println("Whyyyy");
        //result = Lists.reverse(result);
        //return result;
        System.out.println(result);
        return Lists.reverse(result);
        /*
        //reverse result
        List<Edge> realResult = new ArrayList<Edge>();
        for(int i = result.size() - 1; i > -1; i--){
            realResult.add(result.get(i));
        }

        //graph.recursiveBFS(source, dist, nodeQueue, visited, result, accum);
        return realResult;
        */

    }

}
