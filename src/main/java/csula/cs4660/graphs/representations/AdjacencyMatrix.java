package csula.cs4660.graphs.representations;

import csula.cs4660.graphs.Edge;
import csula.cs4660.graphs.Node;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

/**
 * Adjacency matrix in a sense store the nodes in two dimensional array
 */
public class AdjacencyMatrix implements Representation {
    private Node[] nodes;//going to use an arraylist instead
    private int[][] adjacencyMatrix;
    private List<Node> nodeList = new ArrayList<Node>();
    public AdjacencyMatrix(File file) {
        try{
            FileReader fileReader = new FileReader(file);
            BufferedReader br = new BufferedReader(fileReader);

            String numberOfNodes = br.readLine();
            int nod = Integer.valueOf(numberOfNodes);

            adjacencyMatrix = new int[nod][nod];

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
                int from = tempList.get(0);
                int to = tempList.get(1);
                //We don't care for weight for now
                adjacencyMatrix[from][to] = tempList.get(2);
                line = br.readLine();

            }


        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public AdjacencyMatrix() {

    }

    @Override
    public boolean adjacent(Node x, Node y) {
        int row = (int) x.getData();
        int col = (int) y.getData();
        if (adjacencyMatrix[row][col] != 0){
            return true;
        };
        return false;
    }

    @Override
    public List<Node> neighbors(Node x) {
        List<Node> result = new ArrayList<Node>();
        int max = nodeList.size();
        int row = (int) x.getData();
        if (nodeList.contains(x)){
            for (int i = 0; i < max; i++){
                if(adjacencyMatrix[row][i] != 0){
                    result.add(nodeList.get(i));
                }
            }
        }
        return result;
    }

    @Override
    public boolean addNode(Node x) {
        if (nodeList.contains(x)){
            return false;
        }
        nodeList.add(x);
        int[][] oldMatrix = adjacencyMatrix;
        adjacencyMatrix = new int[nodeList.size()][nodeList.size()];
        for (int i = 0; i < nodeList.size(); i++){
            for (int j = 0; j < nodeList.size(); j++){
                if (i == nodeList.size() - 1 || j == nodeList.size() - 1){
                    adjacencyMatrix[i][j] = 0;
                }
                else{
                    adjacencyMatrix[i][j] = oldMatrix[i][j];
                }
            }
        }
        return true;
    }

    @Override
    public boolean removeNode(Node x) {
        if (!nodeList.contains(x)){
            return false;
        }
        nodeList.remove(x);
        int vertex = (int) x.getData();
        int[][] oldMatrix = adjacencyMatrix;
        adjacencyMatrix = new int[nodeList.size()][nodeList.size()];
        for (int i = 0; i < nodeList.size(); i++){
            for (int j = 0; j < nodeList.size(); j++){
                if (i == vertex || j == vertex){
                    continue;
                }
                else{
                    adjacencyMatrix[i][j] = oldMatrix[i][j];
                }
            }
        }
        return true;
    }

    @Override
    public boolean addEdge(Edge x) {
        if (nodeList.contains(x.getFrom()) && nodeList.contains(x.getTo())){
            int row = (int) x.getFrom().getData();
            int col = (int) x.getTo().getData();
            if (adjacencyMatrix[row][col] != 0) {
                return false;
            }
            else {
                adjacencyMatrix[row][col] = x.getValue();
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean removeEdge(Edge x) {
        if (nodeList.contains(x.getFrom()) && nodeList.contains(x.getTo())){
            int row = (int) x.getFrom().getData();
            int col = (int) x.getTo().getData();
            if (adjacencyMatrix[row][col] == 0) {
                return false;
            }
            else {
                adjacencyMatrix[row][col] = 0;
                return true;
            }
        }
        return false;
    }

    @Override
    public int distance(Node from, Node to) {
        return adjacencyMatrix[(int) from.getData()][(int) to.getData()];
    }

    @Override
    public Optional<Node> getNode(int index) {
        return null;
    }

    @Override
    public Optional<Node> getNode(Node node) {
        Iterator<Node> iterator = Arrays.asList(nodes).iterator();
        Optional<Node> result = Optional.empty();
        while (iterator.hasNext()) {
            Node next = iterator.next();
            if (next.equals(node)) {
                result = Optional.of(next);
            }
        }
        return result;
    }
}
