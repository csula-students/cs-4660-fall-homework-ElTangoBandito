package csula.cs4660.quizes;

import csula.cs4660.graphs.Edge;
import csula.cs4660.graphs.Graph;
import csula.cs4660.graphs.Node;
import csula.cs4660.graphs.representations.Representation;
import csula.cs4660.graphs.searches.BFS;
import csula.cs4660.quizes.models.State;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


/**
 * Here is your quiz entry point and your app
 */
public class App {

    private static Graph graph;
    public static void main(String[] args) {

        graph = new Graph(
                Representation.of(
                        Representation.STRATEGY.ADJACENCY_LIST
                )
        );

        List<Node> visited = new ArrayList<Node>();
        // to get a state, you can simply call `Client.getState with the id`
        State initialState = Client.getState("10a5461773e8fd60940a56d2e9ef7bf4").get();
        //0dad77ed7f0c3b040666d41c42a43374
        State distState = Client.getState("0dad77ed7f0c3b040666d41c42a43374").get();
        //State distState = Client.getState("e577aa79473673f6158cc73e0e5dc122").get();
        Node currentNode = new Node(initialState);
        Stack<Node> accum = new Stack();
        Node fromNode = new Node(initialState);
        Node toNode = new Node(distState);
        buildGraph(graph, currentNode, visited, accum);
        List<Edge> result = graph.search(new BFS(), fromNode, toNode);
        State startState = (State) fromNode.getData();
        System.out.println("BFS Results: ");
        System.out.println("============================================");
        System.out.println(startState.getLocation().getName());
        for (Edge e: result){
            Node tempNode = e.getTo();
            State tempState = (State) tempNode.getData();
            System.out.println(tempState.getLocation().getName());
        }


        // to get an edge between state to its neighbor, you can call stateTransition
        //System.out.println(Client.stateTransition(initialState.getId(), initialState.getNeighbors()[0].getId()));
    }

    public static void buildGraph(Graph graph, Node currentNode, List<Node> visited, Stack<Node> accum){
        accum.push(currentNode);
        int counter = 0;
        graph.addNode(currentNode);
        //while(counter<30) {
        while(!accum.isEmpty()) {
            currentNode = accum.pop();
            if (!visited.contains(currentNode)) {
                State nodeState = (State) currentNode.getData();
                State currentState = Client.getState(nodeState.getId()).get();
                if (counter % 10 == 0){
                    System.out.println(counter);
                }
                counter++;
                State[] neighbors = currentState.getNeighbors();

                visited.add(currentNode);
                for (State s : neighbors) {
                    Node n = new Node(s);

                    if (!visited.contains(n)) {
                        graph.addNode(n);
                    }

                    int cost = Client.stateTransition(currentState.getId(), s.getId()).get().getEvent().getEffect();
                    Edge e = new Edge(currentNode, n, cost);
                    graph.addEdge(e);
                    accum.push(n);
                }
            }
        }

        //System.out.println(initialState);
    }
}
