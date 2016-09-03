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

    public AdjacencyList(File file) {
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader br = new BufferedReader(fileReader);

            String numberOfNodes = br.readLine();
            int nod = Integer.valueOf(numberOfNodes);

            Multimap<Node, Edge> m = ArrayListMultimap.create();
            for(int i = 0; i < nod; i++){
                Node temp = new Node(i);
                m.put(temp, null);
            }
            /*
            while(line != null){
                List<Integer> tempList = new ArrayList();
                for(String number: line.split(" ")){
                    int digit = Integer.parseInt(number);
                    tempList.add(digit);
                }
                numberList.add((ArrayList) tempList);
                line = br.readLine();
            }
            */
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    public AdjacencyList() {

    }

    @Override
    public boolean adjacent(Node x, Node y) {
        return false;
    }

    @Override
    public List<Node> neighbors(Node x) {
        return null;
    }

    @Override
    public boolean addNode(Node x) {
        return false;
    }

    @Override
    public boolean removeNode(Node x) {
        return false;
    }

    @Override
    public boolean addEdge(Edge x) {
        return false;
    }

    @Override
    public boolean removeEdge(Edge x) {
        return false;
    }

    @Override
    public int distance(Node from, Node to) {
        return 0;
    }

    @Override
    public Optional<Node> getNode(int index) {
        return null;
    }
}
