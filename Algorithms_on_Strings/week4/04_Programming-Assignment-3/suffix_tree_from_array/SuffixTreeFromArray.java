import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class SuffixTreeFromArray {
    public static final int ALPHABET_SIZE = 5;
    public static final int NA = -1;

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

    // convert letter to number in lexicographical order
    private int c2n(char firstLetter){
        switch(firstLetter){
            case '$': return 0;
            case 'A': return 1;
            case 'C': return 2;
            case 'G': return 3;
            case 'T': return 4;
            default: return -1;
        }
    }

    // Data structure to store edges of a suffix tree.
    public class Edge {
        // The ending node of this edge.
        int node;
        // Starting position of the substring of the text 
        // corresponding to the label of this edge.
        int start;
        // Position right after the end of the substring of the text 
        // corresponding to the label of this edge.
        int end;

        Edge(int node, int start, int end) {
            this.node = node;
            this.start = start;
            this.end = end;
        }
    }

    // Data structure to store node of a suffix tree.
    public class SuffixTreeNode {
        int parent;
        int[] children;
        int stringDepth;
        int edgeStart;
        int edgeEnd;

        SuffixTreeNode(int parent, int stringDepth, int edgeStart, int edgeEnd){
            children = new int[ALPHABET_SIZE];
            Arrays.fill(children, NA);
            this.parent = parent;
            this.stringDepth = stringDepth;
            this.edgeStart = edgeStart;
            this.edgeEnd = edgeEnd;
        }
    }

    // Build suffix tree of the string text given its suffix array suffix_array
    // and LCP array lcp_array. Return the tree as a mapping from a node ID
    // to the list of all outgoing edges of the corresponding node. The edges in the
    // list must be sorted in the ascending order by the first character of the edge label.
    // Root must have node ID = 0, and all other node IDs must be different
    // nonnegative integers.
    //
    // For example, if text = "ACACAA$", an edge with label "$" from root to a node with ID 1
    // must be represented by new Edge(1, 6, 7). This edge must be present in the list tree.get(0)
    // (corresponding to the root node), and it should be the first edge in the list
    // (because it has the smallest first character of all edges outgoing from the root).
    Map<Integer, List<Edge>> SuffixTreeFromSuffixArray(
            int[] suffixArray,
            int[] lcpArray,
            final String text) {
        Map<Integer, List<Edge>> tree = new HashMap<Integer, List<Edge>>();
        // Implement this function yourself
        List<SuffixTreeNode> suffix_tree = new ArrayList<>();
        // add root node
        SuffixTreeNode root = new SuffixTreeNode(NA, 0, NA, NA);
        suffix_tree.add(root);
        int lcpPrev = 0;
        int curNodeIndex = 0;
        for (int order=0; order<suffixArray.length; order++){
            int suffix = suffixArray[order];
            SuffixTreeNode curNode = suffix_tree.get(curNodeIndex);
            while (curNode.stringDepth > lcpPrev){
                curNodeIndex = curNode.parent;
                curNode = suffix_tree.get(curNodeIndex);
            }
            if (curNode.stringDepth == lcpPrev){
                curNodeIndex = createNewLeaf(suffix_tree, curNodeIndex, text, suffix);
            } else {
                int edgeStart = suffixArray[order-1] + curNode.stringDepth;
                int offset = lcpPrev - curNode.stringDepth;
                int midNodeIndex = breakEdge(suffix_tree, curNodeIndex, text, edgeStart, offset);
                curNodeIndex = createNewLeaf(suffix_tree, midNodeIndex, text, suffix);
            }
            if (order < text.length()-1)
                lcpPrev = lcpArray[order];
        }

        System.out.println(suffix_tree.size());

        for (int nodeIndex=0; nodeIndex<suffix_tree.size(); nodeIndex++){
            SuffixTreeNode curNode = suffix_tree.get(nodeIndex);
            tree.put(nodeIndex, new ArrayList<Edge>());
            boolean hasNoChildren = true;
            for (int childIndex: curNode.children){
                if (childIndex != NA){
                    hasNoChildren = false;
                    tree.get(nodeIndex).add(new Edge(childIndex,
                            suffix_tree.get(childIndex).edgeStart, suffix_tree.get(childIndex).edgeEnd+1));
                }
            }
            if (hasNoChildren)
                tree.remove(nodeIndex);
        }

        return tree;
    }

    private int breakEdge(List<SuffixTreeNode> suffix_tree, int curNodeIndex, String text, int start, int offset) {
        int startCharCode = c2n(text.charAt(start));
        int midCharCode = c2n(text.charAt(start+offset));
        SuffixTreeNode curNode = suffix_tree.get(curNodeIndex);
        SuffixTreeNode midNode = new SuffixTreeNode(curNodeIndex,
                curNode.stringDepth+offset,
                start, start+offset-1);
        suffix_tree.add(midNode);
        int midNodeIndex = suffix_tree.size() - 1;
        midNode.children[midCharCode] = curNode.children[startCharCode];
        SuffixTreeNode childNode = suffix_tree.get(curNode.children[startCharCode]);
        childNode.parent = midNodeIndex;
        childNode.edgeStart = start + offset;
        curNode.children[startCharCode] = midNodeIndex;
        return midNodeIndex;
    }

    private int createNewLeaf(List<SuffixTreeNode> suffix_tree, int curNodeIndex, String text, int suffix) {
        SuffixTreeNode curNode = suffix_tree.get(curNodeIndex);
        SuffixTreeNode leaf = new SuffixTreeNode(curNodeIndex,
                text.length()-suffix,
                suffix+curNode.stringDepth,
                text.length()-1);
        suffix_tree.add(leaf);
        int leafIndex = suffix_tree.size()-1;
        suffix_tree.get(curNodeIndex).children[c2n(text.charAt(leaf.edgeStart))] = leafIndex;
        return leafIndex;
    }

    static public void main(String[] args) throws IOException {
        new SuffixTreeFromArray().run();
    }

    public void print(ArrayList<String> x) {
        for (String a : x) {
            System.out.println(a);
        }
    }

    public void run() throws IOException {
        FastScanner scanner = new FastScanner();
        String text = scanner.next();
        int[] suffixArray = new int[text.length()];
        for (int i = 0; i < suffixArray.length; ++i) {
            suffixArray[i] = scanner.nextInt();
        }
        int[] lcpArray = new int[text.length() - 1];
        for (int i = 0; i + 1 < text.length(); ++i) {
            lcpArray[i] = scanner.nextInt();
        }
        System.out.println(text);
        // Build the suffix tree and get a mapping from 
        // suffix tree node ID to the list of outgoing Edges.
        Map<Integer, List<Edge>> suffixTree = SuffixTreeFromSuffixArray(suffixArray, lcpArray, text);
        ArrayList<String> result = new ArrayList<String>();
        // Output the edges of the suffix tree in the required order.
        // Note that we use here the contract that the root of the tree
        // will have node ID = 0 and that each vector of outgoing edges
        // will be sorted by the first character of the corresponding edge label.
        //
        // The following code avoids recursion to avoid stack overflow issues.
        // It uses two stacks to convert recursive function to a while loop.
        // This code is an equivalent of 
        //
        //    OutputEdges(tree, 0);
        //
        // for the following _recursive_ function OutputEdges:
        //
        // public void OutputEdges(Map<Integer, List<Edge>> tree, int nodeId) {
        //     List<Edge> edges = tree.get(nodeId);
        //     for (Edge edge : edges) {
        //         System.out.println(edge.start + " " + edge.end);
        //         OutputEdges(tree, edge.node);
        //     }
        // }
        //
        int[] nodeStack = new int[text.length()];
        int[] edgeIndexStack = new int[text.length()];
        nodeStack[0] = 0;
        edgeIndexStack[0] = 0;
        int stackSize = 1;
        while (stackSize > 0) {
            int node = nodeStack[stackSize - 1];
            int edgeIndex = edgeIndexStack[stackSize - 1];
            stackSize -= 1;
            if (suffixTree.get(node) == null) {
                continue;
            }
            if (edgeIndex + 1 < suffixTree.get(node).size()) {
                nodeStack[stackSize] = node;
                edgeIndexStack[stackSize] = edgeIndex + 1;
                stackSize += 1;
            }
            result.add(suffixTree.get(node).get(edgeIndex).start + " " + suffixTree.get(node).get(edgeIndex).end);
            nodeStack[stackSize] = suffixTree.get(node).get(edgeIndex).node;
            edgeIndexStack[stackSize] = 0;
            stackSize += 1;
        }
        print(result);
    }
}
