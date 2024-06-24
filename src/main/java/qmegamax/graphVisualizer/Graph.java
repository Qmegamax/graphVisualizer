package qmegamax.graphVisualizer;

import java.util.ArrayList;

public class Graph {
    public int nodeCount;
    public int linkCount;
    public ArrayList<Integer>[] links;

    public Graph(int nodeCount) {
        this.nodeCount=nodeCount;
        links=new ArrayList[nodeCount];
        for(int i=0;i<nodeCount;i++) {
            links[i]=new ArrayList<>();
        }
    }

    public void addNode(int a,int b) {
        links[a].add(b);
        links[b].add(a);
    }
}

