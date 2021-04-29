import java.util.*;
import java.io.*;

public class WordNet {
    private HashMap<Integer, String> mySynset;//mapped values are synsets whose ids are keys
    private HashMap<String, Node> nounLU;//mapped values are root nodes referencing a linked list containing ids of synsets in which the key noun appears 
    private int numSynsets; //# of synsets
    
    private Digraph myHypernym;
    private ShortestCommonAncestor sca;
        
    //constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) throws FileNotFoundException {
        readSyn(synsets);
	readHyper(hypernyms);
    }
        
    private class Node {
        int id;//synset id
        Node next;
        
        Node(int id) {
            this.id = id;
        }
    }

    //read synsets from file and store in mySynset; 
    private void readSyn(String synsets) throws FileNotFoundException {
        mySynset = new HashMap<Integer, String>(); 
        nounLU = new HashMap<String, Node>(); 
                
        //open synsets file and create Scanner object to read it
        File input = new File(synsets);
        Scanner in = new Scanner(input);
        
        while (in.hasNext()) {
            String s = in.nextLine();
            String[] myFields = s.split(",");
            //myFields[0] contains synset id 
            //myFields[1] contains synset
            //myFields[2] contains gloss 
            String[] nouns = myFields[1].split(" ");//elements are the synonyms in the synset
                          
            for (int i = 0; i < nouns.length; i++) {              
                if (nounLU.containsKey(nouns[i])) {//noun already maps a linked list
                    //traverse the list
                    Node temp = nounLU.get(nouns[i]), first = temp;
                    while (temp.next != null) 
                        temp = temp.next;
                        
                    temp.next = new Node(Integer.parseInt(myFields[0]));//add new node containing synset id to the end of the list
                    nounLU.put(nouns[i], first);//update entry in HashMap 
		}
                    
                else
                    nounLU.put(nouns[i], new Node(Integer.parseInt(myFields[0]))); // adds nouns[i] if not already there
                
                mySynset.put(Integer.parseInt(myFields[0]), myFields[1]);//add synset id key and synset value
                numSynsets++;//update number of synsets
            }
        }
    }

    //creates digraph myHypernym using data in hypernyms file 
    private void readHyper(String hypernyms) throws FileNotFoundException {
	myHypernym = new Digraph(numSynsets);//initializes digraph with vertices for each synset 
        
        //open hypernyms file and create Scanner object to read it
	File input = new File(hypernyms);
	Scanner in = new Scanner(input);
	
        String s = in.nextLine();
	while (in.hasNextLine()) {
            String[] myFields = s.split(",");
            //myFields[0] contains synset id
            //subsequent elements are ids of hypernyms
            
            for (int i = 1; i < myFields.length; i++)
		myHypernym.addEdge(Integer.parseInt(myFields[0]), Integer.parseInt(myFields[i]));
            
            s = in.nextLine();
	}	
    }

    //returns the set of all WordNet nouns
    public Iterable<String> nouns() {
	return nounLU.keySet();
    }

    //returns true of the given word is a valid WordNet noun
    public boolean isNoun(String word) {
	if (word != null) return nounLU.containsKey(word);
	else throw new java.lang.NullPointerException("isNoun() cannot return null");
    }

    //returns a synset that is the shortest common ancestor of the given nouns
    public String sca(String noun1, String noun2) {
        
        sca=new ShortestCommonAncestor(myHypernym); 
        
        //create subset with each of noun1's synset ids
        List<Integer>synsets1=new ArrayList<Integer>();     
        Node setID=nounLU.get(noun1);
        while(setID!=null){
            synsets1.add(setID.id);
            setID=setID.next;
        }
        
        //create subset with each of noun2's synset ids
        List<Integer>synsets2=new ArrayList<Integer>(); 
        setID=nounLU.get(noun2);
        while(setID!=null){
            synsets2.add(setID.id);
            setID=setID.next;
        }
        
        //find shortest common ancestor between these subsets
        int ancestorID=sca.ancestorSubset(synsets1, synsets2);
        
        //return the synset indexed by that synset id
	return mySynset.get(ancestorID);
    }
    
    //returns distance between noun1 and noun2
    public int distance (String noun1, String noun2) {
        
	if (!isNoun(noun1) || !isNoun(noun2))
            throw new IllegalArgumentException();
        
        //create subset with each of noun1's synset ids
        List<Integer>synsets1=new ArrayList<Integer>();     
        Node setID=nounLU.get(noun1);
        while(setID!=null){
            synsets1.add(setID.id);
            setID=setID.next;
        }
        
        //create subset with each of noun2's synset ids
        List<Integer>synsets2=new ArrayList<Integer>(); 
        setID=nounLU.get(noun2);
        while(setID!=null){
            synsets2.add(setID.id);
            setID=setID.next;
        }
	
	//return length of shortest ancestral path 
        return sca.lengthSubset(synsets1, synsets2);
    }
    
    //demonstrates each method in the class 
    public static void main(String[] args) throws FileNotFoundException {
        //constructormethod 
        WordNet words=new WordNet("synsets.txt","hypernyms.txt");
        
        //nouns() method
        int count=0;
        for(String noun : words.nouns())
            count++;
        System.out.println("There are "+count+" nouns in the WordNet");
        
        //isNoun() method
        System.out.println("\"gigolo\" is in the WordNet: "+words.isNoun("gigolo"));
        System.out.println("\"boy toy\" is in the WordNet: "+words.isNoun("boy_toy"));

        //sca() method
        System.out.println("The shortest common ancestor of \"virginity\" and \"virility\" is: "+words.sca("virginity", "virility"));
        
        //distance method()
        System.out.println("The distance between \"virginity\" and \"virility\" is: "+words.distance("virginity", "virility"));   
    }
}
