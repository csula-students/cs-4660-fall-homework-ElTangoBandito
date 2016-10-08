package csula.cs4660.graphs.representations;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import csula.cs4660.graphs.Edge;
import csula.cs4660.graphs.Node;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Adjacency list is probably the most common implementation to store the unknown
 * loose graph
 *
 * TODO: please implement the method body
 */
public class AdjacencyList implements Representation {
    private Map<Node, Collection<Edge>> adjacencyList;
    private Multimap<Node, Edge> mmap;
    private List<Node> nodeList;

    public AdjacencyList(File file) {
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader br = new BufferedReader(fileReader);

            String numberOfNodes = br.readLine();
            int nod = Integer.valueOf(numberOfNodes);

            mmap = ArrayListMultimap.create();
            nodeList = new ArrayList<Node>();
            for(int i = 0; i < nod; i++) {
                Node tempNode = new Node(i);
                nodeList.add(tempNode);
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
                mmap.put(fromNode, curEdge);
                line = br.readLine();
            }


        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    public AdjacencyList() {
    }

    @Override
    public boolean adjacent(Node x, Node y) {
        boolean result = false;
        List<Edge> edgeList = new ArrayList<Edge>();
        edgeList.addAll(mmap.get(x));
        for(Edge e: edgeList){
            if (y.equals(e.getTo())){
                result = true;
            };
        }
        return result;
    }

    @Override
    public List<Node> neighbors(Node x) {
        List<Node> result = new ArrayList<Node>();
        for (Edge e: mmap.get(x)){
            result.add(e.getTo());
        }
        return result;
    }

    @Override
    public boolean addNode(Node x) {
        if (nodeList.contains(x)){
            return false;
        }
        else{
            nodeList.add(x);
            return true;
        }
    }

    @Override
    public boolean removeNode(Node x) {
        if (nodeList.contains(x)){
            nodeList.remove(x);
            mmap.removeAll(x);
            Map<Node, Edge> toBeRemoved = new HashMap<Node, Edge>();
            for (Map.Entry entry: mmap.entries()){
                Edge e = (Edge) entry.getValue();
                if (e.getTo().equals(x)){
                    Node n = (Node) entry.getKey();
                    toBeRemoved.put(n, e);
                }
            }
            for (Node key: toBeRemoved.keySet()){
                mmap.remove(key, toBeRemoved.get(key));
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean addEdge(Edge x) {
        Node fromNode = x.getFrom();
        Node toNode = x.getTo();
        if(nodeList.contains(fromNode) && nodeList.contains(toNode)){

            if(!mmap.get(fromNode).contains(x)){
                mmap.put(fromNode, x);
                return true;
            };

        }
        return false;
    }

    @Override
    public boolean removeEdge(Edge x) {
        Node fromNode = x.getFrom();
        Node toNode = x.getTo();
        if(nodeList.contains(fromNode) && nodeList.contains(toNode)){

            if(mmap.get(fromNode).contains(x)){
                mmap.remove(fromNode, x);
                return true;
            };

        }
        return false;
    }

    @Override
    public int distance(Node from, Node to) {
        int result = 0;
        for (Edge e: mmap.get(from)){
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
/*
    @Override
    public void recursiveBFS(Node currentNode, Node destination, Queue<Node> nodeQueue, List<Node> visited, List<Edge> result, Queue<Edge> accum){
        if (nodeQueue.isEmpty()){
            return;
        }
        Node current = nodeQueue.poll();
        if(!visited.contains(current)) {
            visited.add(current);
            for (Edge e : mmap.get(current)) {
                Node nextNode = e.getTo();
                if (!visited.contains(nextNode)) {
                    System.out.println(e);
                    nodeQueue.add(nextNode);
                }
            }
        }
        //System.out.println(current);
        recursiveBFS(currentNode, destination, nodeQueue, visited, result, accum);
    }
    */
}
