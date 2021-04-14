/**
 *  The DirectedCycle class represents a data type for 
 *  determining whether a digraph has a directed cycle.
 *  The hasCycle operation determines whether the digraph has
 *  a simple directed cycle and, if so, the cycle operation
 *  returns one.
 *  <p>
 *  This implementation uses depth-first search.
 *  The constructor takes Î¸(V + E) time in the worst
 *  case, where V is the number of vertices and E is
 *  the number of edges.
 *  Each instance method takes Î¸(1) time.
 *  It uses Î¸(V) extra space (not including the digraph).
 */
import java.util.Stack;

public class DirectedCycle {
    private boolean[] marked;        // marked[v] = has vertex v been marked?
    private int[] edgeTo;            // edgeTo[v] = previous vertex on path to v
    private boolean[] onStack;       // onStack[v] = is vertex on the stack?
    private Stack<Integer> cycle;    // directed cycle (or null if no such cycle)

    /**
     * Determines whether the digraph G has a directed cycle and, if so,
     * finds such a cycle.
     * @param G the digraph
     */
    public DirectedCycle(Digraph G) {
        marked  = new boolean[G.V()];
        onStack = new boolean[G.V()];
        edgeTo  = new int[G.V()];
        
        for (int v = 0; v < G.V(); v++)
            if (!marked[v] && cycle == null) 
                dfs(G, v);
    }

    // run DFS and find a directed cycle (if one exists)
    private void dfs(Digraph G, int v) {
        onStack[v] = true;
        marked[v] = true;
        
        for (int w : G.adj(v)) {
            // short circuit if directed cycle found
            if (cycle != null) 
                return;

            // found new vertex, so recur
            else if (!marked[w]) {
                edgeTo[w] = v;
                dfs(G, w);
            }

            // trace back directed cycle
            else if (onStack[w]) {
                cycle = new Stack<Integer>();
                for (int x = v; x != w; x = edgeTo[x]) 
                    cycle.push(x);
                
                cycle.push(w);
                cycle.push(v);
                assert check();
            }
        }
        onStack[v] = false;
    }

    /**
     * Does the digraph have a directed cycle?
     * @return true if the digraph has a directed cycle, false otherwise
     */
    public boolean hasCycle() {
        return cycle != null;
    }

    /**
     * Returns a directed cycle if the digraph has a directed cycle, and null otherwise.
     * @return a directed cycle (as an iterable) if the digraph has a directed cycle,
     *    and null otherwise
     */
    public Iterable<Integer> cycle() {
        return cycle;
    }


    // certify that digraph has a directed cycle if it reports one
    private boolean check() {

        if (hasCycle()) {
            // verify cycle
            int first = -1, last = -1;
            
            for (int v : cycle()) {
                if (first == -1) 
                    first = v;
                last = v;
            }
            
            if (first != last) {
                System.err.printf("cycle begins with %d and ends with %d\n", first, last);
                return false;
            }
        }

        return true;
    }
}