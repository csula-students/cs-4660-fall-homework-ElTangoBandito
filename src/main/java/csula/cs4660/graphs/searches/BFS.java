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
        Map<Node, Node> nodeMap = new HashMap<Node, Node>();
        Queue<Node> nodeQueue = new LinkedList<Node>();
        List<Node> visited = new ArrayList<Node>();
        List<Edge> result = new ArrayList<Edge>();

        //Queue<Edge> accum = new LinkedList<Edge>();
        nodeQueue.add(source);
        int iter = 0;
        while (!nodeQueue.isEmpty()){
            iter++;
            //System.out.println("Itteration: ");
            //System.out.println(iter);
            Node currentNode = nodeQueue.poll();
            if (currentNode.equals(dist)) {
                visited.add(currentNode);
                break;
            }
            if (!visited.contains(currentNode)){
                visited.add(currentNode);

                List<Node> childs = graph.neighbors(currentNode);
                //System.out.println("Childs");
                //System.out.println(childs.size());

                for (Node node: childs){
                    if(!nodeQueue.contains(node)){
                        if(!visited.contains(node)){
                            nodeMap.put(node, currentNode);
                            nodeQueue.add(node);
                        }
                    }
                    //nodeMap.put(node, currentNode);
                    if(node.equals(dist)){
                        nodeQueue.clear();
                        break;
                    }
                    //nodeQueue.add(node);
                    //nodeQueue.add(node);
                }
            }
        }
        //trace back the child-parent map
        System.out.println("Total Iterations:");
        System.out.println(iter);
        System.out.println("Tracer Map Size: ");
        System.out.println(nodeMap.size());
        Node tracerNode = dist;
        while(!tracerNode.equals(source)){
            Node parentNode = nodeMap.get(tracerNode);
            Edge e = new Edge(parentNode, tracerNode, graph.distance(parentNode, tracerNode));
            result.add(e);
            tracerNode = parentNode;
        }
        return Lists.reverse(result);


    }

    private List<Edge> recursiveBFS(Graph graph, Node source, Node dist, List<Edge> result){
        return result;
    }

}
