import java.io.*;
import java.util.*;

class Node
{
	public static final int Letters =  4;
	public static final int NA      = -1;
	public int next [];
	public boolean patternEnd;

	Node ()
	{
		next = new int [Letters];
		Arrays.fill (next, NA);
		patternEnd = false;
	}
}

public class TrieMatchingExtended implements Runnable {
	int letterToIndex (char letter)
	{
		switch (letter)
		{
			case 'A': return 0;
			case 'C': return 1;
			case 'G': return 2;
			case 'T': return 3;
			default: assert (false); return Node.NA;
		}
	}

	List <Integer> solve (String text, int n, List <String> patterns) {
		List <Integer> result = new ArrayList <Integer> ();

		// write your code here
		List<Node> trie = trieConstruction(patterns);
		for (int i=0; i<text.length(); i++){
			String subtext = text.substring(i);
			boolean tmp = prefixTrieMatching(subtext, trie);
			if (tmp) result.add(i);
		}
		Collections.sort(result);

		return result;
	}

	private boolean prefixTrieMatching(String text, List<Node> trie){
		boolean result = false;

		if (text==null || text.length()==0) return result;
		//int index = 0; // index of text char array
		//int lt = letterToIndex(text.charAt(index)); // first letter of text
		int curNode = 0; // root of trie
		for (int i=0; i<text.length(); i++){
			int lt = letterToIndex(text.charAt(i));
			if (curNode<trie.size() && trie.get(curNode).patternEnd){
				result = true;
				break;
			} else if (curNode<trie.size() && trie.get(curNode).next[lt]!=Node.NA){
				curNode = trie.get(curNode).next[lt];
				if (i==text.length()-1 && trie.get(curNode).patternEnd){
					result = true;
					break;
				}
			} else 
				break;
		}
		
		return result;
	}

	private List<Node> trieConstruction(List<String> patterns){
		List<Node> trie = new ArrayList<Node>();

		trie.add(new Node());
		for (String pt: patterns){
			int curNode = 0; // index of current node
			for (int i=0; i<pt.length(); i++){
				int lt = letterToIndex(pt.charAt(i)); // convert letter to int index
				if (curNode<trie.size() && lt>=0 && trie.get(curNode).next[lt]!=Node.NA){ // if contains letter
					curNode = trie.get(curNode).next[lt]; // go to next node
					if (i==pt.length()-1)
						trie.get(curNode).patternEnd = true;
				}else { // not contain, add new node
					trie.add(new Node());
					int newNode = trie.size() - 1;
					trie.get(curNode).next[lt] = newNode;
					if (i==pt.length()-1)
						trie.get(newNode).patternEnd = true;
					curNode = newNode;
				}
				//System.out.println(curNode+"--"+trie.get(curNode).patternEnd+"-"+lt);
			}
		}

		return trie;
	}

	public void run () {
		try {
			BufferedReader in = new BufferedReader (new InputStreamReader (System.in));
			String text = in.readLine ();
		 	int n = Integer.parseInt (in.readLine ());
		 	List <String> patterns = new ArrayList <String> ();
			for (int i = 0; i < n; i++) {
				patterns.add (in.readLine ());
			}

			List <Integer> ans = solve (text, n, patterns);

			for (int j = 0; j < ans.size (); j++) {
				System.out.print ("" + ans.get (j));
				System.out.print (j + 1 < ans.size () ? " " : "\n");
			}
		}
		catch (Throwable e) {
			e.printStackTrace ();
			System.exit (1);
		}
	}

	public static void main (String [] args) {
		new Thread (new TrieMatchingExtended ()).start ();
	}
}
