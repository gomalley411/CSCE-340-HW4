import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;

public class WordNet {
	private HashMap<Integer, String> mySynset;
	private HashMap<String, Node> nounLU; // helps the process of traversing through nouns
	private int numSynsets; // # of synsets
	private Digraph myHypernym;
	private ShortestCommonAncestor sca;

	public WordNet(String synsets, String hypernyms) throws FileNotFoundException {
		readSyn(synsets);
		readHyper(hypernyms);

	}

	// Nodes for nouns so we can traverse through the maps if we need to
	private class Node {
		private int nodeID;
		private Node next;

		public Node(int id) {
			this.nodeID = id;
		}
	}

	// reads synsets file and makes a HashMap of all the nouns
	private void readSyn(String synsets) throws FileNotFoundException {
		mySynset = new HashMap<Integer, String>();
		nounLU = new HashMap<String, Node>();
		File input = new File("synsets.txt");
		Scanner in = new Scanner(input);

		while (in.hasNext()) {
			String s = in.nextLine();
			String[] myFields = s.split(","); // splits string up into sections
			String[] nouns = myFields[1].split(" ");

			for (int i = 0; i < nouns.length; i++) {
				if (nounLU.containsKey(nouns[i])) {
					Node temp = nounLU.get(nouns[i]), first = temp;
					while (temp.next != null) temp = temp.next;

					temp.next = new Node(Integer.parseInt(myFields[0])); // new node will contain that numerical value as a string
					nounLU.put(nouns[i], first);
				}
				else
					nounLU.put(nouns[i], new Node(Integer.parseInt(myFields[0]))); // adds nouns[i] if not already there

				mySynset.put(Integer.parseInt(myFields[0]), myFields[1]);
				numSynsets++;
			}
		}
	}

	// make the digraph and read the hypernyms file
	private void readHyper(String hypernyms) throws FileNotFoundException {
		myHypernym = new Digraph(numSynsets * 2);
		File input = new File("hypernyms.txt");
		Scanner in = new Scanner(input);
		int roots = 0; // # of roots
		boolean[] rootCheck = new boolean[numSynsets]; // true if an index is not a root, false if it is

		while (in.hasNext()) {
			String s = in.nextLine();
			String[] myFields = s.split(",");
			int numFields = myFields.length;

			// this is a possible root if there is only one field in the string
			if (numFields == 1) roots++;
			System.out.println(rootCheck.length);
			rootCheck[Integer.parseInt(myFields[0])] = true;

			for (int i = 1; i < numFields; i++) {
				myHypernym.addEdge(Integer.parseInt(myFields[0]), Integer.parseInt(myFields[i]));
			}
		}

		// now we test for only a singular possible root
		for (int i = 0; i < rootCheck.length; i++) {
			if (!rootCheck[i]) roots++;
		}
		if (roots != 1) throw new IllegalArgumentException("roots must be equal to 1");

		// test for D.A.G. (directed acyclic graph)
		// write later
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
		return ans;
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


	public static void main(String[] args) throws FileNotFoundException {
		WordNet wordnet = new WordNet("synsets.txt", "hypernyms.txt");
		System.out.println("# synsets: " + wordnet.numSynsets);
		assert !wordnet.isNoun("gjgjgjg");
		assert wordnet.isNoun("1530s");
		System.out.println(wordnet.sca("Alan_Rickman", "Alpena"));
	}

}