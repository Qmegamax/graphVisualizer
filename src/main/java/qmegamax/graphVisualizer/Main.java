package qmegamax.graphVisualizer;

public class Main {
        public static void main(String[] args) {
               Graph graph = new Graph(10);

               addNodes(graph,7);
               CanvasFrame frame = new CanvasFrame(graph);
        }



        public static void addNodes(Graph graph, int amount) {
                int size = graph.nodeCount;

                int maxTries;

                for (int i = 0; i < amount; i++) {
                        int a,b;

                        maxTries=size+30;
                        do {
                                a = (int) (Math.random() * size);
                                b = (int) (Math.random() * size);
                                maxTries--;
                        }while((graph.links[a].contains(b) || a==b) && maxTries>0);

                        graph.linkCount = amount;
                        graph.addNode(a,b);
                }
        }
}