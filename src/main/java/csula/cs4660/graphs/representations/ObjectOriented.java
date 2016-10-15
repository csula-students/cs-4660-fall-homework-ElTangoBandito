package csula.cs4660.graphs.representations;

import csula.cs4660.graphs.Edge;
import csula.cs4660.graphs.Node;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Object oriented representation of graph is using OOP approach to store nodes
 * and edges
 *
 * TODO: Please fill the body of methods in this class
 */
public class ObjectOriented implements Representation {
    private Collection<Node> nodes;
    private Collection<Edge> edges;
    private HashMap<Node, VertexRelation> rmap;

    public ObjectOriented(File file) {
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader br = new BufferedReader(fileReader);

            String numberOfNodes = br.readLine();
            int nod = Integer.valueOf(numberOfNodes);
            rmap = new HashMap<Node, VertexRelation>();
            for (int i = 0; i < nod; i++){
                Node tempNode = new Node(i);
                VertexRelation v = new VertexRelation(tempNode);
                rmap.put(tempNode, v);
            }

            String line = br.readLine();
            while(line != null){
                List<Integer> tempList = new ArrayList();
                for(String number: line.split(":")){
                    int digit = Integer.parseInt(number);
                    tempList.add(digit);
                }
                Node fromNode = new Node(tempList.get(0));
                Node toNode = new Node(tempList.get(1));
                Edge curEdge = new Edge(fromNode, toNode, tempList.get(2));
                rmap.get(fromNode).addEdge(curEdge);
                line = br.readLine();
            }
            

        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public ObjectOriented() {

    }

    @Override
    public boolean adjacent(Node x, Node y) {
        List<Node> adjacents = new ArrayList<Node>();
        adjacents = rmap.get(x).getNeighbors();
        return adjacents.contains(y);
    }

    @Override
    public List<Node> neighbors(Node x) {
        return rmap.get(x).getNeighbors();
    }

    @Override
    public boolean addNode(Node x) {
        if (rmap.containsKey(x)){
            return false;
        }
        VertexRelation v = new VertexRelation(x);
        rmap.put(x, v);
        return true;
    }

    @Override
    public boolean removeNode(Node x) {
        if(!rmap.containsKey(x)){
            return false;
        }
        rmap.remove(x);
        Map<Node, List<Edge>> mapToEdit = new HashMap<Node, List<Edge>>();
        for (Node key: rmap.keySet()){
            List<Edge> edges = new ArrayList<Edge>();
            for (Edge e: rmap.get(key).getEdgeList()){
                if (e.getTo().equals(x)){
                    edges.add(e);
                }
            }
            mapToEdit.put(key, edges);
        }
        for (Node key: mapToEdit.keySet()){
            if (mapToEdit.get(key).size() > 0){
                for (Edge e: mapToEdit.get(key)) {
                    rmap.get(key).removeEdge(e);
                }
            }
        }
        //i hate this method
        return true;
    }

    @Override
    public boolean addEdge(Edge x) {
        return rmap.get(x.getFrom()).addEdge(x);
    }

    @Override
    public boolean removeEdge(Edge x) {
        return rmap.get(x.getFrom()).removeEdge(x);
    }

    @Override
    public int distance(Node from, Node to) {
        int result = 0;
        VertexRelation vt = rmap.get(from);
        for(Edge e :vt.getEdgeList()){
            if (e.getTo().equals(to)){
                result = e.getValue();
            }
        }
        return result;
    }

    @Override
    public Optional<Node> getNode(int index) {
        return null;
    }

}
