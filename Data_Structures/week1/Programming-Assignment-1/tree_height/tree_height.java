import java.util.*;
import java.io.*;

public class tree_height {
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

	class Node{
		int val;
		List<Node> children;

		Node(){
			children = new ArrayList<Node>();
		}

		void addChild(Node nd){
			children.add(nd);
		}

		List<Node> getChildren(){
			return children;
		}
	}

	public class TreeHeight {
		int n;
		int parent[];
		
		void read() throws IOException {
			FastScanner in = new FastScanner();
			n = in.nextInt();
			parent = new int[n];
			for (int i = 0; i < n; i++) {
				parent[i] = in.nextInt();
			}
		}

		int computeHeight() {
            // Replace this code with a faster implementation
			int maxHeight = 0;
			// for (int vertex = 0; vertex < n; vertex++) {
			// 	int height = 0;
			// 	for (int i = vertex; i != -1; i = parent[i])
			// 		height++;
			// 	maxHeight = Math.max(maxHeight, height);
			// }
			Node[] nodes = new Node[n];
			for (int i=0; i<n; i++){
				nodes[i] = new Node();
			}
			Node root = null;
			for (int i=0; i<parent.length; i++){
				if (parent[i] == -1)
					root = nodes[i];
				else {
					nodes[parent[i]].addChild(nodes[i]);
				}
			}
			Queue<Node> queue = new LinkedList<Node>();
			queue.offer(root);
			int level = 0;
			while (true){
				int numOfNodes = queue.size();
				if (numOfNodes == 0)
					break;
				level++;
				while (numOfNodes-- > 0){
					Node node = queue.poll();
					for (Node nd: node.getChildren()){
						queue.offer(nd);
					}
				}
			}
			maxHeight = level;
			return maxHeight;
		}
	}

	static public void main(String[] args) throws IOException {
            new Thread(null, new Runnable() {
                    public void run() {
                        try {
                            new tree_height().run();
                        } catch (IOException e) {
                        }
                    }
                }, "1", 1 << 26).start();
	}
	public void run() throws IOException {
		TreeHeight tree = new TreeHeight();
		tree.read();
		System.out.println(tree.computeHeight());
	}
}
