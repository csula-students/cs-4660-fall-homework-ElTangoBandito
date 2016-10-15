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
import java.util.HashMap;
import java.util.Map;


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

        List<String> visited = new ArrayList<String>();
        // to get a state, you can simply call `Client.getState with the id`
        State initialState = Client.getState("10a5461773e8fd60940a56d2e9ef7bf4").get();
        //0dad77ed7f0c3b040666d41c42a43374
        State distState = Client.getState("e577aa79473673f6158cc73e0e5dc122").get();
        //State distState = Client.getState("e577aa79473673f6158cc73e0e5dc122").get();
        Node currentNode = new Node(initialState);
        Stack<State> accum = new Stack();
        Node fromNode = new Node(initialState);
        Node toNode = new Node(distState);

        buildGraph(graph, initialState, visited, accum);

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

    public static void buildGraph(Graph graph, State currentState, List<String> visited, Stack<State> accum){
        accum.push(currentState);
        int counter = 0;
        graph.addNode(new Node(currentState));
        int edges = 0;
        int possibleEdges = 0;
        State startingState = currentState;
        Map<String, State> stateMap = new HashMap<String, State>();
        stateMap.put(currentState.getId(), currentState);
        int numberOfNodes = 0;

        //while(counter<50) {
        while(!accum.isEmpty()) {
            currentState = accum.pop();
            if (!visited.contains(currentState.getId())) {
                //State nodeState = (State) currentNode.getData();
                //State currentState = Client.getState(nodeState.getId()).get();

                if (counter % 10 == 0){
                    System.out.println(counter);
                }
                counter++;

                //graph.addNode(new Node(currentState));

                State[] neighbors = currentState.getNeighbors();
                possibleEdges += neighbors.length;
                visited.add(currentState.getId());
                for (State s : neighbors) {
                    String stateId = s.getId();
                    if(!visited.contains(stateId)){
                        State nextState = Client.getState(s.getId()).get();
                        stateMap.put(s.getId(), nextState);
                        accum.push(nextState);

                        if(graph.addNode(new Node(nextState))){
                            numberOfNodes++;
                        };

                        Node parentNode = new Node(currentState);
                        int cost = Client.stateTransition(currentState.getId(), nextState.getId()).get().getEvent().getEffect();
                        Edge e = new Edge(parentNode, new Node(nextState), cost);
                        if(graph.addEdge(e)){
                            edges++;
                        }
                    }
                    else if(stateMap.containsKey(stateId)){
                        State childState = stateMap.get(stateId);
                        Node parentNode = new Node(currentState);
                        int cost = Client.stateTransition(currentState.getId(), childState.getId()).get().getEvent().getEffect();
                        Edge e = new Edge(parentNode, new Node(childState), cost);
                        if(graph.addEdge(e)){
                            edges++;
                        }

                    }
                }
            }
        }
        System.out.println("Number of nodes in graph:");
        System.out.println(numberOfNodes);
        System.out.println("Number of actual edges in graph:");
        System.out.println(edges);
        System.out.println("Number of edges in graph:");
        System.out.println(possibleEdges);
    }
}
