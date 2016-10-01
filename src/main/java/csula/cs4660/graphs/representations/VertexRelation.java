package csula.cs4660.graphs.representations;

import csula.cs4660.graphs.Edge;
import csula.cs4660.graphs.Node;

import java.util.ArrayList;
import java.util.List;

public class VertexRelation {
    private Node node;
    private List<Edge> edgeList;
    private List<Node> neighbors;

    public VertexRelation(Node n){
        node = n;
        edgeList = new ArrayList<Edge>();
        neighbors = new ArrayList<Node>();
    }

    public boolean addEdge(Edge e){
        boolean result = false;
        if (!edgeList.contains(e)){
            edgeList.add(e);
            result = true;
        }
        updateNeighbor();
        return result;
    }

    public boolean removeEdge(Edge e){
        boolean result = false;
        if (edgeList.contains(e)){
            edgeList.remove(e);
            result = true;
        }
        updateNeighbor();
        return result;
    }

    public void updateNeighbor(){
        List<Node> trueNeighbors = new ArrayList<Node>();
        for (Edge e: edgeList){
            if(!neighbors.contains(e.getTo())){
                neighbors.add(e.getTo());
            }
            trueNeighbors.add(e.getTo());
        }
        List<Node> toRemove = new ArrayList<Node>();
        for (Node n: neighbors){
            if (!trueNeighbors.contains(n)){
                toRemove.add(n);
            }
        }
        neighbors.removeAll(toRemove);
    }

    public List<Edge> getEdgeList(){
        return edgeList;
    }

    public List<Node> getNeighbors(){
        return neighbors;
    }
}
