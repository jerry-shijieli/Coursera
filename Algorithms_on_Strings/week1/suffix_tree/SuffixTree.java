import java.util.*;
import java.io.*;
import java.util.zip.CheckedInputStream;

public class SuffixTree {
    class Node
    {
        public static final int Letters =  5;
        public static final int NA      = -1;
        public int children[];
        public int position; // position of the whole substring along this path, -1 if node is not leaf.
        public int start; // start position of substring in its incoming edge
        public int length; // length of substring in its incoming edge

        Node ()
        {
            children = new int [Letters];
            Arrays.fill (children, NA);
            position = NA;
            start = NA;
            length = 0;
        }
    }

    class FastScanner {
        StringTokenizer tok = new StringTokenizer("");
        BufferedReader in;

        FastScanner() {
            in = new BufferedReader(new InputStreamReader(System.in));
        }

        String next() throws IOException {
            while (!tok.hasMoreElements())
                tok = new StringTokenizer(in.readLine());
            return tok.nextToken();
        }

        int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }

    private int letterToIndex (char letter)
    {
        switch (letter)
        {
            case '$': return 0;
            case 'A': return 1;
            case 'C': return 2;
            case 'G': return 3;
            case 'T': return 4;
            default: assert (false); return Node.NA;
        }
    }

    // Build a suffix tree of the string text and return a list
    // with all of the labels of its edges (the corresponding 
    // substrings of the text) in any order.
    public List<String> computeSuffixTreeEdges(String text) {
        List<String> result = new ArrayList<String>();
        // Implement this function yourself
        int size = text.length();
        List<Node> stree = new ArrayList<Node>();
        stree.add(new Node());
        for (int pos=0; pos<size; pos++){
            int curNode = 0;
            int id = pos; // start position of substring of text
            while (id < size){
                int lt = letterToIndex(text.charAt(id));
                Node node = stree.get(curNode);
                if (lt>=0 && node.children[lt]==Node.NA){ // edge not exist, create new edge and leaf node
                    Node nodeX = new Node();
                    nodeX.start = id;
                    nodeX.length = size - id;
                    nodeX.position = pos;
                    stree.add(nodeX);
                    node.children[lt] = stree.size() - 1;
                    node.position = Node.NA;
                    id = size;
                    curNode = node.children[lt];
                } else if (lt>=0 && node.children[lt]!=Node.NA){ // edge exist, break edge into two and add two new leaf nodes.
                    Node parent = node;
                    curNode = node.children[lt];
                    node = stree.get(curNode);
                    int j=id+1, k=node.start+1;
                    boolean isAllMatch = true;
                    for (; j<size && k<node.start+node.length; j++, k++){
                        if (text.charAt(j) != text.charAt(k)){
                            isAllMatch = false;
                            Node nodeA = new Node();
                            nodeA.start = node.start;
                            nodeA.length = k-nodeA.start;
                            node.length = node.length + node.start - k;
                            node.start = k;
                            stree.add(nodeA);
                            parent.children[lt] = stree.size() - 1;
                            nodeA.children[letterToIndex(text.charAt(k))] = curNode;
                            Node nodeB = new Node();
                            nodeB.start = j;
                            nodeB.length = size - j;
                            nodeB.position = pos;
                            stree.add(nodeB);
                            curNode = nodeA.children[letterToIndex(text.charAt(j))] = stree.size() - 1;
                            j = size;
                        }
                    }
                    if (isAllMatch){
                        id = j;
                    } else {
                        id = size;
                    }
                } else 
                    id = size;  
            }
        }

        // System.out.println(stree.size());
        for (int i=1; i<stree.size(); i++){
            Node node = stree.get(i);
            // System.out.println(node.position+" -- "+text.substring(node.start, node.start+node.length));
            result.add(text.substring(node.start, node.start+node.length));
        }

        return result;
    }


    static public void main(String[] args) throws IOException {
        new SuffixTree().run();
    }

    public void print(List<String> x) {
        for (String a : x) {
            System.out.println(a);
        }
    }

    public void run() throws IOException {
        FastScanner scanner = new FastScanner();
        String text = scanner.next();
        List<String> edges = computeSuffixTreeEdges(text);
        print(edges);
    }
}
