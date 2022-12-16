import java.util.ArrayList;

public class RegNet {
    //creates a regional network
    //G: the original graph
    //max: the budget
    public static boolean visited[];

    public static Graph run(Graph G, int max) {
        //TODO
        int totalWeight = 0;
        ArrayList<Edge> connected = new ArrayList<>();
        Graph mst = new Graph(G.V());
        Graph withStops = new Graph(G.V());
        mst.setCodes(G.getCodes());
        withStops.setCodes(G.getCodes());
        //Create a MST using Kruskal
        //-Sort edges by ascending order
        ArrayList<Edge> sorted;
        sorted = G.sortedEdges();
        //-Go through all the sorted edges and look at two nodes the edge belongs to, union if they are not already and they do not create a cycle (Create MST)
        UnionFind UF = new UnionFind(G.V());
        for (int i = 0; i < G.E() && connected.size() < G.V() - 1; i++) {
            Edge e = sorted.get(i);
            int v = e.vi();
            int u = e.ui();
            //Checks for cycle
            if (UF.find(v) != UF.find(u)) {
                UF.union(v, u);
                connected.add(new Edge(e.u, e.v, e.w));
                mst.addEdge(u, v, e.w);
                totalWeight += e.w;
            }
        }
        //-Remove edges, starting by largest edges and make sure the graph is connected and it only creates one stray edge
        int index = mst.sortedEdges().size() - 1;
        while (mst.totalWeight() > max) {
            if (mst.deg(mst.sortedEdges().get(index).v) == 1 || mst.deg(mst.sortedEdges().get(index).u) == 1) {
                mst.removeEdge(mst.sortedEdges().get(index));
                index = mst.E();
            }
            index--;
        }
        mst = mst.connGraph();
        //-Check if there is room for more edges that fits in the budget
        //-BFS Portion
        for (int i = 0; i < mst.V(); i++) {
            BFS(mst, mst.getCode(i), withStops, G);
        }
        withStops.sortedEdges();
        for (int i = 0; i < withStops.E(); i++) {
            if (withStops.edges().get(i).w <= max - mst.totalWeight()) {
                mst.addEdge(new Edge(withStops.edges().get(i).u, withStops.edges().get(i).v, withStops.edges().get(i).w));
            }
        }
        return mst;
    }

    static void BFS (Graph mst, String vertex, Graph withStops, Graph given) {
        String v;
        visited = new boolean[mst.V()];
        BetterQueue<String> Q = new BetterQueue<>();
        Q.add(vertex);
        visited[mst.index(vertex)] = true;
        int levels = 0;
        while (!Q.isEmpty()) {
            int levelLength = Q.size();
            for (int j = 0; j < levelLength; j++) {
                v = Q.remove();
                for (int i = 0; i < mst.adj(v).size(); i++) {
                    if (!visited[mst.adj(v).get(i)]) {
                        Q.add(mst.getCode(mst.adj(v).get(i)));
                        visited[mst.adj(v).get(i)] = true;
                        withStops.addEdge(new Edge(vertex, mst.getCode(mst.adj(v).get(i)),
                                given.getEdgeWeight(given.index(vertex), given.index(mst.getCode(mst.adj(v).get(i)))),
                                levels));
                    }
                }
            }
            levels++;
        }
    }
}
