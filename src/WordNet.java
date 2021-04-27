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
	//readHyper(hypernyms);
    }
        
    private class Node {
        int nodeID;
        Node next;
        
        public Node(int id) {
            //this.nodeID = id;
            nodeID=id;
        }
    }

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

	// make the digraph and read the hypernyms file
	private void readHyper(String hypernyms) throws FileNotFoundException {
		myHypernym = new Digraph(numSynsets);
		File input = new File(hypernyms);
		Scanner in = new Scanner(input);
		String s = in.nextLine();
		while (in.hasNextLine()) {
			String[] myFields = s.split(",");
			for (int i = 1; i < myFields.length; i++) {
				//System.out.println(myFields[i]);
				myHypernym.addEdge(Integer.parseInt(myFields[0])/2, Integer.parseInt(myFields[i])/2);
			}
			s = in.nextLine();
		}
		
		// generate the ShortestCommonAncestor instance
		sca = new ShortestCommonAncestor(myHypernym);
	}

	// returns all WordNet nouns
	public Iterable<String> nouns() {
		return nounLU.keySet();
	}

	// is the word a valid WordNet noun? returns true if it is
	public boolean isNoun(String word) {
		if (word != null) return nounLU.containsKey(word);
		else throw new java.lang.NullPointerException("isNoun() cannot return null");
	}

	// shortest common ancestor
	public String sca(String noun1, String noun2) {
		String ans = "";
		int i = 0;
		while (noun1.charAt(i) == noun2.charAt(i)) {
			if (i == noun1.length() || i == noun2.length()) break;
			ans += noun1.charAt(i);
			i++;
		}
		return "The shortest common ancestor between \"" + noun1 + "\" and \"" + noun2 + "\" is: " + ans;
	}

	public int distance (String nounA, String nounB) {
		if (!isNoun(nounA) || !isNoun(nounB))
			throw new IllegalArgumentException();

		Iterable<Integer> it0 = getIter(nounA), it1 = getIter(nounB);
		return sca.lengthSubset(it0, it1); // note to self: maybe use sca.length(it0, it1) here instead, idk?
	}

	private Iterable<Integer> getIter(final String noun) {
		return new Iterable<Integer>() {
			public Iterator<Integer> iterator() {
				return new Iterator<Integer>() {
					Node curr = nounLU.get(noun);

					public boolean hasNext() {
						return curr != null;
					}

					public Integer next() {
						Integer val = curr.nodeID;
						curr = curr.next;
						return val;
					}

					public void remove() {
					}				   
				};
			}
		};
	}

/*
	public static void main(String[] args) throws FileNotFoundException {
		WordNet wordnet = new WordNet();
		System.out.println("# synsets: " + wordnet.numSynsets);
		assert !wordnet.isNoun("gjgjgjg");
		assert wordnet.isNoun("1530s");
		System.out.println(wordnet.sca("1860s", "1870s"));
	}*/

}
