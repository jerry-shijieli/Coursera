import java.util.*;
import java.io.*;

public class is_bst_hard {
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

    public class IsBST {
        class Node {
            int key;
            int left;
            int right;

            Node(int key, int left, int right) {
                this.left = left;
                this.right = right;
                this.key = key;
            }
        }

        int nodes;
        Node[] tree;
        Node prev = null;

        void read() throws IOException {
            FastScanner in = new FastScanner();
            nodes = in.nextInt();
            tree = new Node[nodes];
            for (int i = 0; i < nodes; i++) {
                tree[i] = new Node(in.nextInt(), in.nextInt(), in.nextInt());
            }
        }

        boolean isBinarySearchTree() {
          // Implement correct algorithm here
            if (tree.length == 0) return true;
            return isBST(0, tree, true);
        }

        boolean isBST(int index, Node[] tree, boolean isLeftChild){
            if (index>=0 && index<=tree.length && tree[index].key!=-1){
                if (!isBST(tree[index].left, tree, true))
                    return false;
                if (prev!=null) {
                    if (isLeftChild && tree[index].key<=prev.key)
                        return false;
                    if (!isLeftChild && tree[index].key<prev.key)
                        return false;
                }
                prev = tree[index];
                return isBST(tree[index].right, tree, false);
            }
            return true;
        }

        boolean solve() {
            return isBinarySearchTree();
        }
    }

    static public void main(String[] args) throws IOException {
        new Thread(null, new Runnable() {
            public void run() {
                try {
                    new is_bst_hard().run();
                } catch (IOException e) {
                }
            }
        }, "1", 1 << 26).start();
    }
    public void run() throws IOException {
        IsBST tree = new IsBST();
        tree.read();
        if (tree.solve()) {
            System.out.println("CORRECT");
        } else {
            System.out.println("INCORRECT");
        }
    }
}
