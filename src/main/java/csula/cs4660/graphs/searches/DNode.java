package csula.cs4660.graphs.searches;

import csula.cs4660.graphs.Node;

/**
 * Created by Kuo-Cheng on 10/11/2016.
 */
public class DNode implements Comparable<DNode>{

    private Node node;
    private int distance;

    public DNode(Node nodeIn, int distanceIn){
        node = nodeIn;
        distance = distanceIn;
    }

    public Node getNode(){
        return node;
    }

    public int getDistance(){
        return distance;
    }

    @Override
    public int compareTo(DNode node){
        return -(distance - node.distance);
        // - to flip the result for the quiz
    }
}
