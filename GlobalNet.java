import java.util.Arrays;

public class GlobalNet
{
    //creates a global network
    //O : the original graph
    //regions: the regional graphs
    private static int[] dist;
    private static String[] prev;
    public static Graph run(Graph O, Graph[] regions)
    {
        //TODO
        Graph result = new Graph(O.V());
        result.setCodes(O.getCodes());
        for (Graph region : regions) {
            for (int j = 0; j < region.edges().size(); j++) {
                result.addEdge(region.edges().get(j));
            }
        }
        //Run Dijkstra on each region
        for (int i = 0; i < regions.length; i++) {
            dijkstra(O, regions[i].getCode(0), regions[i]);
            System.out.println(Arrays.toString(dist));
            for (int j = i + 1; j < regions.length; j++) {
                int currDist = Integer.MAX_VALUE;
                int currIndex = 0;
                for (int k = 0; k < regions[j].getCodes().length; k++) {
                    String code = regions[j].getCode(k);
                    if (dist[O.index(code)] < currDist) {
                        currDist = dist[O.index(code)];
                        currIndex = O.index(code);
                    }
                }
                int vertex = currIndex;
                while (prev[vertex] != null) {
                    result.addEdge(O.getEdge(vertex, O.index(prev[vertex])));
                    vertex = O.index(prev[vertex]);
                }
            }
        }
        return result;
    }

    static void dijkstra(Graph given, String vertex, Graph region) {
        dist = new int[given.V()];
        prev = new String[given.V()];
        DistQueue Q = new DistQueue(given.V());

        //Initializing dist and prev to INF and -1 respectively
        dist[given.index(vertex)] = 0;
        for (int i = 0; i < given.getCodes().length; i++) {
            String u = given.getCode(i);
            if (u != vertex) {
                dist[given.index(u)] = Integer.MAX_VALUE;
            }
            prev[given.index(u)] = null;
            Q.insert(given.index(u), dist[given.index(u)]);
        }
        for (int i = 0; i < region.getCodes().length; i++) {
            String u = region.getCode(i);
            dist[given.index(u)] = 0;
            Q.set(given.index(u), dist[given.index(u)] );
        }

        while (!Q.isEmpty()) {
            int u = Q.delMin();
            for (int i = 0; i < given.adj(u).size(); i++) {
                int w = given.adj(u).get(i);
                int d = dist[u] + given.getEdgeWeight(u, w);
                if (d < dist[w]) {
                    dist[w] = d;
                    prev[w] = given.getCode(u);
                    Q.set(w, d);
                }
            }
        }
    }
}
    
    
    
