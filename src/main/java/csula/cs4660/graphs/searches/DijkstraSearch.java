package csula.cs4660.graphs.searches;

import com.google.common.collect.Interner;
import com.google.common.collect.Lists;
import csula.cs4660.graphs.Edge;
import csula.cs4660.graphs.Graph;
import csula.cs4660.graphs.Node;

import java.util.*;

/**
 * As name, dijkstra search using graph structure
 */
public class DijkstraSearch implements SearchStrategy {


    @Override
    public List<Edge> search(Graph graph, Node source, Node dist) {

        Map<Node, DNode> nodeMap = new HashMap<Node, DNode>();
        PriorityQueue<DNode> nodeQueue = new PriorityQueue<DNode>();
        List<Node> visited = new ArrayList<Node>();
        List<Edge> result = new ArrayList<Edge>();

        nodeQueue.offer(new DNode(source, 0));
        while (!nodeQueue.isEmpty()){
            DNode currentDNode = nodeQueue.poll();
            Node currentNode = currentDNode.getNode();
            if (currentNode.equals(dist)) {
                visited.add(currentNode);
                break;
            }
            if (!visited.contains(currentNode)){
                visited.add(currentNode);
                List<Node> childs = graph.neighbors(currentNode);
                for (Node node: childs){
                    int currentDistance = currentDNode.getDistance() + graph.distance(currentNode, node);
                    if(!nodeQueue.contains(node)){
                        nodeQueue.offer(new DNode(node, currentDistance));


                        if(nodeMap.containsKey(node)){
                            int existingDistance = nodeMap.get(node).getDistance();
                            if (currentDistance < existingDistance){
                                nodeMap.put(node, new DNode(currentNode, currentDistance));
                            }
                        }
                        else{
                            nodeMap.put(node, new DNode(currentNode, currentDistance));
                        }

                    }
                }
            }
        }

        //trace back the child-parent map
        Node tracerNode = dist;
        while(!tracerNode.equals(source)){
            Node parentNode = nodeMap.get(tracerNode).getNode();
            Edge e = new Edge(parentNode, tracerNode, graph.distance(parentNode, tracerNode));
            result.add(e);
            tracerNode = parentNode;
        }

        return Lists.reverse(result);
    }

}
