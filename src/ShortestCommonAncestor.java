import java.util.*;

public class ShortestCommonAncestor {
    
    Digraph graph; 
    int V;
    int root; 
    
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
        /*
        System.out.println("DAG is rooted: "+rooted);
        System.out.println("Root vertex: "+root);
        DirectedDFS search=new DirectedDFS(graph.reverse(), root);
        for(int i=0; i<search.count(); i++){
            System.out.println(i+"  "+search.marked(i));
        }*/

    }
    
    //length of shortest ancestral path between v and w
    public int length(int v, int w){
        
        return 0;
    }
    
    //a shortest common ancestor of vertices v and w
    public int ancestor(int v, int w){
        
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
        return commonAncestors.get(commonAncestors.size());
    }
    
    //length of shortest ancestral path of vertex subsets A and B
    public int lengthSubset(Iterable<Integer> subsetA, Iterable<Integer> subsetB){
        
        return 0;
    }
    
    //a shortest common ancestor of vertex subsets A and B
    public int ancestorSubset(Iterable<Integer> subsetA, Iterable<Integer> subsetB){
        
        ArrayList<Integer> commonAncestors=new ArrayList<Integer>();
        //find vertices reachable from v
        DirectedDFS searchA=new DirectedDFS(graph, subsetA);
        //find vertices reachable from w 
        DirectedDFS searchB=new DirectedDFS(graph, subsetB);
        //determine which vertices are reachable from both v and w 
        for(int i=0; i<V; i++){
            if(searchA.marked(i) && searchB.marked(i))
                commonAncestors.add(i);
        }
        return commonAncestors.get(commonAncestors.size()); //don't actually return this tho
    }   
    
}
