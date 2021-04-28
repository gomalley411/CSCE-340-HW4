import java.util.*;

public class ShortestCommonAncestor {
    
    private Digraph graph; 
    private int V;
    private int root; 
    private ArrayList<Integer> subsetCommonAncestors=new ArrayList<Integer>();//holds the common ancestors for two given subsets
    private int subsetMinLength;//holds the length of the shortest ancestral path for two given subsets
    
    //constructor method takes a rooted DAG as an argument
    public ShortestCommonAncestor(Digraph G){
        
        //validate that argument is a DAG 
        DirectedCycle cycle=new DirectedCycle(G);
        if(cycle.hasCycle()) throw new IllegalArgumentException("Digraph is not a DAG");
        
        //Step 2: is it rooted? Do we need to validate that it's rooted? 
        graph=new Digraph(G);
        V=graph.V();
        root=0;
        boolean rooted=false; 
        
        while(root<V && !rooted){
            DirectedDFS search=new DirectedDFS(graph.reverse(), root);
            if(search.count()==V)
                rooted=true;
        }  
        if(!rooted) throw new IllegalArgumentException("Digraph is not rooted");
        
    }
    
    //length of shortest ancestral path between v and w
    public int length(int v, int w){
        
        if(v>=V || w>= V) throw new IllegalArgumentException("Vertices must be inside prescribed range");
        
        //create BFS objects for both vertices
        BreadthFirstDirectedPaths pathV=new BreadthFirstDirectedPaths(graph, v);
        BreadthFirstDirectedPaths pathW=new BreadthFirstDirectedPaths(graph, w);
        
        //return the sum of the shortest paths from each vertex to the shortest common ancestor
        return pathV.distTo(ancestor(v,w))+pathW.distTo(ancestor(v,w));
    }
    
    //a shortest common ancestor of vertices v and w
    public int ancestor(int v, int w){
        
        if(v>=V || w>= V) throw new IllegalArgumentException("Vertices must be inside prescribed range");
        
        ArrayList<Integer> commonAncestors=new ArrayList<Integer>();
        //find vertices reachable from v
        DirectedDFS searchV=new DirectedDFS(graph, v);
        //find vertices reachable from w 
        DirectedDFS searchW=new DirectedDFS(graph, w);
        //determine which vertices are reachable from both v and w 
        for(int i=0; i<V; i++){
            if(searchV.marked(i) && searchW.marked(i))
                commonAncestors.add(i);
        }
        //return common ancestor that is farthest from the root
        return commonAncestors.get(commonAncestors.size()-1);
    }
    
    //length of shortest ancestral path of vertex subsets A and B
    public int lengthSubset(Iterable<Integer> subsetA, Iterable<Integer> subsetB){
        
        if(subsetA==null || subsetB==null) throw new IllegalArgumentException("Subsets must contain vertices");
        
        //determine shortest common ancestor
        int ancestor=ancestorSubset(subsetA, subsetB);
        
        return subsetMinLength;
    }
    
    //a shortest common ancestor of vertex subsets A and B
    public int ancestorSubset(Iterable<Integer> subsetA, Iterable<Integer> subsetB){
        
        if(subsetA==null || subsetB==null) throw new IllegalArgumentException("Subsets must contain vertices");
        
        //find vertices reachable from subsetA
        DirectedDFS searchA=new DirectedDFS(graph, subsetA);
        //find vertices reachable from subsetB 
        DirectedDFS searchB=new DirectedDFS(graph, subsetB);
        //determine which vertices are reachable from both subsets
        for(int i=0; i<V; i++){
            if(searchA.marked(i) && searchB.marked(i))
                subsetCommonAncestors.add(i);
        }
        
        BreadthFirstDirectedPaths pathA=new BreadthFirstDirectedPaths(graph, subsetA);
        BreadthFirstDirectedPaths pathB=new BreadthFirstDirectedPaths(graph, subsetB);
        
        //determine shortest path 
        int path=0;
        subsetMinLength=graph.E();//holds the length of the shortest ancestral path
        int ancestor=0;//holds the shortest common ancestor
        
        for(Integer integer : subsetCommonAncestors){
            
            //calculate shortest path to ancestor
            int i=integer.intValue();
            int aPath=0, bPath=0; 
            for(Integer vertex: pathA.pathTo(i))
                aPath++;
            for(Integer vertex: pathB.pathTo(i))
                bPath++;
            path=aPath+bPath-2; 
            //if path is shorter than previous min, update shortest ancestral path and shortest common ancestor
            if(path<subsetMinLength){
                subsetMinLength=path;
                ancestor=i;   
            }
        }
        return ancestor; 
    }   
}
