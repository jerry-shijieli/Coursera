import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.*;

public class InverseBWT {
    class Row implements Comparable<Row>{
        StringBuilder data;

        Row() {
            this.data = new StringBuilder();
        }

        @Override
        public int compareTo(Row r2){
            int i=0, j=0;
            while (i<this.data.length() && j<r2.data.length()){
                char c1 = this.data.charAt(i++), c2 = r2.data.charAt(j++);
                if (c1!=c2 && c1=='$')
                    return -1;
                else if (c2!=c1 && c2=='$')
                    return 1;
                else if (c1 == c2)
                    continue;
                else
                    return c1<c2? -1: 1;
            }
            return this.data.length()-r2.data.length();
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

    String inverseBWT(String bwt) {
        StringBuilder result = new StringBuilder();

        // write your code here
        Row[] matrix = new Row[bwt.length()];
        for (int i=0; i<bwt.length(); i++)
            matrix[i] = new Row();
        for (int count=0; count<bwt.length()-1; count++) {
            for (int i = 0; i<bwt.length(); i++) {
                matrix[i].data.insert(0, bwt.charAt(i));
            }
            Arrays.sort(matrix);
        }
        result.append(matrix[0].data.substring(1));
        result.append(bwt.charAt(0));
        result.append('$');

        return result.toString();
    }

    static public void main(String[] args) throws IOException {
        new InverseBWT().run();
    }

    public void run() throws IOException {
        FastScanner scanner = new FastScanner();
        String bwt = scanner.next();
        System.out.println(inverseBWT(bwt));
    }
}
