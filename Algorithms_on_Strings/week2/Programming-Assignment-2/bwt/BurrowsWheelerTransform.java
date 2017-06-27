import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class BurrowsWheelerTransform {
    class Row implements Comparable<Row>{
        String data;

        Row(String text) {
            this.data = new String(text);
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

    String BWT(String text) {
        StringBuilder result = new StringBuilder();

        // write your code here
        Row[] matrix = new Row[text.length()];
        matrix[0] = new Row(text);
        for (int i=1; i<text.length(); i++){
            String rotation = text.substring(i) + text.substring(0,i);
            matrix[i] = new Row(rotation);
        }
        Arrays.sort(matrix);
        for (int i=0; i<text.length(); i++){
            result.append(matrix[i].data.charAt(text.length()-1));
        }

        return result.toString();
    }

    static public void main(String[] args) throws IOException {
        new BurrowsWheelerTransform().run();
    }

    public void run() throws IOException {
        FastScanner scanner = new FastScanner();
        String text = scanner.next();
        System.out.println(BWT(text));
    }
}
